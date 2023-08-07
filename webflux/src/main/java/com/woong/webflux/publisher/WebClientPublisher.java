package com.woong.webflux.publisher;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientPublisher {
    
    private final WebClient webClient;
    private final static byte[] NULL_BYTES = {};
    
    
    public Mono<String> send(ServerRequest request){
        return webClient
                .post()
                .uri("http://localhost:8081/mirror")
                .headers(httpHeader -> {
                    httpHeader.addAll(request.headers().asHttpHeaders());
                })
                .contentType(request.headers().asHttpHeaders().getContentType()) // request를 그대로 header로
                .body(request.bodyToMono(byte[].class).defaultIfEmpty(NULL_BYTES), byte[].class)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                })
                .onErrorResume(e->{e.printStackTrace(); return Mono.empty();});
    }    
}
