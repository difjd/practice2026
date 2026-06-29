package dev.vorstu.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
