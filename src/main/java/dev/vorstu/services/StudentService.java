package dev.vorstu.services;


import dev.vorstu.dto.student.StudentCreateDto;
import dev.vorstu.dto.student.StudentResponseDto;

import dev.vorstu.dto.student.StudentProfileUpdateDto;
import dev.vorstu.dto.student.StudentUpdateDto;
import dev.vorstu.entities.Group;
import dev.vorstu.entities.Student;
import dev.vorstu.entities.Teacher;
import dev.vorstu.entities.User;
import dev.vorstu.enums.Role;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRepository;

import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;


    public StudentService(StudentRepository studentRepository, UserRepository userRepository, GroupService groupService, StudentMapper studentMapper, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.studentMapper = studentMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;

    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getMyGroupStudents(){
        User currentUser = currentUserService.getCurrentUser();
        return studentRepository.findGroupmatesByStudentUserId(currentUser.getId())
                .map(studentMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public StudentResponseDto updateMe(StudentProfileUpdateDto dto){
        Student currentStudent = getCurrentStudent();
        currentStudent.setFio(dto.getFio());
        currentStudent.setPhoneNumber(dto.getPhoneNumber());
        Student savedStudent = studentRepository.save(currentStudent);
        return studentMapper.toResponseDto(savedStudent);
    }

    public Student getCurrentStudent(){
        User currentUser = currentUserService.getCurrentUser();
        return studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("student profile not found"));
    }

    public List<StudentResponseDto> getStudentsFromMyTeacherGroups(){
        User currentUser = currentUserService.getCurrentUser();
        return studentRepository.findStudentsByTeacherUserId(currentUser.getId())
                .map(studentMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public StudentResponseDto updateStudentFromMyGroups(Long studentId, StudentProfileUpdateDto dto) {
        User currentUser = currentUserService.getCurrentUser();
        boolean allowed = studentRepository.existsStudentInTeacherGroups(studentId, currentUser.getId());
        if (!allowed) throw new AccessDeniedException("teacher can edit only students from assigned groups");
        Student student = findStudentById(studentId);
        student.setFio(dto.getFio());
        student.setPhoneNumber(dto.getPhoneNumber());
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }

    @Transactional
    public StudentResponseDto createStudent(StudentCreateDto dto){
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("email already exists");
        Group group = groupService.findGroupById(dto.getGroupId());
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getEmail(), passwordHash, Role.STUDENT);
        User savedUser = userRepository.save(user);

        Student student = studentMapper.toEntity(dto);
        student.setGroup(group);
        student.setUser(savedUser);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }

    @Transactional
    public StudentResponseDto changeStudent(Long id, StudentUpdateDto dto){
        Student student = findStudentById(id);
        Group group = groupService.findGroupById(dto.getGroupId());
        studentMapper.updateEntityFromDto(dto, student);
        student.setGroup(group);

        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }

    @Transactional
    public Long deleteStudent(Long id){
        Student student = findStudentById(id);
        User user = student.getUser();
        studentRepository.delete(student);
        if(user!=null) userRepository.delete(user);
        return id;
    }

    public List<StudentResponseDto> getAllStudents(){
        return studentMapper.toResponseDtoList(studentRepository.findAll());
    }

    public Page<StudentResponseDto> getStudentsPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable)
                .map(studentMapper::toResponseDto);
    }

    public StudentResponseDto getStudentById(Long id) {
        Student student = findStudentById(id);
        return studentMapper.toResponseDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByGroupId(Long groupId){
        return studentRepository.findAllByGroupId(groupId)
                .map(studentMapper::toResponseDto)
                .toList();
    }


    public Student findStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("student not found, id: " + id));
    }
}
