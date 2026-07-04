package com.totalplay.biblioteca.repository;

import com.totalplay.biblioteca.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE " +
            "LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(l.autor.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    Page<Libro> buscarPorTituloOAutor(@Param("busqueda") String busqueda, Pageable pageable);
}