package dev.vorstu.services;

import dev.vorstu.dto.student.StudentCreateDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entities.Group;
import dev.vorstu.entities.Student;
import dev.vorstu.entities.User;
import dev.vorstu.enums.Role;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupService groupService;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private StudentService studentService;

    @Test
    void createStudentTest() {
        StudentCreateDto createDto = new StudentCreateDto();
        createDto.setFio("Иванов Иван");
        createDto.setPhoneNumber("+10");
        createDto.setGroupId(1L);
        createDto.setEmail("student@mail.com");
        createDto.setPassword("12345");

        Group group = new Group("VM");
        group.setId(1L);
        User user = new User("student@mail.com", "hash123", Role.STUDENT);
        user.setId(1L);

        Student student = new Student();
        student.setFio("Иванов Иван");
        student.setPhoneNumber("+10");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setFio("Иванов Иван");
        savedStudent.setPhoneNumber("+10");
        savedStudent.setGroup(group);
        savedStudent.setUser(user);

        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(1L);
        responseDto.setFio("Иванов Иван");
        responseDto.setPhoneNumber("+10");
        responseDto.setGroupId(1L);
        responseDto.setGroupName("VM");
        responseDto.setEmail("student@mail.com");

        when(userRepository.existsByEmail("student@mail.com")).thenReturn(false);
        when(groupService.findGroupById(1L)).thenReturn(group);
        when(passwordEncoder.encode("12345")).thenReturn("hash123");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(studentMapper.toEntity(createDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(savedStudent);
        when(studentMapper.toResponseDto(savedStudent)).thenReturn(responseDto);
        StudentResponseDto result = studentService.createStudent(createDto);

        assertEquals(1L, result.getId());
        assertEquals("Иванов Иван", result.getFio());
        assertEquals("+10", result.getPhoneNumber());
        assertEquals(1L, result.getGroupId());
        assertEquals("VM", result.getGroupName());
        assertEquals("student@mail.com", result.getEmail());
    }

    @Test
    void getStudentByIdTest() {
        Long id = 1L;
        Group group = new Group("AM");
        group.setId(2L);
        User user = new User("petrov@mail.com", "hash", Role.STUDENT);
        user.setId(2L);

        Student student = new Student();
        student.setId(id);
        student.setFio("Петров Петр");
        student.setPhoneNumber("+78");
        student.setGroup(group);
        student.setUser(user);

        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(id);
        responseDto.setFio("Петров Петр");
        responseDto.setPhoneNumber("+78");
        responseDto.setGroupId(2L);
        responseDto.setGroupName("AM");
        responseDto.setEmail("petrov@mail.com");

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentMapper.toResponseDto(student)).thenReturn(responseDto);
        StudentResponseDto result = studentService.getStudentById(id);

        assertEquals(1L, result.getId());
        assertEquals("Петров Петр", result.getFio());
        assertEquals("+78", result.getPhoneNumber());
        assertEquals(2L, result.getGroupId());
        assertEquals("AM", result.getGroupName());
        assertEquals("petrov@mail.com", result.getEmail());
    }

    @Test
    void getStudentByIdNotFoundTest() {
        Long id = 999L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(id));
    }
}


