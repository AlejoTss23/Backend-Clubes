package com.clubes.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubes.model.contactos;
import com.clubes.repository.ContactosRepository;

@Service
public class ContactosService {
    @Autowired
    private ContactosRepository contactoRepository;

    public List<contactos> listarTodos() {
        return contactoRepository.findAll();
    }

    public Optional<contactos> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public contactos guardar(contactos contacto) {
        try {
            return contactoRepository.save(contacto);
        } catch (Exception e) {
            // Log the exception details here to understand what went wrong
            System.err.println("Error saving contacto: " + e.getMessage());
            throw e; // rethrow to handle it accordingly
        }
    }

    public void eliminar(Long id) {
        try {
            contactoRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting contacto: " + e.getMessage());
            throw e;
        }
    }
}
