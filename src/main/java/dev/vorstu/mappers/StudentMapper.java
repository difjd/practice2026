package dev.vorstu.mappers;

import dev.vorstu.dto.StudentCreateDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.dto.StudentUpdateDto;
import dev.vorstu.entities.Student;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentCreateDto dto);
    StudentResponseDto toResponseDto(Student student);
    List<StudentResponseDto> toResponseDtoList(Iterable<Student> students);
    void updateEntityFromDto(StudentUpdateDto dto, @MappingTarget Student student);
}
