package dev.vorstu.dto.admin;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponseDto {
    private Long id;
    private String fio;
    private String phoneNumber;
    private String email;
}
