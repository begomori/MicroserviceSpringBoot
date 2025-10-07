package com.bego.springcloud.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bego.springcloud.msvc.items.models.Product;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {
    // llama al metodo findAll() del ProductController del otromicroservicio
    @GetMapping
    List<Product> findAll();

    // llama al metodo details() del ProductController del otro microservicio
    @GetMapping("/{idProduct}")
    Product details(@PathVariable Long idProduct);

}
