package com.clubes.service.Encuestas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubes.controller.encuestas.EstadisticasDTO;
import com.clubes.controller.encuestas.RespuestaDTO;
import com.clubes.model.Encuestas.Respuesta;
import com.clubes.repository.Encuestas.RespuestaRepository;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;
    
    public void guardarRespuestas(List<Respuesta> respuestas) {
        for (Respuesta respuesta : respuestas) {
            respuestaRepository.save(respuesta);
        }
    }

    public List<Respuesta> obtenerRespuestasPorEncuesta(Long idEncuesta) {
        return respuestaRepository.findByEncuesta_Id(idEncuesta);
    }

        public List<Long> obtenerGruposDeRespuestas() {
        return respuestaRepository.findDistinctIdRespuestaGrupo();
    }




    public List<RespuestaDTO> obtenerRespuestasPorGrupo(Long idRespuestaGrupo) {
        List<Respuesta> respuestas = respuestaRepository.findByIdRespuestaGrupo(idRespuestaGrupo);
        return respuestas.stream().map(respuesta -> {
            RespuestaDTO dto = new RespuestaDTO();
            dto.setEncuestaId(respuesta.getEncuesta().getId());
            dto.setPreguntaId(respuesta.getPregunta().getIdPregunta());
            dto.setTextoPregunta(respuesta.getPregunta().getTextoPregunta());  // Aquí agregas el texto de la pregunta
            dto.setTextoOpcion(respuesta.getOpcion() != null ? respuesta.getOpcion().getTextoOpcion() : null);  // Aquí se agrega el texto de la opción
            dto.setRespuestaTexto(respuesta.getRespuestaTexto());
            dto.setRespuestaNumero(respuesta.getRespuestaNumero());
            return dto;
        }).collect(Collectors.toList());
    }
    
    

    private RespuestaDTO convertirARespuestaDTO(Respuesta respuesta) {
        RespuestaDTO dto = new RespuestaDTO();
        dto.setEncuestaId(respuesta.getEncuesta().getId());
        dto.setPreguntaId(respuesta.getPregunta().getIdPregunta());
        dto.setTextoOpcion(respuesta.getOpcion() != null ? respuesta.getOpcion().getTextoOpcion() : null);
        dto.setRespuestaTexto(respuesta.getRespuestaTexto());
        dto.setRespuestaNumero(respuesta.getRespuestaNumero());
        return dto;
    }

public Map<String, Object> generarEstadisticasPorEncuesta(Long idEncuesta) {
    List<Respuesta> respuestas = respuestaRepository.findByEncuesta_Id(idEncuesta);
    
    // Estadísticas para preguntas de opción múltiple
    Map<String, Integer> opcionesConteo = new HashMap<>();
    for (Respuesta respuesta : respuestas) {
        if (respuesta.getOpcion() != null) {
            String textoOpcion = respuesta.getOpcion().getTextoOpcion();
            opcionesConteo.put(textoOpcion, opcionesConteo.getOrDefault(textoOpcion, 0) + 1);
        }
    }

    // Estadísticas para preguntas numéricas o de texto (puedes agregar lógica adicional)
    Map<String, Object> estadisticas = new HashMap<>();
    estadisticas.put("opcionesConteo", opcionesConteo);
    
    return estadisticas;
}


public List<EstadisticasDTO> obtenerEstadisticasPorEncuesta(Long idEncuesta) {
    List<Respuesta> respuestas = respuestaRepository.findByEncuesta_Id(idEncuesta);
    
    Map<String, Map<String, Integer>> estadisticasPorPregunta = new HashMap<>();

    for (Respuesta respuesta : respuestas) {
        String textoPregunta = respuesta.getPregunta().getTextoPregunta();
        String textoOpcion = respuesta.getOpcion() != null ? respuesta.getOpcion().getTextoOpcion() : respuesta.getRespuestaTexto();

        // Verifica que no sea un valor numérico si la opción debe ser un texto
        if (textoOpcion != null && !textoOpcion.matches("\\d+")) {
            estadisticasPorPregunta.putIfAbsent(textoPregunta, new HashMap<>());
            Map<String, Integer> conteoOpciones = estadisticasPorPregunta.get(textoPregunta);
            conteoOpciones.put(textoOpcion, conteoOpciones.getOrDefault(textoOpcion, 0) + 1);
        } else {
            System.out.println("Opción numérica detectada: " + textoOpcion + " para la pregunta: " + textoPregunta);
        }
    }

    List<EstadisticasDTO> estadisticasList = new ArrayList<>();
    for (Map.Entry<String, Map<String, Integer>> entry : estadisticasPorPregunta.entrySet()) {
        EstadisticasDTO dto = new EstadisticasDTO();
        dto.setPregunta(entry.getKey());
        dto.setOpcionesConteo(entry.getValue());
        estadisticasList.add(dto);
    }

    return estadisticasList;
}

public Respuesta findById(Long id) {
    return respuestaRepository.findById(id).orElse(null);
}

public List<RespuestaDTO> obtenerRespuestasPorIds(List<Long> ids) {
    List<Respuesta> respuestas = respuestaRepository.findRespuestasByGrupoIds(ids);

    return respuestas.stream().map(respuesta -> {
        String textoPregunta = respuesta.getPregunta() != null ? respuesta.getPregunta().getTextoPregunta() : "Pregunta no proporcionada";
        String textoOpcion = respuesta.getOpcion() != null ? respuesta.getOpcion().getTextoOpcion() : "Opción no proporcionada";

        return new RespuestaDTO(
            respuesta.getEncuesta().getId(),
            respuesta.getPregunta() != null ? respuesta.getPregunta().getIdPregunta() : null,
            textoPregunta,
            textoOpcion,
            respuesta.getRespuestaTexto(),
            respuesta.getRespuestaNumero(),
            respuesta.getIdRespuestaGrupo()
        );
    }).collect(Collectors.toList());
  }
}
