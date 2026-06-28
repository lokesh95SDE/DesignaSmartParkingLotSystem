# AsynchronousBackend - Synchronous vs Asynchronous Programming Masterclass

## Overview

This project demonstrates multiple execution models in Spring Boot:

1. **Traditional Synchronous (Blocking) Processing**
2. **Reactive Asynchronous (Non-Blocking) Processing**
3. **Sequential Async Composition**
4. **Parallel Async Execution**
5. **Streaming Responses using Flux**
6. **Fastest Response Wins Pattern**

The repository intentionally compares:

| Technology     | Programming Model | Blocking | Thread Usage           |
| -------------- | ----------------- | -------- | ---------------------- |
| `RestTemplate` | Imperative        | Yes      | One thread per request |
| `WebClient`    | Reactive          | No       | Event-loop based       |
| `Mono`         | Reactive Async    | No       | Event-loop based       |
| `Flux`         | Reactive Streams  | No       | Event-loop based       |

---

# Project Architecture

```text
Client
   |
   v
+-------------------------+
| HelloWorldController    |
+-------------------------+
            |
            v
+-------------------------+
| HelloWorldService       |
+-------------------------+
     |              |
     |              |
     v              v
RestTemplate     WebClient
(Blocking)      (Non-Blocking)
```

---

# Dependencies Responsible for Async Behaviour

```gradle
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

## Why both?

### Spring MVC (`starter-web`)

Provides:

* DispatcherServlet
* Tomcat
* Blocking request processing

### Spring WebFlux (`starter-webflux`)

Provides:

* Reactive programming
* `Mono`
* `Flux`
* `WebClient`
* Reactor framework

---

# Configuration Analysis

## BeanConfig.java

```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

## Behaviour

Creates a traditional HTTP client.

### Synchronous Flow

```text
Thread-1
   |
   +---- HTTP Call ---- waits
                           |
                     Response arrives
                           |
                      Continue execution
```

The thread is blocked until the response arrives.

---

```java
@Bean
public WebClient webClient() {
    return WebClient.builder().build();
}
```

## Behaviour

Creates a reactive HTTP client.

### Asynchronous Flow

```text
Thread-1
   |
   +---- Register HTTP request
   |
   +---- Return immediately

Netty Event Loop
   |
Response arrives
   |
Callback executed
```

The calling thread is not blocked.

---

# Controller Analysis

---

# Endpoint: `/hello`

```java
@GetMapping("/hello")
public String hello() {
    System.out.println("Thread Handling : "
                       + Thread.currentThread().getName());

    return "Hello, World!";
}
```

## Execution Model

✅ Fully synchronous

## Flow

```text
Client
  |
Tomcat Thread
  |
Controller
  |
Return Response
```

Thread remains occupied for the complete lifecycle.

---

# Endpoint: `/hello2`

```java
return helloWorldService.invocationOfHelloworld();
```

---

# Service Analysis

```java
for(int j=0; j<100000000;j++){
    double temp = Math.sqrt(j)*Math.pow(j,2);
}
```

## Behaviour

Simulates CPU-intensive work.

## Important Observation

This is:

```text
CPU Bound
```

Even inside reactive applications, CPU-heavy loops block threads.

---

```java
return restTemplate.getForObject(...)
```

## Execution

```text
Tomcat Thread
     |
     |------ HTTP Call
     |
     |------ WAITING
     |
Response Received
```

## Result

Entire request thread remains blocked.

---

# Endpoint: `/dummyProductSync`

```java
public ProductResult dummyProductSync()
```

Uses:

```java
RestTemplate
```

## Execution

```text
Client
  |
Tomcat Thread
  |
CPU Work
  |
HTTP Call
  |
Wait
  |
Response
```

## Characteristics

| Property     | Value |
| ------------ | ----- |
| Blocking     | Yes   |
| Scalability  | Low   |
| Thread Usage | High  |

---

# Endpoint: `/dummyProductAsync`

```java
public Mono<ProductResult> dummyProductAsync()
```

## Core Line

```java
webClient.get()
         .uri(...)
         .retrieve()
         .bodyToMono(ProductResult.class)
```

---

# What is Mono?

`Mono<T>` represents:

```text
0 or 1 asynchronous result
```

Equivalent to:

```java
Future<T>
CompletableFuture<T>
Promise<T>
```

in other ecosystems.

---

# Execution Flow

```text
Tomcat Thread
      |
      | registers request
      |
      +-------- returns immediately

Netty Event Loop
      |
HTTP Response Arrives
      |
deserialize body
      |
emit ProductResult
```

---

# Reactive Pipeline

```java
.doOnSuccess(...)
```

Equivalent to:

```java
callback(response)
```

Executed when data becomes available.

---

```java
.doOnError(...)
```

Equivalent to:

```java
catch(exception)
```

for async pipelines.

---

# Sync vs Async Comparison

## Synchronous

```java
ProductResult result =
        restTemplate.getForObject(...);

System.out.println(result);
```

Execution pauses.

---

## Reactive

```java
Mono<ProductResult> mono =
        webClient.get()
                 .retrieve()
                 .bodyToMono(...);
```

Execution continues immediately.

---

# Endpoint: `/dummyProductParallelAll`

```java
Mono.zip(resultMono1,
         resultMono2,
         resultMono3)
```

---

# Concept: Parallel Aggregation

Starts all three requests concurrently.

```text
Request1  -----------------|
Request2  -----------------|---- wait for ALL
Request3  -----------------|
```

Equivalent synchronous code:

```java
r1 = call();
r2 = call();
r3 = call();
```

Total Time:

```text
T = t1 + t2 + t3
```

Reactive Parallel:

```text
T = max(t1,t2,t3)
```

---

# Line-by-Line

```java
Mono<ProductResult> resultMono1 = ...
```

Creates lazy pipelines.

⚠️ No HTTP call occurs yet.

Reactive streams are:

```text
Lazy
```

Execution begins only after subscription.

---

```java
Mono.zip(...)
```

Subscribes to all Monos simultaneously.

Waits for every source to complete.

---

```java
.map(tuple -> List.of(...))
```

Transforms aggregated result.

---

# Endpoint: `/dummyProductParallelFast`

```java
Mono.firstWithValue(
       resultMono1,
       resultMono2,
       resultMono3)
```

## Pattern

```text
Race Strategy
```

Whichever request finishes first wins.

Remaining requests are cancelled.

---

## Flow

```text
API1 ----------- 400 ms
API2 ----- 100 ms  <- Winner
API3 -------- 250 ms

Result = API2
```

Useful for:

* Multi-region services
* Redundant replicas
* Failover systems

---

# Endpoint: `/dummyProductSequentialSync`

```java
result1 = restTemplate...
result2 = restTemplate...
result3 = webClient...block();
```

---

# Critical Observation

```java
.block()
```

Converts:

```text
Non-blocking -> Blocking
```

This defeats the purpose of WebClient.

Equivalent:

```java
Future.get()
```

Avoid in reactive applications.

---

# Execution Timeline

```text
Call1 ---> Wait
Call2 ---> Wait
Call3 ---> Wait

Total = Sum of all durations
```

---

# Endpoint: `/dummyProductSequentialAsync`

```java
flatMap(...)
```

---

# Concept: Async Chaining

Execution:

```text
API1 completes
      |
API2 starts
      |
API3 starts
```

Unlike `zip`, requests are not parallel.

---

# Flow

```text
Mono1
   |
flatMap
   |
Mono2
   |
flatMap
   |
Mono3
```

---

# Why flatMap?

`flatMap` unwraps asynchronous values.

Without it:

```java
Mono<Mono<ProductResult>>
```

With it:

```java
Mono<ProductResult>
```

---

# Endpoint: `/dummyProductStreamFlux`

```java
Flux.interval(Duration.ofSeconds(4))
```

---

# What is Flux?

Represents:

```text
0 to N asynchronous elements
```

Examples:

* Kafka messages
* Stock prices
* Sensor readings
* Notifications

---

# Execution

```text
Every 4 seconds
        |
Call external API
        |
Send response
```

---

```java
.take(5)
```

Limits stream to:

```text
5 emissions
```

---

## Response Type

```java
TEXT_EVENT_STREAM
```

Uses:

```text
Server Sent Events (SSE)
```

Connection remains open.

---

# Thread Behaviour

Expected thread names:

```text
http-nio-8080-exec-1
reactor-http-nio-2
parallel-1
```

---

# Lazy Execution in Reactor

Reactive code does nothing until subscribed.

```java
Mono<ProductResult> mono =
        webClient.get()...
```

Still:

```text
NO HTTP CALL
```

Only after:

```java
return mono;
```

Spring subscribes internally.

---

# Further Concepts (Missing Topics Audit)

---

# 1. Event Loop

WebFlux internally uses:

```text
Netty Event Loop
```

Few threads handle thousands of requests.

```text
EventLoop-1
EventLoop-2
EventLoop-3
```

Unlike Tomcat:

```text
1 request = 1 thread
```

---

# 2. CompletableFuture

Missing from repository.

Example:

```java
CompletableFuture.supplyAsync(() -> service.call());
```

Hybrid model between blocking and reactive.

---

# 3. Thread Pools

Missing.

Examples:

```java
Executors.newFixedThreadPool(10)
Schedulers.boundedElastic()
```

Required when blocking work exists.

---

# 4. Scheduler Switching

Missing.

Reactive CPU work should use:

```java
Mono.fromCallable(this::heavyWork)
    .subscribeOn(Schedulers.boundedElastic())
```

---

# 5. Callbacks vs Promises vs Reactive Streams

## Callback

```java
api.call(result -> {});
```

Problems:

* Callback Hell

---

## Promise/Future

```java
future.thenApply(...)
```

Better composition.

---

## Reactive Streams

```java
Mono
Flux
```

Provides:

* Backpressure
* Composition
* Error propagation

---

# 6. Backpressure

Missing.

Protects systems from overload.

Example:

```java
Flux.range(1,1000000)
```

Consumer can request:

```text
only 100 records
```

at a time.

---

# 7. Mutex / Locks

Missing.

Necessary when multiple async operations update shared state.

Example:

```java
synchronized
ReentrantLock
AtomicInteger
```

---

# 8. Context Switching

Blocking applications:

```text
Many threads
```

cause:

* CPU overhead
* Memory overhead

Reactive systems reduce context switching.

---

# Production Realities

# Error Handling

## Synchronous

```java
try{
   service.call();
}catch(Exception ex){
}
```

Stack traces are straightforward.

---

## Reactive

Exceptions travel through the stream.

```java
webClient.get()
    .retrieve()
    .bodyToMono(...)
    .onErrorResume(ex -> fallback())
```

Recommended:

```java
.onErrorReturn(defaultValue)

.onErrorResume(ex -> alternative())

.retry(3)

.timeout(Duration.ofSeconds(3))
```

---

## Problem

Stack traces may look fragmented.

Example:

```text
reactor.core.publisher.FluxFlatMap
MonoPeekTerminal
MonoMapFuseable
```

Enable:

```properties
spring.reactor.debug-agent.enabled=true
```

or

```java
Hooks.onOperatorDebug();
```

for detailed traces.

---

# Silent Failures

Possible if:

```java
.doOnError(...)
```

only logs.

Current code:

```java
.doOnError(error -> {
   log(error);
});
```

⚠️ Error still propagates.

If no subscriber handles it:

```text
Dropped Exception
```

may occur.

Recommended:

```java
.onErrorResume(ex ->
      Mono.error(new CustomException()))
```

---

# Performance & Scaling

## Excellent For

✅ I/O Bound Operations

* REST APIs
* Databases
* Kafka
* Redis

---

## Poor For

Current code contains:

```java
for(int j=0; j<100000000;j++)
```

Reactive threads should never execute CPU-heavy loops.

This blocks Netty event loops.

---

Recommended:

```java
Mono.fromCallable(this::cpuWork)
    .subscribeOn(Schedulers.boundedElastic());
```

---

# Potential Bottlenecks

Current bottlenecks:

1. CPU loop blocks event loop.
2. External API latency.
3. `.block()` usage.
4. No timeout configuration.
5. No connection pool tuning.

---

# Concurrency Pitfalls

## Race Conditions

No shared mutable state currently.

Safe.

However adding:

```java
private int counter;
```

would become unsafe.

---

## Deadlocks

Possible when mixing:

```java
.block()
```

inside reactive threads.

Example:

```java
webClient.get()
         .retrieve()
         .bodyToMono(...)
         .block();
```

inside Netty thread.

---

## Resource Starvation

Current CPU loops can starve:

```text
reactor-http-nio threads
```

Result:

```text
Slow responses
Timeouts
```

---

# Testing Stability

# Avoid

```java
Thread.sleep(5000);
```

Never rely on sleeps.

---

# Use StepVerifier

```java
StepVerifier.create(service.dummyProductAsync())
            .expectNextCount(1)
            .verifyComplete();
```

---

# Test Flux

```java
StepVerifier.create(service.dummyProductStreamFlux())
            .expectNextCount(5)
            .verifyComplete();
```

---

# Virtual Time Testing

For:

```java
Flux.interval()
```

Use:

```java
StepVerifier.withVirtualTime(
      () -> service.dummyProductStreamFlux())
      .thenAwait(Duration.ofSeconds(20))
      .expectNextCount(5)
      .verifyComplete();
```

No real waiting required.

---

# Mock External APIs

Use:

* WireMock
* MockWebServer

Example:

```java
stubFor(get("/products")
   .willReturn(okJson(response)));
```

Avoid hitting:

```text
https://dummyjson.com
```

during tests.

---

# Recommended Improvements

```java
✓ Replace RestTemplate with WebClient
✓ Remove .block()
✓ Move CPU work to boundedElastic scheduler
✓ Add timeout()
✓ Add retry()
✓ Add fallback()
✓ Add structured logging
✓ Add Circuit Breaker (Resilience4j)
✓ Add StepVerifier tests
✓ Add WebClient connection pooling
✓ Add centralized error handling
```

---

# Summary

This repository successfully demonstrates:

* Blocking vs Non-Blocking execution
* Mono vs Flux
* Sequential vs Parallel execution
* Zip aggregation
* First-response-wins strategy
* Reactive streaming
* Event-driven programming

It is an excellent foundation for understanding how modern high-scale Java systems handle concurrency and asynchronous workloads.
