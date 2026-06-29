package dev.vorstu.services;

import dev.vorstu.dto.group.GroupCreateDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.dto.group.GroupUpdateDto;
import dev.vorstu.entities.Group;
import dev.vorstu.mappers.GroupMapper;
import dev.vorstu.repositories.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Transactional
    public GroupResponseDto createGroup(GroupCreateDto dto){
        Group group = groupMapper.toEntity(dto);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toResponseDto(savedGroup);
    }

    @Transactional
    public GroupResponseDto updateGroup(Long id, GroupUpdateDto dto){
        Group group = findGroupById(id);
        groupMapper.updateEntityFromDto(dto, group);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toResponseDto(savedGroup);
    }

    @Transactional
    public void deleteGroup(Long id){
        Group group = findGroupById(id);
        groupRepository.delete(group);
    }

    public List<GroupResponseDto> getAllGroups() {
        return groupMapper.toResponseDtoList(groupRepository.findAll());
    }

    public GroupResponseDto getGroupById(Long id) {
        Group group = findGroupById(id);
        return groupMapper.toResponseDto(group);
    }


    public Group findGroupById(Long id){
        return groupRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("can`t found group"));
    }
}
