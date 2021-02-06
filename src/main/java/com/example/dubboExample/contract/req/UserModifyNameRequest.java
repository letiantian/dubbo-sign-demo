package com.example.dubboExample.contract.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class UserModifyNameRequest implements Serializable {

    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotBlank(message = "newUserName 不能为空")
    private String newUserName;

}
