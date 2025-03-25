package co.uis.edu.bibliotecaMalaga.model.dto;

import java.util.Date;

public class LibroResponseDTO {


    private String titulo;

    private String autor;

    private String descripcion;

    // Constructores

    public LibroResponseDTO(){}

    public LibroResponseDTO(String titulo, String autor, String descripcion, Date fechaPublicacion){
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
    }


    // Setters

    public void setTitulo(String titulo){ this.titulo = titulo;}
    public void setAutor(String autor){ this.autor = autor;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    // Getters

    public String getTitulo(){return this.titulo;}
    public String getAutor(){return  this.autor;}
    public String getDescripcion(){return  this.descripcion;}
}
