package com.clubes.service.Encuestas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubes.controller.encuestas.EncuestaDTO;
import com.clubes.controller.encuestas.OpcionDTO;
import com.clubes.controller.encuestas.PreguntaDTO;
import com.clubes.model.Encuestas.Encuesta;
import com.clubes.model.Encuestas.Opcion;
import com.clubes.model.Encuestas.Pregunta;
import com.clubes.repository.Encuestas.EncuestaRepository;

import jakarta.transaction.Transactional;

@Service
public class EncuestaService {

    @Autowired
    private EncuestaRepository encuestaRepository;

    public Encuesta crearEncuesta(Encuesta encuesta) {
        return encuestaRepository.save(encuesta);
    }

@Transactional
public Encuesta guardarEncuesta(Encuesta encuesta) {
    // Verificamos si hay preguntas en la encuesta
    if (encuesta.getPreguntas() != null) {
        for (Pregunta pregunta : encuesta.getPreguntas()) {
            // Asociamos cada pregunta a la encuesta
            pregunta.setEncuesta(encuesta);

            // Verificamos si la pregunta tiene opciones y las asociamos a la pregunta
            if (pregunta.getOpciones() != null) {
                for (Opcion opcion : pregunta.getOpciones()) {
                    opcion.setPregunta(pregunta);
                }
            }
        }
    }

    // Guardamos la encuesta junto con las preguntas y opciones
    return encuestaRepository.save(encuesta);
}


    public List<EncuestaDTO> obtenerTodasLasEncuestas() {
        List<Encuesta> encuestas = encuestaRepository.findAll();
        return encuestas.stream()
            .map(this::convertToEncuestaDTO)
            .collect(Collectors.toList());
    }

    public EncuestaDTO obtenerEncuestaPorId(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
        return convertToEncuestaDTO(encuesta);
    }

    // Convertir Encuesta a EncuestaDTO
    private EncuestaDTO convertToEncuestaDTO(Encuesta encuesta) {
        EncuestaDTO dto = new EncuestaDTO();
        dto.setId(encuesta.getId());
        dto.setTitulo(encuesta.getTitulo());
        dto.setDescripcion(encuesta.getDescripcion());

        List<PreguntaDTO> preguntasDTO = encuesta.getPreguntas().stream()
            .map(this::convertToPreguntaDTO)
            .collect(Collectors.toList());
        dto.setPreguntas(preguntasDTO);

        return dto;
    }

    // Convertir Pregunta a PreguntaDTO
    private PreguntaDTO convertToPreguntaDTO(Pregunta pregunta) {
        PreguntaDTO dto = new PreguntaDTO();
        dto.setIdPregunta(pregunta.getIdPregunta());
        dto.setTextoPregunta(pregunta.getTextoPregunta());
        dto.setTipoPregunta(pregunta.getTipoPregunta());

        // Convertir opciones si las tiene
        if (pregunta.getOpciones() != null) {
            List<OpcionDTO> opcionesDTO = pregunta.getOpciones().stream()
                .map(opcion -> new OpcionDTO(opcion.getIdOpcion(), opcion.getTextoOpcion()))
                .collect(Collectors.toList());
            dto.setOpciones(opcionesDTO);
        }

        return dto;
    }

    @Transactional
    public void eliminarEncuesta(Long id) {
        Encuesta encuesta = encuestaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
    
        // Eliminar todas las preguntas y opciones relacionadas
        encuesta.getPreguntas().clear();
        encuestaRepository.deleteById(id);
    }
    
    
    public Encuesta actualizarEncuesta(Long id, Encuesta encuestaActualizada) {
        Encuesta encuestaExistente = encuestaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
    
        encuestaExistente.setTitulo(encuestaActualizada.getTitulo());
        encuestaExistente.setDescripcion(encuestaActualizada.getDescripcion());
        encuestaExistente.setPreguntas(encuestaActualizada.getPreguntas());
    
        return encuestaRepository.save(encuestaExistente);  // Guardar la encuesta actualizada
    }
}


