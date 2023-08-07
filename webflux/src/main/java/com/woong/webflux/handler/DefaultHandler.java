package com.woong.webflux.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.woong.webflux.publisher.ResponsePublisher;
import com.woong.webflux.publisher.WebClientPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
// request를 그대로 내보내는 test
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultHandler {

    private final WebClientPublisher webClientPublisher;
    private final ResponsePublisher jsonPublisher;

    public Mono<ServerResponse> handle(ServerRequest request){

        log.info("DefaultHandler 호출");
        
        return Mono
                .just(request)
                .flatMap(webClientPublisher::send)
                .flatMap(string -> {
                    if(string != null){
                        System.out.println(string);
                        return ServerResponse
                            .ok()
                            .contentType(request.headers().asHttpHeaders().getContentType())
                            .bodyValue(string);
                    }else{
                        return ServerResponse
                            .status(500)
                            .bodyValue("Internal ServerResponse Error");
                    }
                });
    }
}
