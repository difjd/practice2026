package dev.vorstu.services;

import dev.vorstu.dto.auth.*;
import dev.vorstu.entities.User;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.security.CurrentUserService;
import dev.vorstu.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CurrentUserService currentUserService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.currentUserService = currentUserService;
    }

    public JwtAuthDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        boolean passwordMatches = passwordEncoder.matches(dto.getPassword(), user.getPasswordHash());
        if (!passwordMatches) throw new IllegalArgumentException("wrong password");
        return jwtService.generateAuthToken(user.getEmail());
    }

    public JwtAuthDto refreshToken(RefreshTokenDto dto){
        String refreshToken = dto.getRefreshToken();
        if (!jwtService.validateJwtToken(refreshToken)) throw new IllegalArgumentException("invalid refresh token");
        String email = jwtService.getEmailFromToken(refreshToken);
        userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        return jwtService.refreshBaseToken(email, refreshToken);
    }

    @Transactional
    public JwtAuthDto changeEmail(ChangeEmailDto dto){
        User currentUser = currentUserService.getCurrentUser();
        if (userRepository.existsByEmail(dto.getNewEmail()))
            throw new IllegalArgumentException("email already exists");
        currentUser.setEmail(dto.getNewEmail());
        User savedUser = userRepository.save(currentUser);
        return jwtService.generateAuthToken(savedUser.getEmail());
    }

    @Transactional
    public void changePassword(ChangePasswordDto dto){
        User currentUser = currentUserService.getCurrentUser();
        boolean oldPasswordMatches = passwordEncoder.matches(
                dto.getOldPassword(),
                currentUser.getPasswordHash());
        if (!oldPasswordMatches) throw new IllegalArgumentException("wrong old password");
        String newPasswordHash = passwordEncoder.encode(dto.getNewPassword());
        currentUser.setPasswordHash(newPasswordHash);
        userRepository.save(currentUser);
    }
}
