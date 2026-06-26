package dev.vorstu.controllers;


import dev.vorstu.dto.Student;
import dev.vorstu.repositories.StudentRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.List;


@RestController
@RequestMapping("api/base")
public class BaseController {
    private final StudentRepository studentRepository;

    public BaseController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent){
        return studentRepository.save(newStudent);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent){
        if(changingStudent.getId()==null) throw new RuntimeException("id of changing student can`t be null");
        Student student = studentRepository.findById(changingStudent.getId())
                .orElseThrow(() -> new RuntimeException("student wasn`t found, id: " + changingStudent.getId()));
        student.setFio(changingStudent.getFio());
        student.setGroup(changingStudent.getGroup());
        student.setPhoneNumber(changingStudent.getPhoneNumber());

        return studentRepository.save(student);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id){
        studentRepository.deleteById(id);
        return id;
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentByGroup(@RequestParam(value = "group") String group){
        return studentRepository.findFirstByGroup(group)
                .orElse(null);
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student wasnt found, id: " + id));
    }


    @GetMapping("students")
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

}
