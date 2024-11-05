package com.clubes.controller.encuestas;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.Encuestas.Encuesta;
import com.clubes.model.Encuestas.Opcion;
import com.clubes.model.Encuestas.Pregunta;
import com.clubes.model.Encuestas.Respuesta;
import com.clubes.repository.Encuestas.RespuestaRepository;
import com.clubes.service.Encuestas.RespuestaService;


@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

       @Autowired
    private RespuestaRepository respuestaRepository;

    // Endpoint para guardar una lista de respuestas
    @PostMapping
    public ResponseEntity<Void> guardarRespuestas(@RequestBody List<RespuestaDTO> respuestasDTO) {
        List<Respuesta> respuestas = new ArrayList<>();
        
        // Generar un ID único para el grupo de respuestas
        Long idRespuestaGrupo = System.currentTimeMillis();  // Puedes usar el tiempo actual como ID único
    
        for (RespuestaDTO dto : respuestasDTO) {
            Respuesta respuesta = new Respuesta();
            Encuesta encuesta = new Encuesta();
            encuesta.setId(dto.getEncuestaId());
            respuesta.setEncuesta(encuesta);
    
            Pregunta pregunta = new Pregunta();
            pregunta.setIdPregunta(dto.getPreguntaId());
            respuesta.setPregunta(pregunta);
    
            // Asignar el idRespuestaGrupo a cada respuesta
            respuesta.setIdRespuestaGrupo(idRespuestaGrupo);
    
            if (dto.getOpcionIds() != null && !dto.getOpcionIds().isEmpty()) {
                for (Long opcionId : dto.getOpcionIds()) {
                    Opcion opcion = new Opcion();
                    opcion.setIdOpcion(opcionId);
                    Respuesta nuevaRespuesta = new Respuesta();
                    nuevaRespuesta.setEncuesta(encuesta);
                    nuevaRespuesta.setPregunta(pregunta);
                    nuevaRespuesta.setOpcion(opcion);
                    nuevaRespuesta.setIdRespuestaGrupo(idRespuestaGrupo);  // Asignar el mismo grupo
                    respuestas.add(nuevaRespuesta);
                }
            } else {
                respuesta.setRespuestaTexto(dto.getRespuestaTexto());
                respuesta.setRespuestaNumero(dto.getRespuestaNumero());
                respuestas.add(respuesta);
            }
        }
    
        respuestaService.guardarRespuestas(respuestas);
        return ResponseEntity.ok().build();
    }
    
    
    // Endpoint para obtener todas las respuestas de una encuesta específica
    @GetMapping("/{idEncuesta}")
    public ResponseEntity<List<RespuestaDTO>> obtenerRespuestasPorEncuesta(@PathVariable("idEncuesta") Long idEncuesta) {
        List<Object[]> respuestasConOpciones = respuestaRepository.obtenerRespuestasConOpcionTexto(idEncuesta);
        List<RespuestaDTO> respuestasDTO = new ArrayList<>();
    
        for (Object[] row : respuestasConOpciones) {
            Respuesta respuesta = (Respuesta) row[0];
            String textoOpcion = (String) row[1];
    
            RespuestaDTO dto = new RespuestaDTO();
            dto.setEncuestaId(respuesta.getEncuesta().getId());
            dto.setPreguntaId(respuesta.getPregunta().getIdPregunta());
            dto.setTextoPregunta(respuesta.getPregunta().getTextoPregunta()); 
            dto.setTextoOpcion(textoOpcion);  // Guardamos el texto de la opción
            dto.setRespuestaTexto(respuesta.getRespuestaTexto());
            dto.setRespuestaNumero(respuesta.getRespuestaNumero());
    
            respuestasDTO.add(dto);
        }
    
        return new ResponseEntity<>(respuestasDTO, HttpStatus.OK);
    }

    @GetMapping("/grupo/{idRespuestaGrupo}")
    public ResponseEntity<List<RespuestaDTO>> obtenerRespuestasPorGrupo(@PathVariable("idRespuestaGrupo") Long idRespuestaGrupo) {
        List<RespuestaDTO> respuestas = respuestaService.obtenerRespuestasPorGrupo(idRespuestaGrupo);
        return ResponseEntity.ok(respuestas);
    }

    // Endpoint para obtener los grupos de respuestas
    @GetMapping("/grupos")
    public ResponseEntity<List<Long>> obtenerGruposDeRespuestas() {
        List<Long> grupos = respuestaService.obtenerGruposDeRespuestas();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/estadisticas/{idEncuesta}")
    public ResponseEntity<List<EstadisticasDTO>> obtenerEstadisticasPorEncuesta(@PathVariable Long idEncuesta) {
        List<EstadisticasDTO> estadisticas = respuestaService.obtenerEstadisticasPorEncuesta(idEncuesta);
        return ResponseEntity.ok(estadisticas);
    }
    
    @PostMapping("/obtenerRespuestasPorIds")
    public ResponseEntity<List<RespuestaDTO>> obtenerRespuestasPorIds(@RequestBody List<Long> ids) {
        List<RespuestaDTO> respuestas = respuestaService.obtenerRespuestasPorIds(ids);
        System.out.println("Respuestas obtenidas: " + respuestas);  // Log para verificar el contenido de las respuestas
        return ResponseEntity.ok(respuestas);
    }
    
    
}

