package com.cju.shoppingmall.auth.controller;

import com.cju.shoppingmall.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // ✅ 로그인 페이지: GET /login
    @GetMapping("/login")
    public String loginPage()
    {
        return "screens/login";   // templates/screens/login.html
    }

    // ✅ 로그인 API: POST /login
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> request)
    {
        try {
            String username = request.get("username");
            String password = request.get("password");

            if (username == null || password == null ||
                    username.trim().isEmpty() || password.trim().isEmpty())
            {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "아이디와 비밀번호를 입력해주세요"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtUtil.createToken(authentication.getName());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", token,
                    "username", authentication.getName(),
                    "message", "로그인 성공"
            ));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "아이디 또는 비밀번호가 틀립니다"));
        }
    }
}
