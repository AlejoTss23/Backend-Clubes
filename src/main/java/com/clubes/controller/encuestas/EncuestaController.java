package com.clubes.controller.encuestas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.Encuestas.Encuesta;
import com.clubes.service.Encuestas.EncuestaService;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    @Autowired
    private EncuestaService encuestaService;

    @GetMapping
    public List<EncuestaDTO> obtenerTodasLasEncuestas() {
        return encuestaService.obtenerTodasLasEncuestas();
    }

    @PostMapping
    public Encuesta guardarEncuesta(@RequestBody Encuesta encuesta) {
        System.out.println("Encuesta recibida: " + encuesta);
        return encuestaService.guardarEncuesta(encuesta);
    }

    @GetMapping("/{id}")
    public EncuestaDTO obtenerEncuestaPorId(@PathVariable Long id) {
        return encuestaService.obtenerEncuestaPorId(id);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarEncuesta(@PathVariable Long id) {
    encuestaService.eliminarEncuesta(id);
    return ResponseEntity.noContent().build();  // Devuelve 204 No Content si la eliminación fue exitosa
}

@PutMapping("/{id}")
public Encuesta actualizarEncuesta(@PathVariable Long id, @RequestBody Encuesta encuestaActualizada) {
    return encuestaService.actualizarEncuesta(id, encuestaActualizada);
 }
}
