package co.uis.edu.bibliotecaMalaga.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_publicacion", nullable = false)
    private Date fechaPublicacion;

    // Constructores

    public Libro(){}

    public Libro(String titulo, String autor, String descripcion, Date fechaPublicacion){
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
    public Long getId(){return this.id;}
    public String getTitulo(){return this.titulo;}
    public String getAutor(){return this.autor;}
    public String getDescripcion(){return  this.descripcion;}
    public Date getFechaPublicacion(){return this.fechaPublicacion;}

}
