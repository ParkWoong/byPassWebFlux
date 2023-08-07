package com.woong.webflux.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class MirrorHandler {
    public Mono<ServerResponse> handle(ServerRequest request){
        return request
        .bodyToMono(String.class)
        .flatMap(string -> {
                return ServerResponse
                .ok()
                .contentType(request.headers().asHttpHeaders().getContentType())
                .bodyValue(string==null?"":string);
        })
        .onErrorResume(error -> {
            error.printStackTrace();
            return ServerResponse.status(500).bodyValue("Internal Server Error");        
        });
    }
}
