package dev.vorstu.controllers;


import dev.vorstu.dto.student.StudentCreateDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.dto.student.StudentProfileUpdateDto;
import dev.vorstu.dto.student.StudentUpdateDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import dev.vorstu.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my-group")
    public List<StudentResponseDto> getMyGroupStudents(){
        return studentService.getMyGroupStudents();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/me")
    public StudentResponseDto updateMe(@Valid @RequestBody StudentProfileUpdateDto dto){
        return studentService.updateMe(dto);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teacher-groups")
    public List<StudentResponseDto> getStudentsFromMyTeacherGroups(){
        return studentService.getStudentsFromMyTeacherGroups();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{id}/by-teacher")
    public StudentResponseDto updateStudentFromMyGroups(@PathVariable Long id, @Valid @RequestBody StudentProfileUpdateDto dto){
        return studentService.updateStudentFromMyGroups(id, dto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StudentResponseDto createStudent(@RequestBody StudentCreateDto newStudent){
        return studentService.createStudent(newStudent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto changeStudent(@PathVariable("id") Long id, @RequestBody StudentUpdateDto changingStudent){
        return studentService.changeStudent(id, changingStudent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id){
        return studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentResponseDto> getStudentByGroup(@RequestParam(value = "group") Long groupId){
        return studentService.getStudentsByGroupId(groupId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<StudentResponseDto> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudentResponseDto> getStudentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        return studentService.getStudentsPage(page,size);
    }

}
