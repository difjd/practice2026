package dev.vorstu.services;


import dev.vorstu.entities.Student;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student newStudent){
        return studentRepository.save(newStudent);
    }

    public Student changeStudent(Student changingStudent){
        if(changingStudent.getId()==null) throw new RuntimeException("id of changing student can`t be null");
        Student student = studentRepository.findById(changingStudent.getId())
                .orElseThrow(() -> new RuntimeException("student wasn`t found, id: " + changingStudent.getId()));
        student.setFio(changingStudent.getFio());
        student.setGroup(changingStudent.getGroup());
        student.setPhoneNumber(changingStudent.getPhoneNumber());

        return studentRepository.save(student);
    }

    public Long deleteStudent(Long id){
        studentRepository.deleteById(id);
        return id;
    }

    public Student getStudentByGroup(String group){
        return studentRepository.findFirstByGroup(group)
                .orElse(null);
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student wasnt found, id: " + id));
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Page<Student> getStudentsPage(int page, int size){
        return studentRepository.findAll(PageRequest.of(page, size));
    }
}
