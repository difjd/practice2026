package dev.vorstu.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Long groupId;
}
