package com.daelim.swserver.auth.dto;

import com.daelim.swserver.auth.entity.User;
import lombok.Data;


@Data
public class UserDTO {
    private String userId;
    private String userName;
    private String userPassword;
    private String email;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .email(email)
                .build();
    }
}
