package com.cskaoyan.user.service.impl;

import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.dal.entitys.ImageResult;
import com.cskaoyan.user.dto.KaptchaCodeRequest;
import com.cskaoyan.user.dto.KaptchaCodeResponse;
import com.cskaoyan.user.service.IKaptchaService;
import com.cskaoyan.user.utils.ExceptionProcessorUtils;
import com.cskaoyan.user.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class KaptchaServiceImpl implements IKaptchaService {

    @Autowired
    RedissonClient redissonClient;

    private final String KAPTCHA_UUID = "kaptcha_uuid";

    @Override
    public KaptchaCodeResponse getKaptchaCode(KaptchaCodeRequest request) {
        KaptchaCodeResponse response = new KaptchaCodeResponse();
        try {
            // 生成一个验证码信息
            ImageResult capText = VerifyCodeUtils.VerifyCode(140, 43, 4);
            String uuid = UUID.randomUUID().toString();
            RBucket rBucket = redissonClient.getBucket(KAPTCHA_UUID + uuid);
            // 获取验证码的码值
            rBucket.set(capText.getCode());
            log.info("产生的验证码:{},uuid:{}", capText.getCode(), uuid);
            rBucket.expire(120, TimeUnit.SECONDS);
            // 获取验证码图片
            response.setImageCode(capText.getImg());
            response.setUuid(uuid);
            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("KaptchaServiceImpl.getKaptchaCode occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public KaptchaCodeResponse validateKaptchaCode(KaptchaCodeRequest request) {
        KaptchaCodeResponse response = new KaptchaCodeResponse();
        try {
            request.requestCheck();
            String redisKey = KAPTCHA_UUID + request.getUuid();
            // RBuckt对象，当成是一个redis中一个String类型值的代理对象
            RBucket<String> rBucket = redissonClient.getBucket(redisKey);
            String code = rBucket.get();
            log.info("请求的redisKey={},请求的code={},从redis获得的code={}", redisKey, request.getCode(), code);
            if (StringUtils.isNotBlank(code) && request.getCode().equalsIgnoreCase(code)) {
                response.setCode(UserRetCode.SUCCESS.getCode());
                response.setMsg(UserRetCode.SUCCESS.getMessage());
                return response;
            }
            response.setCode(UserRetCode.KAPTCHA_CODE_ERROR.getCode());
            response.setMsg(UserRetCode.KAPTCHA_CODE_ERROR.getMessage());
        } catch (Exception e) {
            log.error("KaptchaServiceImpl.validateKaptchaCode occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
