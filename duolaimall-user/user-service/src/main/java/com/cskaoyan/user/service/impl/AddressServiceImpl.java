package com.cskaoyan.user.service.impl;


import com.cskaoyan.user.constants.UserRetCode;
import com.cskaoyan.user.converter.AddressConverter;
import com.cskaoyan.user.dal.entitys.Address;
import com.cskaoyan.user.dal.persistence.AddressMapper;
import com.cskaoyan.user.dto.*;
import com.cskaoyan.user.service.IAddressService;
import com.cskaoyan.user.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    AddressConverter converter;

    @Override
    public AddressListResponse addressList(AddressListRequest request) {
        AddressListResponse response=new AddressListResponse();
        try{
            request.requestCheck();
            Example example=new Example(Address.class);

            example.createCriteria().andEqualTo("userId",request.getUserId());
            List<Address> addresses=addressMapper.selectByExample(example);
            response.setAddressDtos(converter.address2List(addresses));
            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("AddressServiceImpl.addressList occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public AddressDetailResponse addressDetail(AddressDetailRequest request) {
        AddressDetailResponse response=new AddressDetailResponse();
        try{
            request.requestCheck();
            Address address=addressMapper.selectByPrimaryKey(request.getAddressId());
            response.setAddressDto(converter.address2List(address));
            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("AddressServiceImpl.addressDetail occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public AddAddressResponse createAddress(AddAddressRequest request) {
        log.error("AddressServiceImpl.createAddress request :"+request);
        AddAddressResponse response=new AddAddressResponse();
        try{
            request.requestCheck();
            checkAddressDefaultUnique(request.getIsDefault() != null && request.getIsDefault()==1,request.getUserId());
            Address address=converter.req2Address(request);
            int row=addressMapper.insert(address);
            response.setCode(UserRetCode.SUCCESS.getCode());
            response.setMsg(UserRetCode.SUCCESS.getMessage());
            log.info("AddressServiceImpl.createAddress effect row :"+row);
        }catch (Exception e){
            log.error("AddressServiceImpl.createAddress occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public UpdateAddressResponse updateAddress(UpdateAddressRequest request) {
        log.error("begin - AddressServiceImpl.updateAddress request :"+request);
        UpdateAddressResponse response=new UpdateAddressResponse();
        try{
            request.requestCheck();
            checkAddressDefaultUnique(request.getIsDefault()==1,request.getUserId());
            Address address=converter.req2Address(request);
            int row=addressMapper.updateByPrimaryKey(address);
            response.setMsg(UserRetCode.SUCCESS.getMessage());
            response.setCode(UserRetCode.SUCCESS.getCode());
            log.info("AddressServiceImpl.createAddress effect row :"+row);
        }catch (Exception e){
            log.error("AddressServiceImpl.updateAddress occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public DeleteAddressResponse deleteAddress(DeleteAddressRequest request) {
        log.error("begin - AddressServiceImpl.deleteAddress request :"+request);
        DeleteAddressResponse response=new DeleteAddressResponse();
        try{
            request.requestCheck();
            int row=addressMapper.deleteByPrimaryKey(request.getAddressId());
            if(row>0){
                response.setCode(UserRetCode.SUCCESS.getCode());
                response.setMsg(UserRetCode.SUCCESS.getMessage());
            }else{
                response.setCode(UserRetCode.DATA_NOT_EXIST.getCode());
                response.setMsg(UserRetCode.DATA_NOT_EXIST.getMessage());
            }
            log.info("AddressServiceImpl.deleteAddress effect row :"+row);
        }catch (Exception e){
            log.error("AddressServiceImpl.deleteAddress occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    //地址只能有一个默认
    private void checkAddressDefaultUnique(boolean isDefault,Long userId){
        if(isDefault){
            Example example=new Example(Address.class);
            example.createCriteria().andEqualTo("userId",userId);
            List<Address> addresses=addressMapper.selectByExample(example);
            addresses.parallelStream().forEach(address->{
                if(address.getIsDefault()==1){
                    address.setIsDefault(0);
                    addressMapper.updateByPrimaryKey(address);
                }
            });
        }
    }
}
