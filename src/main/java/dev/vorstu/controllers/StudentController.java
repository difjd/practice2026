package dev.vorstu.controllers;


import dev.vorstu.dto.StudentCreateDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.dto.StudentUpdateDto;
import org.springframework.data.domain.Page;
import dev.vorstu.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/base")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto createStudent(@RequestBody StudentCreateDto newStudent){
        return studentService.createStudent(newStudent);
    }

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto changeStudent(@PathVariable("id") Long id, @RequestBody StudentUpdateDto changingStudent){
        return studentService.changeStudent(id, changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto getStudentByGroup(@RequestParam(value = "group") String group){
        return studentService.getStudentByGroup(group);
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }


    @GetMapping("students")
    public List<StudentResponseDto> getAllStudents(){
        return  studentService.getAllStudents();
    }

    @GetMapping(value = "students/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudentResponseDto> getStudentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        return studentService.getStudentsPage(page,size);
    }

}
