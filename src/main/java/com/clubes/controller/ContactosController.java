package com.clubes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.contactos;
import com.clubes.service.imple.ContactosService;

@RestController
@RequestMapping("/contactos")
public class ContactosController {
    @Autowired
    private ContactosService contactoService;

    @GetMapping
    public List<contactos> listarTodos() {
        return contactoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<contactos> buscarPorId(@PathVariable Long id) {
        Optional<contactos> contacto = contactoService.buscarPorId(id);
        return contacto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<contactos> guardar(@RequestBody contactos contacto) {
        try {
            contactos nuevoContacto = contactoService.guardar(contacto);
            return new ResponseEntity<>(nuevoContacto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<contactos> actualizar(@PathVariable Long id, @RequestBody contactos contacto) {
        return contactoService.buscarPorId(id)
                .map(c -> {
                    contacto.setId(id);
                    contactos actualizado = contactoService.guardar(contacto);
                    return new ResponseEntity<>(actualizado, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return contactoService.buscarPorId(id)
                .map(c -> {
                    contactoService.eliminar(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
