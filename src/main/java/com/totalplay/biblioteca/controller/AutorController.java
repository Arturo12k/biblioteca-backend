package com.totalplay.biblioteca.controller;

import com.totalplay.biblioteca.model.Autor;
import com.totalplay.biblioteca.repository.AutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @GetMapping
    public Page<Autor> obtenerAutores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return autorRepository.findAll(PageRequest.of(page, size));
    }

    @PostMapping
    public Autor crearAutor(@Valid @RequestBody Autor autor) {
        return autorRepository.save(autor);
    }

    @PatchMapping("/{id}")
    public Autor actualizarAutor(@PathVariable String id, @RequestBody Autor datos) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if (datos.getNombre() != null) {
            autor.setNombre(datos.getNombre());
        }

        if (datos.getFechaNacimiento() != null) {
            autor.setFechaNacimiento(datos.getFechaNacimiento());
        }

        return autorRepository.save(autor);
    }

    @DeleteMapping("/{id}")
    public void eliminarAutor(@PathVariable String id) {
        autorRepository.deleteById(id);
    }
}