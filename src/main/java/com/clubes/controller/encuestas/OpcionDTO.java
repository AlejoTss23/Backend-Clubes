package com.clubes.controller.encuestas;

public class OpcionDTO {

    private Long idOpcion;
    private String textoOpcion;

    // Constructor

    public OpcionDTO(Long idOpcion, String textoOpcion) {
        this.idOpcion = idOpcion;
        this.textoOpcion = textoOpcion;
    }

    // Getters y Setters

    public Long getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Long idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }
}
