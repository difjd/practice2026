package dev.vorstu.services;

import dev.vorstu.dto.auth.AuthResponseDto;
import dev.vorstu.dto.auth.LoginRequestDto;
import dev.vorstu.entities.User;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        boolean passwordMatches = passwordEncoder.matches(dto.getPassword(), user.getPasswordHash());
        if (!passwordMatches) throw new IllegalArgumentException("wrong password");
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, user.getId(), user.getEmail(), user.getRole());
    }
}
