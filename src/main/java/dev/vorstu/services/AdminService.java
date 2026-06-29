package dev.vorstu.services;


import dev.vorstu.dto.admin.AdminCreateDto;
import dev.vorstu.dto.admin.AdminResponseDto;
import dev.vorstu.dto.admin.AdminUpdateDto;
import dev.vorstu.entities.Admin;
import dev.vorstu.entities.User;
import dev.vorstu.enums.Role;
import dev.vorstu.mappers.AdminMapper;
import dev.vorstu.repositories.AdminRepository;
import dev.vorstu.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, UserRepository userRepository, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminResponseDto createAdmin(AdminCreateDto dto){
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("email already exists");
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getEmail(), passwordHash, Role.ADMIN);
        User savedUser = userRepository.save(user);
        Admin admin = adminMapper.toEntity(dto);
        admin.setUser(savedUser);
        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toResponseDto(savedAdmin);
    }

    @Transactional
    public AdminResponseDto changeAdmin(Long id, AdminUpdateDto dto){
        Admin admin = findAdminById(id);
        adminMapper.updateEntityFromDto(dto, admin);
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toResponseDto(savedAdmin);
    }

    @Transactional
    public void deleteAdmin(Long id){
        Admin admin = findAdminById(id);
        User user = admin.getUser();
        adminRepository.delete(admin);
        if (user != null) userRepository.delete(user);
    }

    public List<AdminResponseDto> getAllAdmins(){
        return adminRepository.findAll().stream()
                .map(adminMapper::toResponseDto)
                .toList();
    }

    public AdminResponseDto getAdminById(Long id){
        Admin admin = findAdminById(id);
        return adminMapper.toResponseDto(admin);
    }

    public Admin findAdminById(Long id){
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("admin nor found"));
    }
}
