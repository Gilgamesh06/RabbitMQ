package co.uis.edu.bibliotecaMalaga.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

public class LibroAddDTO implements Serializable {

    private static final long serailizableVersionUID = 1L;

    @NotBlank
    @Size(max = 200)
    private String titulo;

    @NotBlank
    @Size(max = 150)
    private String autor;

    private String descripcion;

    @PastOrPresent(message = "La fecha de publicación debe ser hoy o en el pasado")
    private Date fechaPublicacion;

    // Constructores

    public LibroAddDTO(){}

    public LibroAddDTO(String titulo, String autor, String descripcion, Date fechaPublicacion){
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
    }


    // Setters

    public void setTitulo(String titulo){ this.titulo = titulo;}
    public void setAutor(String autor){ this.autor = autor;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setFechaPublicacion(Date fechaPublicacion){this.fechaPublicacion = fechaPublicacion;}

    // Getters

    public String getTitulo(){return this.titulo;}
    public String getAutor(){return  this.autor;}
    public String getDescripcion(){return  this.descripcion;}
    public Date getFechaPublicacion(){return this.fechaPublicacion;}


}
