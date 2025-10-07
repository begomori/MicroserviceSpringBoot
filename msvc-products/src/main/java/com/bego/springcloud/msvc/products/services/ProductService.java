package com.bego.springcloud.msvc.products.services;

// los services contienen la lógica de negocio y llaman a los repositories para acceder a los datos
// es una capa intermedia entre los controllers y los repositories
// permite separar la lógica de negocio del acceso a datos y del manejo de HTTP
// facilita el testing y el mantenimiento del código
import java.util.List;
import java.util.Optional;

import com.bego.springcloud.msvc.products.entities.Product;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id); // usamos Optional para manejar el caso de que no se encuentre el producto

    Product save(Product product);

    void deleteById(Long id);
}
