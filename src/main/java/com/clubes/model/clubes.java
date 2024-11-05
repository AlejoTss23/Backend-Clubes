package com.clubes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.clubes.model.Encuestas.Respuesta;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="clubes")
public class clubes {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idClubes;
	@Column(name="nombre", length = 255, nullable = false)
   private String nombre;
	@Column(name="localidad", length = 100, nullable = true) 
    private String localidad;
    /*  
	@Column(name="telefono", nullable = true)
	private Integer telefono;  */
    
     @Column(name="telefono", length = 100)
	private String telefono;

	@Column(name="departamento", length = 100, nullable = true)
	private String departamento;

	/*@Column(name="celular")
	private Integer celular;  */
      
     @Column(name="celular", length = 100)
	private String celular;

	@Column(name = "pagina_web", length = 255, nullable = true)
    private String paginaWeb;

    @Column(name = "codigo_postal", length = 20, nullable = true)
    private String codigoPostal;

    @Column(name = "fecha_fundacion", nullable = true)
    private Date fechaFundacion;
/* 
    @Column(name = "personeria_juridica", length = 255, nullable = true)
    private Integer personeriaJuridica;  */

     @Column(name = "personeria_juridica", length = 255, nullable = true)
    private String personeriaJuridica;
 
/* 
    @Column(name = "numero_legajo", length = 50, nullable = true)
    private Integer numeroLegajo; */

    
     @Column(name = "numero_legajo", length = 50, nullable = true)
    private String numeroLegajo;
     

    @Column(name = "tipo_legal", length = 255, nullable = true)
    private String tipoLegal;

    @Column(name = "fecha_personeria_juridica", nullable = true)
    private Date fechaPersoneriaJuridica;

    @Column(name = "nodo", nullable = true)
    private Integer nodo;

    @Column(name = "opcion_personeria_juridica", length = 50, nullable = true)
    private String opcionPersoneriaJuridica;

	
     @Column(name = "mail", length = 250, nullable = true)
    private String mail;
     

    
     @Column(name = "instagram", length = 50, nullable = true )
    private String instagram;
      

      
        @Column(name = "facebook", length = 50, nullable = true)
    private String facebook;
       

     
     @Column(name = "otraredsocial", length = 50, nullable = true)
    private String otraredsocial ;
      
      
      @Column(name = "calle", length = 50, nullable = true)
    private String calle;
       
      
    /*@Column(name = "numero_de_calle", length = 50, nullable = true)
    private Integer NumeroDeCalle;  */

    
     @Column(name = "numero_de_calle", length = 50, nullable = true)
    private String NumeroDeCalle;
    
       
    // Generar getter and setters de los nuevos modelos
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Respuesta> respuestas = new ArrayList<>();

    public void agregarRespuesta(Respuesta respuesta) {
      respuestas.add(respuesta);
      respuesta.setClub(this);  // Asigna la referencia del club en la respuesta
    }

    public void eliminarRespuesta(Respuesta respuesta) {
    respuestas.remove(respuesta);
    respuesta.setClub(null);  // Elimina la referencia al club en la respuesta
    }
    
	public String getMail() {
    return mail;
    }

   public void setMail(String mail) {
    this.mail = mail;
    }

    public String getInstagram() {
    return instagram;
    }

    public void setInstagram(String instagram) {
    this.instagram = instagram;
    }

    public String getFacebook() {
    return facebook;
    }

    public void setFacebook(String facebook) {
    this.facebook = facebook;
    }

    public String getOtraredsocial() {
    return otraredsocial;
    }

    public void setOtraredsocial(String otraredsocial) {
    this.otraredsocial = otraredsocial;
    }

   public String getCalle() {
    return calle;
    }

   public void setCalle(String calle) {
    this.calle = calle;
    }

    public String getNumeroDeCalle() {
    return NumeroDeCalle;
    }

    public void setNumeroDeCalle(String numeroDeCalle) {
    NumeroDeCalle = numeroDeCalle;
    }

    public Integer getIdClubes() {
		return idClubes;
	}

	public void setIdClubes(Integer idClubes) {
		this.idClubes = idClubes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getPersoneriaJuridica() {
        return personeriaJuridica;
    }

    public void setPersoneriaJuridica(String personeriaJuridica) {
        this.personeriaJuridica = personeriaJuridica;
    }

    public String getNumeroLegajo() {
        return numeroLegajo;
    }

    public void setNumeroLegajo(String numeroLegajo) {
        this.numeroLegajo = numeroLegajo;
    }

    public String getTipoLegal() {
        return tipoLegal;
    }

    public void setTipoLegal(String tipoLegal) {
        this.tipoLegal = tipoLegal;
    }

    public Date getFechaPersoneriaJuridica() {
        return fechaPersoneriaJuridica;
    }

    public void setFechaPersoneriaJuridica(Date fechaPersoneriaJuridica) {
        this.fechaPersoneriaJuridica = fechaPersoneriaJuridica;
    }

    public Integer getNodo() {
        return nodo;
    }

    public void setNodo(Integer nodo) {
        this.nodo = nodo;
    }

    public String getOpcionPersoneriaJuridica() {
        return opcionPersoneriaJuridica;
    }

    public void setOpcionPersoneriaJuridica(String opcionPersoneriaJuridica) {
        this.opcionPersoneriaJuridica = opcionPersoneriaJuridica;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

}