package com.clubes.controller.encuestas;

import java.util.List;

public class RespuestaDTO {

    private Long encuestaId;
    private Long preguntaId;
    private List<Long> opcionIds;  // Para preguntas de opción múltiple
    private String textoOpcion;    // Para mostrar el texto de la opción seleccionada
    private String respuestaTexto;
    private Integer respuestaNumero;
    private Long idRespuestaGrupo; 
    private String textoPregunta; 
    private Long idRespuesta; // Agregar el campo faltante para idRespuesta

    // Getters y setters

    public RespuestaDTO() {}

    // Constructor con parámetros para inicializar los valores
    public RespuestaDTO(Long encuestaId, Long preguntaId, String textoPregunta, String textoOpcion, String respuestaTexto, Integer respuestaNumero, Long idRespuestaGrupo) {
        this.encuestaId = encuestaId;
        this.preguntaId = preguntaId;
        this.textoPregunta = textoPregunta;
        this.textoOpcion = textoOpcion;
        this.respuestaTexto = respuestaTexto;
        this.respuestaNumero = respuestaNumero;
        this.idRespuestaGrupo = idRespuestaGrupo;
    }

    // Getter y setter para idRespuesta
    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public Integer getRespuestaNumero() {
        return respuestaNumero;
    }

    public void setRespuestaNumero(Integer respuestaNumero) {
        this.respuestaNumero = respuestaNumero;
    }

    public Long getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(Long encuestaId) {
        this.encuestaId = encuestaId;
    }

    public List<Long> getOpcionIds() {
        return opcionIds;
    }

    public void setOpcionIds(List<Long> opcionIds) {
        this.opcionIds = opcionIds;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public Long getIdRespuestaGrupo() {
        return idRespuestaGrupo;
    }

    public void setIdRespuestaGrupo(Long idRespuestaGrupo) {
        this.idRespuestaGrupo = idRespuestaGrupo;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }
}

