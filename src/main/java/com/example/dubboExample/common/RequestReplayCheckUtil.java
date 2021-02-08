package com.example.dubboExample.common;

/**
 * 请求重放校验
 */
public class RequestReplayCheckUtil {

    public static void checkTimestamp(Long timestamp) {
        if (timestamp == null) {
            throw new RuntimeException("timestamp 不能为空");
        }
        // timestamp 必须在当前时间的前后5分钟之内
        long currentTime = System.currentTimeMillis()/1000;

        if (timestamp < currentTime - 300 || timestamp > currentTime + 300 ) {
            throw new RuntimeException("timestamp 有问题");
        }
    }
}
