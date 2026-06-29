package dev.vorstu.mappers;

import dev.vorstu.dto.student.StudentCreateDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.dto.student.StudentUpdateDto;
import dev.vorstu.entities.Student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(ignore = true, target = "group")
    @Mapping(ignore = true, target = "user")
    Student toEntity(StudentCreateDto dto);

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "user.email", target = "email")
    StudentResponseDto toResponseDto(Student student);

    List<StudentResponseDto> toResponseDtoList(Iterable<Student> students);
    @Mapping(ignore = true, target = "group")
    @Mapping(ignore = true, target = "user")
    void updateEntityFromDto(StudentUpdateDto dto, @MappingTarget Student student);
}
