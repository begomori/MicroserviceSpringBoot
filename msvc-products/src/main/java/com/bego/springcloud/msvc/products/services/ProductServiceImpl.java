package com.bego.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bego.springcloud.msvc.products.entities.Product;
import com.bego.springcloud.msvc.products.repositories.ProductRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository; // inyectamos el repository de productos
    private final Environment environment;

    // usamos constructor para inyección de dependencias
    public ProductServiceImpl(ProductRepository repository, Environment environment) {
        this.environment = environment;
        this.repository = repository;
    }

    // Método para encontrar todos los productos y asignarles el puerto del servidor
    // actual
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>) repository.findAll()).stream().map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        }).toList();
    }

    // Método para encontrar un producto por ID y asignarle el puerto del servidor
    // actual
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }

    @Override
    @Transactional
    public Product save(Product product) {
        // Lógica para guardar un producto
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Lógica para eliminar un producto por ID
        repository.deleteById(id);
    }

}
