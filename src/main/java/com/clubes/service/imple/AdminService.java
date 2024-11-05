package com.clubes.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clubes.model.Admin;
import com.clubes.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveAdmin(Admin admin) {
        // Asegurarse de que los campos username y password no estén vacíos
        if (admin.getUsername() == null || admin.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        
        // Encriptar la contraseña antes de guardarla en la base de datos
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void updateAdmin(Long id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        existingAdmin.setUsername(admin.getUsername());
        existingAdmin.setRole(admin.getRole());
        adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }
    
    public void changePassword(Long id, String newPassword) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
    }

}


