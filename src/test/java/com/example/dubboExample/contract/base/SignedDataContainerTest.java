package com.example.dubboExample.contract.base;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class SignedDataContainerTest {

    @Data
    public static class UserInfo {
        private String name;
        private List<Long> scores;
    }

    @Test
    public void test() {
        Assertions.assertEquals(1, 1);
    }


    @Test
    public void test_fetchBizData_01() {
        SignedDataContainer<String> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData("hello");
        String bizData = signedDataContainer.fetchBizData(String.class);
        Assertions.assertEquals("hello", bizData);
    }

    @Test
    public void test_fetchBizData_02() {
        SignedDataContainer<Integer> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(123);
        Integer bizData = signedDataContainer.fetchBizData(Integer.class);
        Assertions.assertEquals(123, bizData);
    }

    @Test
    public void test_fetchBizData_03() {
        SignedDataContainer<Long> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(123L);
        Long bizData = signedDataContainer.fetchBizData(Long.class);
        Assertions.assertEquals(123L, bizData);
    }

    @Test
    public void test_fetchBizData_04() {
        SignedDataContainer<Void> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(null);
        Object obj = signedDataContainer.fetchBizData(Void.class);
        Assertions.assertNull(obj);
    }

    @Test
    public void test_fetchBizData_05() {
        SignedDataContainer<List<String>> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(Lists.newArrayList("123", "456"));
        List<String> bizData = signedDataContainer.fetchBizData(new TypeToken<List<String>>(){});
        System.out.println(bizData);
        Assertions.assertEquals(2, bizData.size());
        Assertions.assertEquals("123", bizData.get(0));
        Assertions.assertEquals("456", bizData.get(1));
    }

    @Test
    public void test_fetchBizData_06() {
        SignedDataContainer<List<Long>> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(Lists.newArrayList(123L, 456L));
        List<Long> bizData = signedDataContainer.fetchBizData(new TypeToken<List<Long>>(){});
        System.out.println(bizData);
        Assertions.assertEquals(2, bizData.size());
        Assertions.assertEquals(123L, bizData.get(0));
        Assertions.assertEquals(456L, bizData.get(1));
    }

    @Test
    public void test_fetchBizData_07() {
        UserInfo libai = new UserInfo();
        libai.setName("李白");
        libai.setScores(Arrays.asList(99L, 98L, 100L));
        SignedDataContainer<UserInfo> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(libai);
        UserInfo bizData01 = signedDataContainer.fetchBizData(new TypeToken<UserInfo>(){});
        UserInfo bizData02 = signedDataContainer.fetchBizData(UserInfo.class);
        System.out.println(bizData01);
        System.out.println(bizData02);

        Assertions.assertEquals(bizData01, bizData02);

        for (Long item : bizData01.scores) {
            System.out.println(item);
        }

        for (Long item : bizData02.scores) {
            System.out.println(item);
        }
    }


    @Test
    public void test_generateSign_01() {
        String appId = "appId-test";
        String secretKey = "sk-test";
        SignedDataContainer<String> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.setAppId(appId);
        signedDataContainer.storeBizData("hello");
        signedDataContainer.generateSign(secretKey);
        System.out.println(signedDataContainer);
        Assertions.assertTrue(signedDataContainer.checkSign(secretKey));
        Assertions.assertFalse(signedDataContainer.checkSign(secretKey + "111"));
    }

    @Test
    public void test_generateSign_02() {
        String appId = "appId-test";
        String secretKey = "sk-test";

        SignedDataContainer<List<Long>> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(Lists.newArrayList(123L, 456L));

        signedDataContainer.setAppId(appId);
        signedDataContainer.generateSign(secretKey);

        System.out.println(signedDataContainer);
        Assertions.assertTrue(signedDataContainer.checkSign(secretKey));
        Assertions.assertFalse(signedDataContainer.checkSign(secretKey + "111"));
    }

    @Test
    public void test_generateSign_03() {
        String appId = "appId-test";
        String secretKey = "sk-test";

        UserInfo libai = new UserInfo();
        libai.setName("李白");
        libai.setScores(Arrays.asList(99L, 98L, 100L));
        SignedDataContainer<UserInfo> signedDataContainer = new SignedDataContainer<>();
        signedDataContainer.storeBizData(libai);

        signedDataContainer.setAppId(appId);
        signedDataContainer.generateSign(secretKey);

        System.out.println(signedDataContainer);
        Assertions.assertTrue(signedDataContainer.checkSign(secretKey));
        Assertions.assertFalse(signedDataContainer.checkSign(secretKey + "111"));
    }

}
