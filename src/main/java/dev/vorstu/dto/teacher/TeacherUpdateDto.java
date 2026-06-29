package dev.vorstu.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherUpdateDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phoneNumber;
}
