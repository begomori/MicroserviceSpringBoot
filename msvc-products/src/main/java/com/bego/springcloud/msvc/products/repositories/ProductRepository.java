package com.bego.springcloud.msvc.products.repositories;

// los repositories son responsables del acceso a los datos
// interactúan con la base de datos u otras fuentes de datos
// usan frameworks como Spring Data JPA para simplificar las operaciones de acceso a datos
import org.springframework.data.repository.CrudRepository;
import com.bego.springcloud.msvc.products.entities.Product;

// Se usa la interfaz CrudRepository para operaciones CRUD básicas (Create, Read, Update, Delete)
// Product es la entidad y Long es el tipo de dato del ID
public interface ProductRepository extends CrudRepository<Product, Long> {

}
