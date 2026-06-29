package dev.vorstu.mappers;

import dev.vorstu.dto.admin.AdminCreateDto;
import dev.vorstu.dto.admin.AdminResponseDto;
import dev.vorstu.dto.admin.AdminUpdateDto;
import dev.vorstu.entities.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(ignore = true, target = "user")
    Admin toEntity(AdminCreateDto dto);

    @Mapping(source = "user.email", target = "email")
    AdminResponseDto toResponseDto(Admin admin);

    @Mapping(ignore = true, target = "user")
    void updateEntityFromDto(AdminUpdateDto dto, @MappingTarget Admin admin);
}
