package dev.vorstu.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phoneNumber;
}
