package dev.vorstu.controllers;

import dev.vorstu.dto.admin.AdminCreateDto;
import dev.vorstu.dto.admin.AdminResponseDto;
import dev.vorstu.dto.admin.AdminUpdateDto;
import dev.vorstu.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public AdminResponseDto createAdmin(@Valid @RequestBody AdminCreateDto dto){
        return adminService.createAdmin(dto);
    }

    @PutMapping("/{id}")
    public AdminResponseDto updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminUpdateDto dto){
        return adminService.changeAdmin(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
    }

    @GetMapping
    public List<AdminResponseDto> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminResponseDto getAdminById(@PathVariable Long id){
        return adminService.getAdminById(id);
    }


}
