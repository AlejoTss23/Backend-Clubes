package com.clubes.controller.encuestas;

import java.util.Map;

public class EstadisticasDTO {
 
    private String pregunta;
    private Map<String, Integer> opcionesConteo;
  
  
    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    public Map<String, Integer> getOpcionesConteo() {
        return opcionesConteo;
    }
    public void setOpcionesConteo(Map<String, Integer> opcionesConteo) {
        this.opcionesConteo = opcionesConteo;
    }
    
}
