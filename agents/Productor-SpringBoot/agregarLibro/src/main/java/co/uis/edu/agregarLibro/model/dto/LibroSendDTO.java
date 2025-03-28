package co.uis.edu.agregarLibro.model.dto;

public class LibroSendDTO {

    private String titulo;
    private String sede;

    public LibroSendDTO(){}
    public LibroSendDTO(String titulo, String sede){
        this.titulo = titulo;
        this.sede = sede;
    }

    public void setSede(String sede) {this.sede = sede;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getSede() {return sede;}
    public String getTitulo() {return titulo;}
}
