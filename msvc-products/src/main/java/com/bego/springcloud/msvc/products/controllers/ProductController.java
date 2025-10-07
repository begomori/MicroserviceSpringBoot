package com.bego.springcloud.msvc.products.controllers;

// los controllers manejan las solicitudes HTTP y devuelven respuestas HTTP
// reciben las solicitudes del cliente, llaman a los services para procesar la lógica de negocio
// y devuelven los resultados al cliente
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bego.springcloud.msvc.products.entities.Product;
import com.bego.springcloud.msvc.products.services.ProductService;

@RestController
// @RequestMapping("/api/products")
public class ProductController {
    // Aquí irían los endpoints para manejar las solicitudes HTTP relacionadas con
    // los productos como GET, POST, PUT, DELETE, etc.
    private final ProductService service; // inyectamos el servicio de productos

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping // convierte la lista de productos a JSON automáticamente
    public ResponseEntity<?> list() { // devuelve la lista de productos
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{idProduct}") // mapea la URL con el ID del producto
    public ResponseEntity<Product> details(@PathVariable Long idProduct) { // obtiene el ID del producto de la URL
        Optional<Product> productOptional = service.findById(idProduct);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow()); // devuelve 200 OK con el producto si se encuentra
        }
        return ResponseEntity.notFound().build(); // devuelve 404 si no se encuentra el producto
    }
}
