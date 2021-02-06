package com.example.dubboExample.client;

import com.example.dubboExample.common.Config;
import com.example.dubboExample.contract.CommonRpcService;
import com.example.dubboExample.contract.base.SignedDataContainer;
import com.example.dubboExample.contract.req.UserModifyNameRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Slf4j
public class ClientMain01 {

    public static void main(String[] args) {
        System.out.println("start");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("client.xml");
        CommonRpcService commonRpcService = ctx.getBean(CommonRpcService.class);

        // 请求数据
        UserModifyNameRequest userModifyNameRequest = new UserModifyNameRequest();
        userModifyNameRequest.setUserId(123L);
//        userModifyNameRequest.setNewUserName("李白");
        SignedDataContainer<UserModifyNameRequest> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.setAppId(Config.APP_ID);
        signedDataContainer.storeBizData(userModifyNameRequest);
        signedDataContainer.generateSign(Config.SECRET_KEY);


        SignedDataContainer<Boolean> signedResponse = commonRpcService.modifyUserName(signedDataContainer);
        log.info("响应: {}", signedResponse);
        log.info("校验响应的签名结果: {}", signedResponse.checkSign(Config.SECRET_KEY));

    }

}
