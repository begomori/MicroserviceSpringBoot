package com.bego.springcloud.msvc.items.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${config.baseurl.endpoint.msvc-products}")
    private String url;

    @Bean // define un bean de Spring
    @LoadBalanced // habilita el balanceo de carga entre instancias del microservicio
    WebClient.Builder webClientBuilder() { // crea un bean de tipo WebClient.Builder para inyectarlo
        return WebClient.builder().baseUrl(url); // establece la URL base del microservicio
    }
}
