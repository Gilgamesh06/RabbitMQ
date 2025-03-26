package co.uis.edu.bibliotecaMalaga.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class LibroUpdateDTO{

    @NotBlank
    @Size(max = 200)
    private String titulo;


    @Size(max = 150)
    private String autor;

    private String descripcion;

    @PastOrPresent(message = "La fecha de publicación debe ser hoy o en el pasado")
    private Date fecha_publicacion;

    // Constructores

    public LibroUpdateDTO(){}

    public LibroUpdateDTO(String titulo, String autor, String descripcion, Date fechaPublicacion){
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
        this.fecha_publicacion = fechaPublicacion;
    }


    // Setters

    public void setTitulo(String titulo){ this.titulo = titulo;}
    public void setAutor(String autor){ this.autor = autor;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setFechaPublicacion(Date fechaPublicacion){this.fecha_publicacion = fechaPublicacion;}

    // Getters

    public String getTitulo(){return this.titulo;}
    public String getAutor(){return  this.autor;}
    public String getDescripcion(){return  this.descripcion;}
    public Date getFechaPublicacion(){return this.fecha_publicacion;}

}
