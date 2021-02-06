package com.example.dubboExample.contract;

import com.example.dubboExample.contract.base.SignedDataContainer;
import com.example.dubboExample.contract.req.UserModifyNameRequest;

import java.util.List;


public interface CommonRpcService {

    SignedDataContainer<Boolean> modifyUserName(SignedDataContainer<UserModifyNameRequest> signedRequest);

    SignedDataContainer<List<Long>> echoList(SignedDataContainer<List<Long>> signedRequest);

}
