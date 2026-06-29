package dev.vorstu.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String fio;
    private String phoneNumber;
    private Long groupId;
    private String groupName;
    private String email;
}
