package org.example.controller;

import org.example.dto.Product;
import org.example.dto.ProductResult;
import org.example.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

@RestController
public class HelloWorldController {

@Autowired
private HelloWorldService helloWorldService;


 @GetMapping("/hello")
    public String hello() {
     System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return "Hello, World!";
    }

 @GetMapping("/hello2")
    public String hello2() {
     System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return helloWorldService.invocationOfHelloworld();
    }

    @GetMapping("/dummyProductSync")
    public ProductResult dummyWorldProductSync(){
      System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return helloWorldService.dummyProductSync();
    }

    @GetMapping("/dummyProductAsync")
    public Mono<ProductResult> dummyWorldProductAsync(){
        System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return helloWorldService.dummyProductAsync();
    }
    @GetMapping("/dummyProductParallelAll")
    public Mono<List<ProductResult>> dummyworldProductParallelAll(){
        System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return helloWorldService.dummyProductParallelAll();
    }

    @GetMapping("/dummyProductParallelFast")
    public Mono<ProductResult> dummyworldProductParallelFast(){
        System.out.println("Thread Handling : "+Thread.currentThread().getName());
        return helloWorldService.dummyProductParallelFast();
    }

    @GetMapping("/dummyProductSequentialSync")
    public List<ProductResult> dummyworldProductSequentialSync() {
        System.out.println("Thread Handling : " + Thread.currentThread().getName());
        return helloWorldService.dummyProductSequentialSync();
    }

    @GetMapping("/dummyProductSequentialAsync")
    public Mono<List<ProductResult>> dummyworldProductSequentialAsync() {
        System.out.println("Thread Handling : " + Thread.currentThread().getName());
        return helloWorldService.dummyProductSequentialAsync();
    }

    @GetMapping(value = "/dummyProductStreamFlux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductResult> dummyworldProductStreamFlux() {
        System.out.println("Thread Handling : " + Thread.currentThread().getName());
        return helloWorldService.dummyProductStreamFlux();
    }



}
