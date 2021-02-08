package com.example.dubboExample.common;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;

public class ValidatorUtilTest {

    @Data
    public static class UserInfo {
        @NotBlank(message = "name 不能为空")
        private String name;
    }


    @Test
    public void test_validate() {
        UserInfo userInfo = new UserInfo();

        // 因为 name 没有值，校验不通过，抛异常
        try {
            ValidatorUtil.validate(userInfo);
            Assertions.fail();
        } catch (RuntimeException ex) {
            Assertions.assertEquals("name 不能为空", ex.getMessage());
        }

        // 通过设值，让校验通过
        userInfo.setName("李白");
        ValidatorUtil.validate(userInfo);
    }

}
