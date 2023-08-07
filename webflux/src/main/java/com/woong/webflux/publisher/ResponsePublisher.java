package com.woong.webflux.publisher;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class ResponsePublisher {
    public Mono<ServerResponse> printOut(String body){
        return ServerResponse
        .ok()
        .headers(heade -> heade.setContentType(MediaType.APPLICATION_JSON))
        .bodyValue(body==null?"" : body); // bodyValue는 민감하기 때문에 null 값이 들어오면 오류를 낸다.
    }
}
