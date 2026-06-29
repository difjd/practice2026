package dev.vorstu.services;


import dev.vorstu.dto.StudentCreateDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.dto.StudentUpdateDto;
import dev.vorstu.entities.Student;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto createStudent(StudentCreateDto newStudentDto){
        Student student = studentMapper.toEntity(newStudentDto);
        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponseDto(savedStudent);
    }

    public StudentResponseDto changeStudent(Long id, StudentUpdateDto changingStudentDto){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student wasn`t found, id: " + id));
        studentMapper.updateEntityFromDto(changingStudentDto, student);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }

    public Long deleteStudent(Long id){
        if (!studentRepository.existsById(id)) throw new RuntimeException("student wasn`t found, id: " + id);
        studentRepository.deleteById(id);
        return id;
    }

    public StudentResponseDto getStudentByGroup(String group){
        Student student = studentRepository.findFirstByGroup(group)
                .orElse(null);
        if(student==null) return null;
        return studentMapper.toResponseDto(student);
    }

    public StudentResponseDto getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student wasnt found, id: " + id));
        return studentMapper.toResponseDto(student);
    }

    public List<StudentResponseDto> getAllStudents(){
        return studentMapper.toResponseDtoList(studentRepository.findAll());
    }

    public Page<StudentResponseDto> getStudentsPage(int page, int size){
        return studentRepository.findAll(PageRequest.of(page, size))
                .map(studentMapper::toResponseDto);
    }
}
