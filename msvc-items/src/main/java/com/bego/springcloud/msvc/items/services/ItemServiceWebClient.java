package com.bego.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.bego.springcloud.msvc.items.models.Item;
import com.bego.springcloud.msvc.items.models.Product;

@Primary // indica que es la implementacion de webClient es la principal y no la de Feign
@Service
public class ItemServiceWebClient implements ItemService {
    private final WebClient.Builder webClientBuilder;

    public ItemServiceWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public List<Item> findAll() {
        return this.webClientBuilder.build().get() // crea una instancia de WebClient y realiza una solicitud GET
                .accept(MediaType.APPLICATION_JSON) // la URL del microservicio al que se llama
                .retrieve() // obtiene la respuesta
                .bodyToFlux(Product.class) // convierte el cuerpo de la respuesta en un flujo de objetos Product
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList() // recopila los elementos del flujo en una lista
                .block(); // bloquea el hilo hasta que se complete la operación y devuelve la lista
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            return Optional.of(this.webClientBuilder.build().get() // crea una instancia de WebClient y realiza una
                                                                   // solicitud GET
                    .uri("/{idProduct}", id) // la URL del microservicio al que se llama, con el id
                                             // del producto
                    .accept(MediaType.APPLICATION_JSON) // indica que se espera una respuesta en formato JSON
                    .retrieve() // obtiene la respuesta
                    .bodyToMono(Product.class) // convierte el cuerpo de la respuesta en un objeto Item
                    .map(product -> new Item(product, new Random().nextInt(10) + 1))
                    .block()); // bloquea el hilo hasta que se complete la operación y devuelve el objeto Item
        } catch (WebClientResponseException e) {
            return Optional.empty(); // si hay un error en la respuesta, devuelve un Optional vacío
        }
    }

}
