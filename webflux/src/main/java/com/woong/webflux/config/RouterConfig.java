package com.woong.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.woong.webflux.handler.DefaultHandler;
import com.woong.webflux.handler.MirrorHandler;

@Configuration
public class RouterConfig {
    
    @Bean
    public RouterFunction<ServerResponse> hanldler(DefaultHandler handler){
        return RouterFunctions.route().POST("/test", handler::handle).build(); // test 라우터 호출
    }
    
    @Bean
    public RouterFunction<ServerResponse> hanldler1(MirrorHandler handler){
        return RouterFunctions.route(RequestPredicates.POST("/mirror"), handler::handle); // mirror -> response를 출력
    }
}
