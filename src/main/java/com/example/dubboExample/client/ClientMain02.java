package com.example.dubboExample.client;

import com.example.dubboExample.common.Config;
import com.example.dubboExample.contract.CommonRpcService;
import com.example.dubboExample.contract.base.SignedDataContainer;
import com.example.dubboExample.contract.req.UserModifyNameRequest;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;


@Slf4j
public class ClientMain02 {

    public static void main(String[] args) {
        System.out.println("start");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("client.xml");
        CommonRpcService commonRpcService = ctx.getBean(CommonRpcService.class);

        // 请求数据
        List<Long> numList = Arrays.asList(123L, 456L);
        SignedDataContainer<List<Long>> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.setAppId(Config.APP_ID);
        signedDataContainer.storeBizData(numList);
        signedDataContainer.generateSign(Config.SECRET_KEY);

        SignedDataContainer<List<Long>> signedResponse = commonRpcService.echoList(signedDataContainer);

        for (Long item : signedResponse.fetchBizData(new TypeToken<List<Long>>(){})) {
            log.info("{}", item);
        }

        log.info("响应: {}", signedResponse);
        log.info("校验响应的签名结果: {}", signedResponse.checkSign(Config.SECRET_KEY));

    }

}
