package com.daelim.swserver.auth.controller;

import com.daelim.swserver.auth.dto.UserDTO;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.daelim.swserver.security.SHA256;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

    private final UserRepository userRepository;

    private final SHA256 sha256 = new SHA256();

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "사용자 회원 가입",
            description = "회원 가입"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원 가입 완료"),
            @ApiResponse(code = 422, message = "이미 해당 ID의 회원이 있음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("signin")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> signin(@RequestBody @NotNull UserDTO userdto) throws NoSuchAlgorithmException {

        Optional<User> tempUser = userRepository.findById(userdto.getUserId());
        userdto.setUserPassword(sha256.encrypt(userdto.getUserPassword()));

        if (tempUser.isPresent()) {
            JsonObject response = new JsonObject();
            response.addProperty("message", "Duplicate user");

            return ResponseEntity.unprocessableEntity().body(response.toString());
        }

        User user = userdto.toEntity();
        try {
            log.info("User Register : " + user.getUserId());
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            JsonObject response = new JsonObject();

            response.addProperty("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response.toString());
        }
    }

    @Operation(
            summary = "사용자 로그인",
            description = "쿠키 발급 : userid"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 완료"),
            @ApiResponse(code = 401, message = "비밀번호 불일치\n해당 회원이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("signup")
    public ResponseEntity<String> signup(@RequestParam(value = "id") String userId,
                                         @RequestParam(value = "password") String password,
                                         HttpServletResponse servletResponse) throws NoSuchAlgorithmException {
        JsonObject response = new JsonObject();

        if (authorize(userId, password, response)) {
            User user = userRepository.findById(userId).orElseThrow();

            log.info("User Login : " + user.getUserId());

            Cookie idCookie = new Cookie("userid", userId);
            servletResponse.addCookie(idCookie);
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString());
        }
    }

    @Operation(
            summary = "사용자 탈퇴",
            description = "서비스에서 탈퇴 합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 204, message = "탈퇴 완료"),
            @ApiResponse(code = 401, message = "비밀번호 불일치\n해당 회원이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") String userId,
                                         @RequestParam(value = "password") String password) throws NoSuchAlgorithmException {
        JsonObject response = new JsonObject();

        if (authorize(userId, password, response)) {
            try {
                userRepository.deleteById(userId);
                log.info("User Deleted : " + userId);

                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                response.addProperty("message", e.getMessage());

                return ResponseEntity.internalServerError().body(response.toString());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString());
        }
    }

    /**
     * @param userId   id
     * @param password password
     * @param response fail message (if success this will empty)
     * @return true when authorized
     */
    private boolean authorize(@NotNull String userId,
                              @NotNull String password,
                              @NotNull JsonObject response) throws NoSuchAlgorithmException {
        Optional<User> user = userRepository.findById(userId);
        String crypto = sha256.encrypt(password);

        if (user.isPresent()) {
            if (crypto.equals(user.get().getUserPassword())) {
                return true;
            } else {
                response.addProperty("message", "Invalid password: " + password);

                return false;
            }
        } else {
            response.addProperty("message", "No account: " + userId);

            return false;
        }
    }

    @Operation(
            summary = "로그인 확인",
            description = "로그인 되어 있는지 확인합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 되어 있음"),
            @ApiResponse(code = 401, message = "로그인 되어 있지 않음")
    })
    @GetMapping("check")
    public ResponseEntity<String> check(@CookieValue(name = "userid", required = false) String userId) {
        JsonObject response = new JsonObject();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            response.addProperty("userID", userId);

            return ResponseEntity.ok(response.toString());
        }
    }

    @Operation(
            summary = "로그아웃",
            description = "로그아웃"
    )
    @GetMapping("logout")
    public ResponseEntity<String> logout(@NotNull HttpServletResponse response) {
        JsonObject json = new JsonObject();

        Cookie cookie = new Cookie("userid", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(json.toString());
    }

}
