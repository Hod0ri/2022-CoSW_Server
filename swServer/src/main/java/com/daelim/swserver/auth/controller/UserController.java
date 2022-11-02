package com.daelim.swserver.auth.controller;

import com.daelim.swserver.auth.dto.UserDTO;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.daelim.swserver.security.SHA256;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    SHA256 sha256 = new SHA256();

    @Operation(
            summary = "사용자 회원가입",
            description = "회원가입 성공여부 상태 리턴 (success true/failed)"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 구동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("signin")
    @ResponseBody
    public String signin(@RequestBody UserDTO userdto) throws NoSuchAlgorithmException {
        JsonObject response = new JsonObject();
        Optional<User> tempUser = userRepository.findById(userdto.getUserId());
        userdto.setUserPassword(sha256.encrypt(userdto.getUserPassword()));

        log.info("Request : " + userdto.toString());

        // Check Duplicate User
        if(tempUser.isPresent()) {
            response.addProperty("success", "failed");
            response.addProperty("message", "Duplicate user");
            return response.toString();
        }

        User user = userdto.toEntity();
        try {
            User saveUser = userRepository.save(user);
            response.addProperty("success", "true");
        } catch (Exception e) {
            response.addProperty("success", "failed");
            response.addProperty("message", e.getMessage());
        }

        return response.toString();
    }

    @Operation(
            summary = "사용자 로그인",
            description = "로그인 성공여부 상태 리턴 (success true/failed)\n쿠키 발급 : userid"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 구동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("signup")
    public String signup(@RequestParam(value = "id") String userId,
                         @RequestParam(value = "password") String password,
                         HttpServletResponse servletResponse) throws NoSuchAlgorithmException{
        JsonObject response = new JsonObject();
        String crypto = sha256.encrypt(password);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            if(crypto.equals(user.get().getUserPassword())) {
                response.addProperty("success", "true");
            } else {
                response.addProperty("success", "failed");
                response.addProperty("message", "Auth");
            }
        } else {
            response.addProperty("success", "failed");
            response.addProperty("message", "NoAccount");
        }

        Cookie idcookie = new Cookie("userid", userId);
        servletResponse.addCookie(idcookie);
        return response.toString();
    }

    @Operation(
            summary = "사용자 탈퇴",
            description = "탈퇴 성공여부 상태 리턴 (success true/failed)"
    )
    @DeleteMapping("delete")
    public String delete(@RequestParam(value = "id") String userId) {
        JsonObject response = new JsonObject();
        try {
            userRepository.deleteById(userId);
            response.addProperty("success", "true");
        } catch(Exception e) {
            response.addProperty("success", "failed");
            response.addProperty("message", e.getMessage());
        }

        return response.toString();
    }

    @ApiIgnore
    @GetMapping("check")
    public String check(@CookieValue(name = "userid", required = false) String userId) {
        JsonObject response = new JsonObject();
        if(userId == null) {
            response.addProperty("success", "false");
        } else {
            response.addProperty("success", "true");
            response.addProperty("userID", userId);
        }

        return response.toString();
    }
    @Operation(
            summary = "로그아웃",
            description = "로그아웃 성공여부 상태 리턴 (success true/failed)"
    )
    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        JsonObject json = new JsonObject();

        Cookie cookie = new Cookie("userid", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        json.addProperty("success", "true");
        return json.toString();
    }


}
