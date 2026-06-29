package dev.vorstu.mappers;


import dev.vorstu.dto.group.GroupCreateDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.dto.group.GroupUpdateDto;
import dev.vorstu.entities.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupCreateDto dto);
    GroupResponseDto toResponseDto(Group group);
    List<GroupResponseDto> toResponseDtoList(Iterable<Group> groups);
    void updateEntityFromDto(GroupUpdateDto dto, @MappingTarget Group group);
}
