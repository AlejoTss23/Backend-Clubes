package com.clubes.repository.Encuestas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clubes.model.Encuestas.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    // Este método busca todas las respuestas por el ID de la encuesta
    List<Respuesta> findByEncuesta_Id(Long idEncuesta);

    @Query("SELECT r, o.textoOpcion FROM Respuesta r JOIN Opcion o ON r.opcion.idOpcion = o.idOpcion WHERE r.encuesta.id = :idEncuesta")
    List<Object[]> obtenerRespuestasConOpcionTexto(@Param("idEncuesta") Long idEncuesta);

    @Query("SELECT r FROM Respuesta r WHERE r.idRespuestaGrupo = :idRespuestaGrupo")
    List<Respuesta> findByIdRespuestaGrupo(@Param("idRespuestaGrupo") Long idRespuestaGrupo);

    @Query("SELECT DISTINCT r.idRespuestaGrupo FROM Respuesta r")
    List<Long> findDistinctIdRespuestaGrupo();


    @Query("SELECT r FROM Respuesta r WHERE r.idRespuestaGrupo IN :ids")
    List<Respuesta> findRespuestasByGrupoIds(@Param("ids") List<Long> ids);
    
    
    
}


