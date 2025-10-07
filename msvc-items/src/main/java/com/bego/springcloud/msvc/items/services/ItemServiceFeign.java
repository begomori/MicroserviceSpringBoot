package com.bego.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bego.springcloud.msvc.items.clients.ProductFeignClient;
import com.bego.springcloud.msvc.items.models.Item;
import com.bego.springcloud.msvc.items.models.Product;

import feign.FeignException;

@Service
public class ItemServiceFeign implements ItemService {
    // inyección automática por Spring del cliente Feign definido en
    // ProductFeignClient
    @Autowired
    private ProductFeignClient client; // Spring encuentra el bean y lo inyecta

    // obtiene todos los productos del microservicio de productos y los convierte en
    // items
    @Override
    public List<Item> findAll() {
        // cada producto lo convierte en un item con cantidad aleatoria entre 1 y 10
        return client.findAll()
                .stream()
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        // obtiene el producto del microservicio de productos y lo convierte en un item
        try {
            Product product = client.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));

        } catch (FeignException e) {
            return Optional.empty();

        }
    }

}
