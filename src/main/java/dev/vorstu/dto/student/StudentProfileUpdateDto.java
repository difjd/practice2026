package dev.vorstu.dto.student;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileUpdateDto {
    @NotBlank
    private String fio;
    @NotBlank
    private String phoneNumber;

}
