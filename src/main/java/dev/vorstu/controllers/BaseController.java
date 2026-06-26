package dev.vorstu.controllers;


import dev.vorstu.entities.Student;
import org.springframework.data.domain.Page;
import dev.vorstu.services.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/base")
public class BaseController {
    private final StudentService studentService;

    public BaseController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent){
        return studentService.createStudent(newStudent);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent){
        return studentService.changeStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentByGroup(@RequestParam(value = "group") String group){
        return studentService.getStudentByGroup(group);
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }


    @GetMapping("students")
    public List<Student> getAllStudents(){
        return  studentService.getAllStudents();
    }

    @GetMapping(value = "students/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Student> getStudentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        return studentService.getStudentsPage(page,size);
    }

}
