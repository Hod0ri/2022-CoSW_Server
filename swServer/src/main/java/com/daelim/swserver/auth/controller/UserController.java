package com.daelim.swserver.auth.controller;

import com.daelim.swserver.auth.dto.UserDTO;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.daelim.swserver.security.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    SHA256 sha256 = new SHA256();

    @PostMapping("signup")
    public void signup(@RequestBody UserDTO userdto) throws NoSuchAlgorithmException {
        userdto.setUserPassword(sha256.encrypt(userdto.getUserPassword()));
        log.info("Request : ", userdto.toString());

        User user = userdto.toEntity();

        User saveUser = userRepository.save(user);


    }
}
