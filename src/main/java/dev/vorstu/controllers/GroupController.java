package dev.vorstu.controllers;

import dev.vorstu.dto.group.GroupCreateDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.dto.group.GroupUpdateDto;
import dev.vorstu.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@PreAuthorize("hasRole('ADMIN')")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping
    public GroupResponseDto createGroup(@Valid @RequestBody GroupCreateDto dto){
        return groupService.createGroup(dto);
    }

    @PutMapping("/{id}")
    public GroupResponseDto updateGroup(@PathVariable Long id, @Valid @RequestBody GroupUpdateDto dto){
        return groupService.updateGroup(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
    }

    @GetMapping
    public List<GroupResponseDto> getAllGroups(){
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public GroupResponseDto getGroupById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }


}
