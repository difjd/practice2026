package dev.vorstu.mappers;

import dev.vorstu.dto.teacher.TeacherCreateDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.dto.teacher.TeacherUpdateDto;
import dev.vorstu.entities.Group;
import dev.vorstu.entities.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "groups")
    Teacher toEntity(TeacherCreateDto dto);

    @Mapping(source = "user.email", target = "email")
    @Mapping(expression = "java(mapGroupIds(teacher.getGroups()))", target = "groupIds")
    @Mapping(expression = "java(mapGroupNames(teacher.getGroups()))", target = "groupNames")
    TeacherResponseDto toResponseDto(Teacher teacher);

    List<TeacherResponseDto> toResponseDtoList(Iterable<Teacher> teachers);
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "groups")
    void updateEntityFromDto(TeacherUpdateDto dto, @MappingTarget Teacher teacher);

    default List<Long> mapGroupIds(List<Group> groups) {
        return groups.stream()
                .map(Group::getId)
                .toList();
    }

    default List<String> mapGroupNames(List<Group> groups) {
        return groups.stream()
                .map(Group::getName)
                .toList();
    }
}
