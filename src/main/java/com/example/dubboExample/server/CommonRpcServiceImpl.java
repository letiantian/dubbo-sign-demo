package com.example.dubboExample.server;

import com.example.dubboExample.common.Config;
import com.example.dubboExample.common.ValidatorUtil;
import com.example.dubboExample.contract.CommonRpcService;
import com.example.dubboExample.contract.base.SignedDataContainer;
import com.example.dubboExample.contract.req.UserModifyNameRequest;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class CommonRpcServiceImpl implements CommonRpcService {

    @Override
    public SignedDataContainer<Boolean> modifyUserName(SignedDataContainer<UserModifyNameRequest> signedRequest) {
        log.info("modifyUserName 请求数据: {}", signedRequest);
        log.info("modifyUserName 请求业务数据: {}", signedRequest.fetchBizData(UserModifyNameRequest.class));
        log.info("modifyUserName 校验请求签名结果: {}", signedRequest.checkSign(Config.SECRET_KEY));

        // 校验
        ValidatorUtil.validate(signedRequest.fetchBizData(UserModifyNameRequest.class));

        SignedDataContainer<Boolean> response = new SignedDataContainer<>();
        response.setAppId(Config.APP_ID);
        response.storeBizData(Boolean.TRUE);
        response.generateSign(Config.SECRET_KEY);

        return response;
    }

    @Override
    public SignedDataContainer<List<Long>> echoList(SignedDataContainer<List<Long>> signedRequest) {
        log.info("echoList 请求数据: {}", signedRequest);
        log.info("echoList 请求业务数据: {}", signedRequest.fetchBizData(new TypeToken<List<Long>>(){}));
        log.info("echoList 校验请求签名结果: {}", signedRequest.checkSign(Config.SECRET_KEY));
        return signedRequest;
    }

}
