package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/solvedac")
public class SolvedacController {

//    @GetMapping()
//    public Flux<String> filterProblem() {
//
//        WebClient client = WebClient.create("https://solved.ac/api/v3");
//
//        return client.get()
//                .uri("/search/problem?query=#dfs")
//                .acceptCharset(Charset.forName("UTF-8"))
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(String.class);
//
//    }

    @GetMapping()
    public ResponseEntity<Flux<String>> filterProblem() {

        WebClient client = WebClient.create("https://solved.ac/api/v3");

        return new ResponseEntity<> (client.get()
                .uri("/search/problem?query=#dfs")
                .acceptCharset(Charset.forName("UTF-8"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(String.class), HttpStatus.OK);

    }
}