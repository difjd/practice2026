package dev.vorstu.dto.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherResponseDto {
    private Long id;
    private String fio;
    private String phoneNumber;
    private String email;
    private List<Long> groupIds;
    private List<String> groupNames;
}
