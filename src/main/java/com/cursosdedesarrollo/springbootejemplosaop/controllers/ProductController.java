package com.cursosdedesarrollo.springbootejemplosaop.controllers;


import com.cursosdedesarrollo.springbootejemplosaop.entities.Product;
import com.cursosdedesarrollo.springbootejemplosaop.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ---- READ: listado (opción 1: lista completa)
    @GetMapping
    public List<Product> all() {
        return service.findAll();
    }

    // ---- READ: listado paginado (opción 2: usa Pageable)
    @GetMapping(params = {"page", "size"})
    public Page<Product> page(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        // Implementa en tu servicio si quieres paginar de verdad (JpaRepository#findAll(Pageable))
        // Aquí devolvemos una Page si añades un método service.findAll(pageable)
        throw new UnsupportedOperationException("Implementa service.findAll(Pageable) si quieres paginación");
    }

    // ---- READ: por id
    @GetMapping("/{id}")
    public Product byId(@PathVariable Long id) {
        return service.findById(id); // lanza IllegalArgumentException si no existe (manejada por tu @ControllerAdvice)
    }

    // ---- CREATE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        Product created = service.create(p);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    // ---- UPDATE (reemplazo completo)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product update(@PathVariable Long id, @Valid @RequestBody Product p) {
        return service.update(id, p);
    }

    // ---- PATCH (parcial, opcional)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product patch(@PathVariable Long id, @RequestBody Product partial) {
        Product current = service.findById(id);
        if (partial.getName() != null && !partial.getName().isBlank()) {
            current.setName(partial.getName());
        }
        if (partial.getPrice() > 0) {
            current.setPrice(partial.getPrice());
        }
        return service.update(id, current);
    }

    // ---- DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
