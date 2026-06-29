package dev.vorstu.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phoneNumber;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
