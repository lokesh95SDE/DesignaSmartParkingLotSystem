package org.example.service;

import org.example.dto.Product;
import org.example.dto.ProductResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class HelloWorldService {
@Autowired
private RestTemplate restTemplate;
@Autowired
private WebClient webClient;

    public String invocationOfHelloworld() {
        System.out.println("Thread Handling : "+Thread.currentThread().getName());

        for(int j=0; j<100000000;j++){
            double temp = Math.sqrt(j)*Math.pow(j,2);
        }

            return restTemplate.getForObject("http://localhost:1002/hello", String.class);
    }

    public ProductResult dummyProductSync() {
        System.out.println("Thread Handling : "+Thread.currentThread().getName());

        for(int j=0; j<100000000;j++){
            double temp = Math.sqrt(j)*Math.pow(j,2);

        }

        return restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
    }

    public Mono<ProductResult> dummyProductAsync() {
        for(int j=0; j<100000000;j++){
            double temp = Math.sqrt(j)*Math.pow(j,2);

        }
        return  webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class)
                .doOnSuccess(productResult -> {
                    System.out.println("Thread Handling : "+Thread.currentThread().getName());
                    System.out.println("Received product result: " + productResult.getProducts().size() + " products");
                }).doOnError(error -> {
                    System.err.println("Error occurred while fetching product result: " + error.getMessage());
                });
    }

    public Mono<List<ProductResult>> dummyProductParallelAll() {
        Mono<ProductResult> resultMono1 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
        Mono<ProductResult> resultMono2 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
        Mono<ProductResult> resultMono3 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);

        Mono<List<ProductResult>> combinedResults = Mono.zip(resultMono1, resultMono2, resultMono3)
                .map(tuple -> List.of(tuple.getT1(), tuple.getT2(), tuple.getT3()));
        return combinedResults.doOnSuccess(productResults -> {
            System.out.println("Thread Handling : "+Thread.currentThread().getName());
        }).doOnError(error -> {
            System.err.println("Error occurred while fetching product results: " + error.getMessage());
        });

    }

    public Mono<ProductResult> dummyProductParallelFast() {
        Mono<ProductResult> resultMono1 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
        Mono<ProductResult> resultMono2 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);
        Mono<ProductResult> resultMono3 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class);

        Mono<ProductResult> fastResult = Mono.firstWithValue(resultMono1, resultMono2, resultMono3);
        return fastResult.doOnSuccess(productResult -> {
            System.out.println("Thread Handling : "+Thread.currentThread().getName());
        }).doOnError(error -> {
            System.err.println("Error occurred while fetching product result: " + error.getMessage());
        });
    }

    public List<ProductResult> dummyProductSequentialSync() {
        ProductResult result1 = restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
        ProductResult result2 = restTemplate.getForObject("https://dummyjson.com/products", ProductResult.class);
        ProductResult result3 = webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).block();

        return List.of(result1,result2,result3);

    }

    public Mono<List<ProductResult>> dummyProductSequentialAsync() {
        return webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(result -> {
            System.out.println("Received first product result: " + result.getProducts().size() + " products");
        }).doOnError(error -> {
            System.err.println("Error occurred while fetching first product result: " + error.getMessage());
            }).flatMap(apiResult->{return webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(result -> {
            System.out.println("Received first product result: " + result.getProducts().size() + " products");
            }).doOnError(error -> {
            System.err.println("Error occurred while fetching first product result: " + error.getMessage());
                }).flatMap(apiResult2->{return webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).doOnSuccess(result -> {
                System.out.println("Received first product result: " + result.getProducts().size() + " products");
                }).doOnError(error -> {
                System.err.println("Error occurred while fetching first product result: " + error.getMessage());
                    }).map(apiResult3->List.of(apiResult,apiResult2,apiResult3));
            });
        });
    }


    public Flux<ProductResult> dummyProductStreamFlux() {
        return Flux.interval(Duration.ofSeconds(4)).take(5).flatMap(i->{
            return webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductResult.class).
            doOnSuccess(productResult -> {
                System.out.println("Thread Handling : "+Thread.currentThread().getName());
                System.out.println("Received product result: " + productResult.getProducts().size() + " products");
            }).doOnError(error -> {
                System.err.println("Error occurred while fetching product result: " + error.getMessage());
            });
        });

    }
}
