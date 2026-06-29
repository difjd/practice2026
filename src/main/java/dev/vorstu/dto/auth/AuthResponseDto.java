package dev.vorstu.dto.auth;

import dev.vorstu.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private Long userId;
    private String email;
    private Role role;
}
