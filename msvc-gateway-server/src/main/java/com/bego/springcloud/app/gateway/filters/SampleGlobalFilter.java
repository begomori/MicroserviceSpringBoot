package com.bego.springcloud.app.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

    private static final String TOKEN_HEADER = "token";
    private static final String TOKEN_VALUE = "abcdefg";
    private static final String COOKIE_NAME = "color";
    private static final String COOKIE_VALUE = "red";

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    // Implementación del filtro global para el gateway
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Ejecutando filtro antes del request PRE");

        // Añadiendo un header al request antes de enviarlo al servicio
        var modifiedRequest = exchange.getRequest().mutate()
                .headers(h -> h.add(TOKEN_HEADER, TOKEN_VALUE))
                .build();

        var modifiedExchange = exchange.mutate().request(modifiedRequest).build();

        // Continuar con la cadena de filtros y añadir lógica POST después del response
        return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> {
            logger.info("Ejecutando filtro POST después del response");

            // Verificar que el header existe antes de acceder a él
            if (modifiedExchange.getRequest().getHeaders().containsKey(TOKEN_HEADER)) {
                String token = modifiedExchange.getRequest().getHeaders().getFirst(TOKEN_HEADER);
                logger.info("token: {}", token);
            } else {
                logger.warn("Token header no encontrado");
            }

            // Añadiendo un cookie al response y pasando el content type a texto plano
            try {
                modifiedExchange.getResponse().getCookies().add(COOKIE_NAME,
                        ResponseCookie.from(COOKIE_NAME, COOKIE_VALUE).build());
                modifiedExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
                modifiedExchange.getResponse().getHeaders().add(TOKEN_HEADER, TOKEN_VALUE);
            } catch (Exception e) {
                logger.warn("No se pudo modificar el response: {}", e.getMessage());
            }
        }));
    }

    @Override
    public int getOrder() {
        return 100; // Orden del filtro
    }

}
