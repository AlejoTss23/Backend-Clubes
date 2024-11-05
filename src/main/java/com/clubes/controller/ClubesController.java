package com.clubes.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.clubes.controller.encuestas.RespuestaDTO;
import com.clubes.model.ClubDTO;
import com.clubes.model.clubes;
import com.clubes.model.Encuestas.Respuesta;
import com.clubes.repository.Encuestas.RespuestaRepository;
import com.clubes.service.ClubesService;
import com.clubes.service.Encuestas.RespuestaService;
import com.clubes.service.imple.excelService;

@RestController
@RequestMapping("/clubes")
public class ClubesController {

    @Autowired
    private ClubesService service;

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private excelService excelService;

    @GetMapping
    public ResponseEntity<List<clubes>> listar() {
        List<clubes> obj = service.listar();
        return new ResponseEntity<List<clubes>>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody clubes clubes) {
        clubes obj = service.registrar(clubes);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdClubes()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<clubes> actualizar(@RequestBody clubes clubes) {
        clubes obj = service.actualizar(clubes);
        return new ResponseEntity<clubes>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        clubes obj = service.ListarPorId(id);
        if (obj == null) {
            throw new Exception("No se encontro ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<clubes> listarPorId(@PathVariable("id") Integer clubes) throws Exception {
        clubes obj = service.ListarPorId(clubes);
        if (obj == null) {
            throw new Exception("No se encontro ID");
        }
        return new ResponseEntity<clubes>(obj, HttpStatus.OK);
    }
    @GetMapping("/pageable")
    public ResponseEntity<Page<clubes>> listPageable(Pageable pageable) {
        Page<clubes> clubes = service.listPageable(pageable);
        return new ResponseEntity<Page<clubes>>(clubes, HttpStatus.OK);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            excelService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body("Archivo cargado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Nuevo endpoint para obtener nombres y fechas de fundación
   @GetMapping("/nombres-fechas")
public ResponseEntity<List<ClubDTO>> listarNombresYFechas() {
    List<ClubDTO> nombresFechas = service.listarNombresYFechas();
    return new ResponseEntity<>(nombresFechas, HttpStatus.OK);
}
 

@PostMapping("/asociar-respuesta")
public ResponseEntity<?> asociarRespuestaAClub(@RequestBody ClubRespuestaDTO dto) {
    // Encontrar el club por ID
    clubes club = service.ListarPorId(dto.getIdClubes().intValue());
    if (club == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club no encontrado");
    }

    // Obtener las respuestas por idRespuestaGrupo
    List<RespuestaDTO> respuestasDTO = respuestaService.obtenerRespuestasPorGrupo(dto.getIdRespuestaGrupo());
    if (respuestasDTO.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron respuestas en el grupo");
    }

    // Convertir RespuestaDTO a Respuesta y asociar las respuestas
    respuestasDTO.forEach(dtoRespuesta -> {
        Respuesta respuesta = new Respuesta();
        respuesta.setIdRespuestaGrupo(dtoRespuesta.getIdRespuestaGrupo());
        respuesta.setTextoPregunta(dtoRespuesta.getTextoPregunta());
        respuesta.setTextoOpcion(dtoRespuesta.getTextoOpcion());
        respuesta.setRespuestaTexto(dtoRespuesta.getRespuestaTexto());
        respuesta.setRespuestaNumero(dtoRespuesta.getRespuestaNumero());

        // Asociar la respuesta convertida al club
        club.agregarRespuesta(respuesta);
    });

    // Guardar el club con las respuestas asociadas
    service.save(club);

    return ResponseEntity.ok("Respuestas del grupo asociadas correctamente");
}


@GetMapping("/clubes/{id}/respuestas")
public ResponseEntity<ClubRespuestaDTO> obtenerRespuestasAsociadas(@PathVariable Long id) {
    clubes club = service.ListarPorId(id.intValue());
    if (club == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Crear el DTO para enviar al frontend
    ClubRespuestaDTO dto = new ClubRespuestaDTO();
    dto.setIdClubes(club.getIdClubes().longValue());
    
    // Obtener respuestas y mapear a DTO
    List<RespuestaDTO> respuestasDTO = club.getRespuestas().stream()
        .map(respuesta -> {
            RespuestaDTO respuestaDTO = new RespuestaDTO();
            respuestaDTO.setIdRespuesta(respuesta.getIdRespuesta());
            respuestaDTO.setTextoPregunta(respuesta.getTextoPregunta());
            respuestaDTO.setRespuestaTexto(respuesta.getRespuestaTexto());
            return respuestaDTO;
        })
        .collect(Collectors.toList());
    
    dto.setRespuestas(respuestasDTO);  // Asignamos las respuestas al DTO

    return ResponseEntity.ok(dto);
  }
}

