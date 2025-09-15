package com.cursosdedesarrollo.springbootejemplosaop.services;


import com.cursosdedesarrollo.springbootejemplosaop.entities.Product;
import com.cursosdedesarrollo.springbootejemplosaop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ---- READ: todos
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repo.findAll();
    }

    // ---- READ: por id
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }

    // ---- CREATE
    @Transactional
    public Product create(Product p) {
        if (p.getPrice() < 1) {
            throw new IllegalArgumentException("El precio mínimo es 1.0");
        }
        return repo.save(p);
    }

    // ---- UPDATE
    @Transactional
    public Product update(Long id, Product p) {
        Product db = findById(id); // lanza excepción si no existe
        db.setName(p.getName());
        db.setPrice(p.getPrice());
        return repo.save(db);
    }

    // ---- DELETE
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("No se puede borrar, producto no encontrado: " + id);
        }
        repo.deleteById(id);
    }
}
