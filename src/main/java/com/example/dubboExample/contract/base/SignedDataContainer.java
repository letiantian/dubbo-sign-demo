package com.example.dubboExample.contract.base;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class SignedDataContainer<T> implements Serializable {

    private String appId;
    private Long timestamp;
    private String bizData;

    private String sign;

    public T fetchBizData(Class<T> clz) {
        Gson gson = new Gson();
        if (bizData == null) {
            return null;
        }
        return gson.fromJson(this.bizData, clz);
    }

    public T fetchBizData(TypeToken<T> typeToken) {
        Gson gson = new Gson();
        if (bizData == null) {
            return null;
        }
        return gson.fromJson(this.bizData, typeToken.getType());
    }

    public void storeBizData(T obj) {
        Gson gson = new Gson();
        if (obj == null) {
            return;
        }
        this.bizData = gson.toJson(obj);
    }

    public void generateSign(String secretKey) {
        this.timestamp = System.currentTimeMillis()/1000;
        this.sign = buildSign(secretKey);
    }

    public boolean checkSign(String secretKey) {
        return Objects.equals(this.sign, buildSign(secretKey));
    }

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
        return Hashing.sha256().newHasher().putString(data, Charsets.UTF_8).hash().toString();
    }
}