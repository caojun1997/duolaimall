package com.cskaoyan.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.mall.commons.constant.SysRetCodeConstants;
import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.dto.*;
import com.cskaoyan.user.form.AddressForm;
import com.cskaoyan.user.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/shopping")
public class AddressController {

    @Autowired
   IAddressService addressService;

    /**
     * 获取当前用户的地址列表
     *
     * @return
     */
    @GetMapping("/addresses")
    public ResponseData addressList(HttpServletRequest request) {
        String userInfo = (String) request.getHeader("user_info");
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        AddressListRequest addressListRequest = new AddressListRequest();
        addressListRequest.setUserId(uid);
        AddressListResponse response = addressService.addressList(addressListRequest);
        if (response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getAddressDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @PostMapping("/addresses")
    public ResponseData addressAdd(@RequestBody AddressForm form,
                           HttpServletRequest servletRequest) {
        log.debug(form.is_Default()+"");
        log.debug(form.toString());

        AddAddressRequest request = new AddAddressRequest();
        String userInfo = (String) servletRequest.getHeader("user_info");
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        request.setUserId(uid);
        request.setUserName(form.getUserName());
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setIsDefault(form.is_Default() ? 1 : null);
        AddAddressResponse response = addressService.createAddress(request);

        if (response.getCode().equals(UserRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @DeleteMapping("/addresses/{addressid}")
    public ResponseData addressDel(@PathVariable("addressid") Long addressid) {
        DeleteAddressRequest request = new DeleteAddressRequest();
        request.setAddressId(addressid);
        DeleteAddressResponse response = addressService.deleteAddress(request);

        if (response.getCode().equals(UserRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());

    }

    @PutMapping("/addresses")
    public ResponseData addressUpdate(@RequestBody AddressForm form,
                              HttpServletRequest servletRequest) {
        UpdateAddressRequest request = new UpdateAddressRequest();
        String userInfo = (String) servletRequest.getHeader("user_info");
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        request.setAddressId(form.getAddressId());
        request.setIsDefault(form.is_Default() ? 1 : null);
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setUserId(uid);
        request.setUserName(form.getUserName());

        UpdateAddressResponse response = addressService.updateAddress(request);

        if (response.getCode().equals(UserRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
