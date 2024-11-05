package com.clubes.controller;

import java.util.List;

import com.clubes.controller.encuestas.RespuestaDTO;

public class ClubRespuestaDTO {

    private Long idClubes;
    private Long idRespuestaGrupo;  // Asegúrate de tener este campo
private List<RespuestaDTO> respuestas;


    public Long getIdClubes() {
        return idClubes;
    }

    public void setIdClubes(Long idClubes) {
        this.idClubes = idClubes;
    }

    public Long getIdRespuestaGrupo() {  // Agrega este getter
        return idRespuestaGrupo;
    }

    public void setIdRespuestaGrupo(Long idRespuestaGrupo) {  // Agrega este setter
        this.idRespuestaGrupo = idRespuestaGrupo;
    }

    public List<RespuestaDTO> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaDTO> respuestas) {
        this.respuestas = respuestas;
    }
}








