package dev.vorstu.services;

import dev.vorstu.dto.StudentCreateDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.entities.Student;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @InjectMocks
    private StudentService studentService;

    @Test
    void createStudentTest() {
        StudentCreateDto createDto = new StudentCreateDto("Иванов Иван", "VM", "+10");
        Student student = new Student("Иванов Иван", "VM", "+10");
        Student savedStudent = new Student("Иванов Иван", "VM", "+10");
        savedStudent.setId(1L);
        StudentResponseDto responseDto = new StudentResponseDto(1L, "Иванов Иван", "VM", "+10");

        when(studentMapper.toEntity(createDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(savedStudent);
        when(studentMapper.toResponseDto(savedStudent)).thenReturn(responseDto);
        StudentResponseDto result = studentService.createStudent(createDto);

        assertEquals(1L, result.getId());
        assertEquals("Иванов Иван", result.getFio());
        assertEquals("VM", result.getGroup());
        assertEquals("+10", result.getPhoneNumber());
    }

    @Test
    void getStudentByIdTest() {
        Long id = 1L;
        Student student = new Student("Петров Петр", "AM", "+78");
        student.setId(id);
        StudentResponseDto responseDto = new StudentResponseDto(id, "Петров Петр", "AM", "+78");

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentMapper.toResponseDto(student)).thenReturn(responseDto);
        StudentResponseDto result = studentService.getStudentById(id);

        assertEquals(1L, result.getId());
        assertEquals("Петров Петр", result.getFio());
        assertEquals("AM", result.getGroup());
        assertEquals("+78", result.getPhoneNumber());
    }

    @Test
    void getStudentByIdNotFoundTest() {
        Long id = 99L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.getStudentById(id));
    }

}
