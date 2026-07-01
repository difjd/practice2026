package dev.vorstu.controllers;

import dev.vorstu.dto.teacher.TeacherCreateDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.dto.teacher.TeacherUpdateDto;
import dev.vorstu.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@PreAuthorize("hasRole('ADMIN')")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public TeacherResponseDto createTeacher(@Valid @RequestBody TeacherCreateDto dto){
        return teacherService.createTeacher(dto);
    }

    @PutMapping("/{id}")
    public TeacherResponseDto updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherUpdateDto dto) {
        return teacherService.updateTeacher(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }

    @PatchMapping("/{teacherId}/groups/{groupId}")
    public TeacherResponseDto addGroup(@PathVariable Long teacherId, @PathVariable Long groupId){
        return teacherService.addGroup(teacherId, groupId);
    }

    @DeleteMapping("/{teacherId}/groups/{groupId}")
    public TeacherResponseDto removeGroup(@PathVariable Long teacherId, @PathVariable Long groupId){
        return teacherService.removeGroup(teacherId, groupId);
    }

    @GetMapping
    public List<TeacherResponseDto> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherResponseDto getTeacherById(@PathVariable Long id){
        return teacherService.getTeacherById(id);
    }

}
