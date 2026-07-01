package dev.vorstu.controllers;

import dev.vorstu.dto.auth.*;

import dev.vorstu.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtAuthDto login(@Valid @RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }

    @PostMapping("/refresh")
    public JwtAuthDto refresh(@RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refreshToken(refreshTokenDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me/email")
    public JwtAuthDto changeEmail(@Valid @RequestBody ChangeEmailDto dto) {
        return authService.changeEmail(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me/password")
    public void changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        authService.changePassword(dto);
    }
}