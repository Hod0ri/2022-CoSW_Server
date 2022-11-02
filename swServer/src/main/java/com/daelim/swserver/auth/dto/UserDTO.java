package com.daelim.swserver.auth.dto;

import com.daelim.swserver.auth.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private String userId;
    private String userName;
    private String userPassword;
    private String userEmail;
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
