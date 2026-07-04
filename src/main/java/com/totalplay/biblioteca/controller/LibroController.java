package com.totalplay.biblioteca.controller;

import com.totalplay.biblioteca.model.Autor;
import com.totalplay.biblioteca.model.Libro;
import com.totalplay.biblioteca.repository.AutorRepository;
import com.totalplay.biblioteca.repository.LibroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroController(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @GetMapping
    public Page<Libro> obtenerLibros(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String buscar
    ) {
        if (buscar != null && !buscar.trim().isEmpty()) {
            return libroRepository.buscarPorTituloOAutor(buscar, PageRequest.of(page, size));
        }

        return libroRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Libro obtenerLibroPorId(@PathVariable String id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @PostMapping
    public Libro crearLibro(@Valid @RequestBody LibroRequest request) {
        Autor autor = autorRepository.findById(request.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Libro libro = new Libro();
        libro.setTitulo(request.getTitulo());
        libro.setIsbn(request.getIsbn());
        libro.setNumeroPaginas(request.getNumeroPaginas());
        libro.setAutor(autor);
        libro.setUrlPortada(request.getUrlPortada());

        return libroRepository.save(libro);
    }

    @PatchMapping("/{id}")
    public Libro actualizarLibro(@PathVariable String id, @RequestBody LibroRequest request) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (request.getTitulo() != null) {
            libro.setTitulo(request.getTitulo());
        }

        if (request.getIsbn() != null) {
            libro.setIsbn(request.getIsbn());
        }

        if (request.getNumeroPaginas() != null) {
            libro.setNumeroPaginas(request.getNumeroPaginas());
        }

        if (request.getUrlPortada() != null) {
            libro.setUrlPortada(request.getUrlPortada());
        }

        if (request.getAutorId() != null) {
            Autor autor = autorRepository.findById(request.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            libro.setAutor(autor);
        }

        return libroRepository.save(libro);
    }

    @DeleteMapping("/{id}")
    public void eliminarLibro(@PathVariable String id) {
        libroRepository.deleteById(id);
    }

    public static class LibroRequest {
        private String titulo;
        private String isbn;
        private String autorId;
        private Integer numeroPaginas;
        private String urlPortada;

        public String getTitulo() {
            return titulo;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getAutorId() {
            return autorId;
        }

        public Integer getNumeroPaginas() {
            return numeroPaginas;
        }

        public String getUrlPortada() {
            return urlPortada;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public void setAutorId(String autorId) {
            this.autorId = autorId;
        }

        public void setNumeroPaginas(Integer numeroPaginas) {
            this.numeroPaginas = numeroPaginas;
        }

        public void setUrlPortada(String urlPortada) {
            this.urlPortada = urlPortada;
        }
    }
}