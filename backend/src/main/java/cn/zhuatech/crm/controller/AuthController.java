/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.dto.AuthDto.*;
import cn.zhuatech.crm.repository.UserRepository;
import cn.zhuatech.crm.security.JwtService;
import cn.zhuatech.crm.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager auth; private final JwtService jwt; private final UserRepository users; private final CurrentUserService current;
    public AuthController(AuthenticationManager auth, JwtService jwt, UserRepository users, CurrentUserService current) { this.auth=auth; this.jwt=jwt; this.users=users; this.current=current; }
    @PostMapping("/login") public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        auth.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        var user = users.findByUsername(req.username()).orElseThrow(); return ApiResponse.ok("登录成功", new LoginResponse(jwt.generate(user.getUsername()), UserView.from(user)));
    }
    @GetMapping("/me") public ApiResponse<UserView> me() { return ApiResponse.ok(UserView.from(current.get())); }
}
