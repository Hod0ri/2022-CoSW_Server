package com.daelim.swserver.auth.dto;

import com.daelim.swserver.auth.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDTO {
    @ApiModelProperty(value = "사용자 ID")

    private String userId;
    @ApiModelProperty(value = "사용자 닉네임")
    private String userName;
    @ApiModelProperty(value = "사용자 비밀번호")
    private String userPassword;
    @ApiModelProperty(value = "사용자 이메일")
    private String userEmail;
    @ApiModelProperty(value = "사용자 NFC UID")
    private String userDeviceId;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .userEmail(userEmail)
                .userDeviceId(userDeviceId)
                .build();
    }

}
