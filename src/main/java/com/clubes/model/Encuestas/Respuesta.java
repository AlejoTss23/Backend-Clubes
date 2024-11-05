package com.clubes.model.Encuestas;

import java.util.List;

import com.clubes.model.clubes;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRespuesta;
    private Long idRespuestaGrupo;

    
    @ManyToOne
    @JoinColumn(name = "idPregunta")
    private Pregunta pregunta;
    private String textoPregunta;
    
    @ManyToOne
    @JoinColumn(name = "idOpcion", nullable = true)
    private Opcion opcion;
    private String textoOpcion;
    private String respuestaTexto;
    private Integer respuestaNumero;

    // Relación correcta con Encuesta
    @ManyToOne
    @JoinColumn(name = "idEncuesta")
    private Encuesta encuesta;
    
    // Opciones seleccionadas en caso de preguntas de selección múltiple
    @ElementCollection
    private List<Long> opcionIds;

    @ManyToOne
    @JoinColumn(name = "id_club")
    @JsonBackReference
    private clubes club;  // Aquí está la referencia a la entidad clubes

    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
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

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
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

    public clubes getClub() {
        return club;
    }

    public void setClub(clubes club) {
        this.club = club;
    }
}

