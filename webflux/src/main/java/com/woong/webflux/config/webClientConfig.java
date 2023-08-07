package com.woong.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class webClientConfig {
    
    @Bean
    public static HttpClient defaultHttpClient(){
        
        HttpClient client = HttpClient.create().doOnConnected(con -> con.addHandlerFirst(new ReadTimeoutHandler(3000))
                                                                        .addHandlerLast(new WriteTimeoutHandler(3000)))
                                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000);

        return client;
    }
    
    private static WebClient webClient(HttpClient httpClient){
        return WebClient
        .builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
    }

    @Bean
    public static WebClient defaultWebClient(HttpClient httpClient){
        log.info("webClient Setting");
        return webClient(defaultHttpClient());
    }
}
