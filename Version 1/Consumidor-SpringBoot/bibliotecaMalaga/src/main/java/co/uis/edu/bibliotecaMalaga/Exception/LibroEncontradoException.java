package co.uis.edu.bibliotecaMalaga.Exception;

public class LibroEncontradoException  extends RuntimeException{

    // Constructor que acepta un mensaje
    public LibroEncontradoException(String message) {
        super(message);
    }

    // Constructor que acepta un mensaje y una causa
    public LibroEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}

