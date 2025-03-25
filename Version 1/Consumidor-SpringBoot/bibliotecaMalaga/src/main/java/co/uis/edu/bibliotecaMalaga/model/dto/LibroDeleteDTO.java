package co.uis.edu.bibliotecaMalaga.model.dto;


import jakarta.validation.constraints.NotBlank;

public class LibroDeleteDTO {

    @NotBlank
    private String titulo;

    // Constructores
    public LibroDeleteDTO(){}
    public LibroDeleteDTO(String titulo){this.titulo = titulo;}

    // Getters
    public String getTitulo(){return this.titulo;}

    // Setters
    public void setTitulo(String titulo){this.titulo = titulo;}
}
