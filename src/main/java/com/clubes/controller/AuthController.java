package com.clubes.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.internal.CoreLogging.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.Admin;
import com.clubes.security.JwtResponse;
import com.clubes.security.JwtTokenUtil;
import com.clubes.service.imple.AdminService;
import com.clubes.service.imple.JwtUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUserDetailsService jwtuserdetails;

    @PostMapping("/register")
    public ResponseEntity<AdminDTO> register(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = adminService.saveAdmin(admin);
            AdminDTO adminDTO = new AdminDTO(savedAdmin.getId(), savedAdmin.getUsername(), savedAdmin.getPassword(), savedAdmin.getRole());
            return ResponseEntity.ok(adminDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    

     // Obtener lista de usuarios
     @GetMapping("/users")
     public ResponseEntity<List<AdminDTO>> getAllUsers() {
         List<Admin> admins = adminService.getAllAdmins();
         List<AdminDTO> adminDTOs = admins.stream()
             .map(admin -> new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getRole()))
             .collect(Collectors.toList());
         return ResponseEntity.ok(adminDTOs);
     }
     
    // Actualizar un usuario
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Admin admin) {
        try {
            adminService.updateAdmin(id, admin);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
        } catch (Exception e) {
            logger.error("Error eliminando el usuario con ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error al eliminar el usuario"));
        }
    }
    
    

    // Cambiar la contraseña de un usuario
    @PutMapping("/users/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            adminService.changePassword(id, newPassword);
            return ResponseEntity.ok("Contraseña cambiada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            // Agrega este log para obtener más detalles
            logger.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Cargar los detalles del usuario
        final UserDetails userDetails = jwtuserdetails.loadUserByUsername(authRequest.getUsername());

        // Generar y devolver el token JWT
        final String token = jwtUtil.generateToken(userDetails);

        // Devolver el token en el cuerpo de la respuesta
        return ResponseEntity.ok(new JwtResponse(token));
    }
}



