package dev.vorstu.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponseDto {
    private Long id;
    private String name;
}