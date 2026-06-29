package dev.vorstu.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCreateDto {
    @NotBlank
    private String name;
}
