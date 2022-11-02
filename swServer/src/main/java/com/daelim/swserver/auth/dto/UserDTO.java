package com.daelim.swserver.auth.dto;

import com.daelim.swserver.auth.entity.User;
import lombok.Data;


@Data
public class UserDTO {
    private String userId;
    private String userName;
    private String userPassword;
    private String useremail;
    private String userdeviceId;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .useremail(useremail)
                .userdeviceId(userdeviceId)
                .build();
    }
}
