package com.totalplay.biblioteca.model;
import com.totalplay.biblioteca.util.TextoUtil;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.UUID;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    private String id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 150)
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 20)
    private String isbn;

    @NotNull(message = "El autor es obligatorio")
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @NotNull(message = "El número de páginas es obligatorio")
    @Min(value = 1, message = "Debe tener al menos 1 página")
    private Integer numeroPaginas;

    private String urlPortada;

    @PrePersist
    public void generarId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public Autor getAutor() {
        return autor;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = TextoUtil.normalizar(titulo);
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }
}