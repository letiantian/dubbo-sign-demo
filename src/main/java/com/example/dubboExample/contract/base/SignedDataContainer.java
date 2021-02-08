package com.example.dubboExample.contract.base;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
public class SignedDataContainer<T> implements Serializable {

    // 调用方标识，会分配密钥
    @NotBlank(message = "appId 不能为空")
    private String appId;

    // 时间戳（单位秒）
    @NotNull(message = "timestamp 不能为空")
    private Long timestamp;

    // 业务数据（使用 json 序列化）
    private String bizData;

    // 签名
    @NotBlank(message = "sign 不能为空")
    private String sign;

    /**
     * 将 bizData 反序列化为业务类对象
     *
     * @param clz 业务类类型
     * @return 业务类对象
     */
    public T fetchBizData(Class<T> clz) {
        Gson gson = new Gson();
        if (bizData == null) {
            return null;
        }
        return gson.fromJson(this.bizData, clz);
    }

    /**
     * 将 bizData 反序列化为业务类对象
     *
     * @param typeToken 业务类本身若有泛型（例如List&lt;String&gt;），则需要使用 TypeToken 机制反序列化
     * @return 业务类对象
     */
    public T fetchBizData(TypeToken<T> typeToken) {
        Gson gson = new Gson();
        if (bizData == null) {
            return null;
        }
        return gson.fromJson(this.bizData, typeToken.getType());
    }

    /**
     * 将业务类对象序列化为 bizData
     * @param obj
     */
    public void storeBizData(T obj) {
        Gson gson = new Gson();
        if (obj == null) {
            return;
        }
        this.bizData = gson.toJson(obj);
    }

    /**
     * 生成 timestamp、sign
     *
     * @param secretKey 密钥
     */
    public void generateSign(String secretKey) {
        this.timestamp = System.currentTimeMillis()/1000;
        this.sign = buildSign(secretKey);
    }

    /**
     * 校验签名
     *
     * @param secretKey 密钥
     * @return 校验结果
     */
    public boolean checkSign(String secretKey) {
        return Objects.equals(this.sign, buildSign(secretKey));
    }

    /**
     * 生成签名
     *
     * @param secretKey 密钥
     * @return 签名
     */
    private String buildSign(String secretKey) {
        String data = String.format("appId=%s&timestamp=%s&bizData=%s&secretKey=%s",
                appId == null ? "" : appId,
                timestamp,
                bizData,
                secretKey == null ? "" : secretKey);
        /*
        System.out.println("data: " + data);
        String result = Hashing.sha256().newHasher().putString(data, Charsets.UTF_8).hash().toString();
        System.out.println("sha256: " + result);
         */
        // 使用 sha256 算法签名，基于 guava 库 Hashing 类
        return Hashing.sha256().newHasher().putString(data, Charsets.UTF_8).hash().toString();
    }

}