package dev.vorstu.services;


import dev.vorstu.dto.teacher.TeacherCreateDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.dto.teacher.TeacherUpdateDto;
import dev.vorstu.entities.Group;
import dev.vorstu.entities.Teacher;
import dev.vorstu.entities.User;
import dev.vorstu.enums.Role;
import dev.vorstu.mappers.TeacherMapper;
import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, GroupService groupService, TeacherMapper teacherMapper, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.teacherMapper = teacherMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public TeacherResponseDto createTeacher(TeacherCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("email already exists");
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getEmail(), passwordHash, Role.TEACHER);
        User savedUser = userRepository.save(user);
        Teacher teacher = teacherMapper.toEntity(dto);
        teacher.setUser(savedUser);
        Teacher savedTeacher = teacherRepository.save(teacher);

        return teacherMapper.toResponseDto(savedTeacher);
    }

    @Transactional
    public TeacherResponseDto updateTeacher(Long id, TeacherUpdateDto dto){
        Teacher teacher = findTeacherById(id);
        teacherMapper.updateEntityFromDto(dto, teacher);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDto(savedTeacher);
    }

    @Transactional
    public void deleteTeacher(Long id){
        Teacher teacher = findTeacherById(id);
        User user = teacher.getUser();
        teacherRepository.delete(teacher);
        if (user != null) userRepository.delete(user);
    }

    @Transactional
    public TeacherResponseDto addGroup(Long teacherId, Long groupId){
        Teacher teacher = findTeacherById(teacherId);
        Group group = groupService.findGroupById(groupId);
        boolean alreadyAssigned = teacher.getGroups().stream().anyMatch(g -> g.getId().equals(groupId));
        if (!alreadyAssigned) teacher.getGroups().add(group);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDto(savedTeacher);
    }

    @Transactional
    public TeacherResponseDto removeGroup(Long teacherId, Long groupId){
        Teacher teacher = findTeacherById(teacherId);
        teacher.getGroups().removeIf(g -> g.getId().equals(groupId));
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDto(savedTeacher);
    }

    public List<TeacherResponseDto> getAllTeachers(){
        return teacherMapper.toResponseDtoList(teacherRepository.findAll());
    }

    public TeacherResponseDto getTeacherById(Long id){
        Teacher teacher = findTeacherById(id);
        return teacherMapper.toResponseDto(teacher);
    }

    public Teacher findTeacherById(Long id){
        return teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("teacher not found, id: " + id));
    }


}
