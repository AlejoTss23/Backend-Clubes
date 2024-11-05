package com.clubes.controller.encuestas;

import java.util.List;

public class PreguntaDTO {

    private Long idPregunta;
    private String textoPregunta;
    private String tipoPregunta;
    private List<OpcionDTO> opciones;

    // Getters y Setters

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public List<OpcionDTO> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionDTO> opciones) {
        this.opciones = opciones;
    }
}

