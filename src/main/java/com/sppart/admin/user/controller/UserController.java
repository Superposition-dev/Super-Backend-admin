package com.sppart.admin.user.controller;

import com.sppart.admin.user.dto.CurrentUser;
import com.sppart.admin.user.dto.LoginRequest;
import com.sppart.admin.user.dto.LogoutDto;
import com.sppart.admin.user.service.UserService;
import com.sppart.admin.utils.CookieUtils;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@CurrentUser String id, @CookieValue(name = CookieUtils.COOKIE_HEADER_NAME) String rt) {
        LogoutDto logoutDto = LogoutDto.builder()
                .id(id)
                .rt(rt)
                .build();

        userService.logout(logoutDto);
    }

    @GetMapping(value = "/regenerateToken")
    public ResponseEntity<?> regenerateToken(
            @CookieValue(name = CookieUtils.COOKIE_HEADER_NAME, required = false) String rt) {
        return userService.regenerateToken(rt);
    }

    @GetMapping("/admin/test")
    public String adminTest(@AuthenticationPrincipal User user) {
        System.out.println("current user.getUserName()= " + user.getUsername());
        System.out.println("current user.getAuthorities()= " + user.getAuthorities());

        return "admin authorize";
    }

    @GetMapping("/manager/test")
    public String managerTest(@AuthenticationPrincipal User user) {
        System.out.println("current user.getUserName()= " + user.getUsername());
        System.out.println("current user.getAuthorities()= " + user.getAuthorities());

        return "manager authorize";
    }

    @GetMapping("/guest/test")
    public String guestTest(@AuthenticationPrincipal User user) {
        System.out.println("current user.getUserName()= " + user.getUsername());
        System.out.println("current user.getAuthorities()= " + user.getAuthorities());

        return "guest authorize";
    }
}
