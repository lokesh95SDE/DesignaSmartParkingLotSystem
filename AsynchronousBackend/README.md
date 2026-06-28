# AsynchronousBackend - Synchronous vs Asynchronous Programming Masterclass

## Overview

This project demonstrates different execution models in Spring Boot using both traditional blocking and reactive non-blocking approaches.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring WebFlux
- Reactor (Mono & Flux)
- RestTemplate
- WebClient

---

## Synchronous vs Asynchronous

| Feature | Synchronous | Asynchronous |
|----------|------------|--------------|
| Thread Model | One request per thread | Event-loop based |
| Blocking | Yes | No |
| Scalability | Lower | Higher |
| Resource Usage | High | Low |
| Suitable For | CPU-intensive operations | I/O-intensive operations |

---

## Architecture

```text
Client
   |
   v
Controller
   |
   v
Service
   |
   +------ RestTemplate (Blocking)
   |
   +------ WebClient (Non-Blocking)
```

---

## Endpoints

### `/hello`

Simple synchronous endpoint.

```java
@GetMapping("/hello")
public String hello() {
    return "Hello World";
}
```

---

### `/dummyProductSync`

Uses `RestTemplate`.

```java
restTemplate.getForObject(...)
```

Characteristics:

- Blocking
- Sequential
- Thread waits until response arrives

---

### `/dummyProductAsync`

Uses `WebClient`.

```java
webClient.get()
         .retrieve()
         .bodyToMono(ProductResult.class)
```

Characteristics:

- Non-blocking
- Reactive
- Returns immediately

---

## Mono

Represents:

```text
0 or 1 asynchronous value
```

Example:

```java
Mono<Product> productMono;
```

---

## Flux

Represents:

```text
0 to N asynchronous values
```

Example:

```java
Flux<Product> productFlux;
```

---

## Parallel Execution

```java
Mono.zip(mono1, mono2, mono3)
```

All requests execute concurrently.

Total execution time:

```text
max(t1,t2,t3)
```

---

## First Response Wins

```java
Mono.firstWithValue(m1,m2,m3)
```

Returns fastest successful response.

Useful for:

- Multi-region deployments
- Redundancy
- Failover

---

## Sequential Async

```java
mono1.flatMap(r1 ->
       mono2.flatMap(r2 ->
       mono3));
```

Each request starts only after previous completion.

---

## Streaming with Flux

```java
Flux.interval(Duration.ofSeconds(4))
```

Useful for:

- SSE
- Kafka streams
- Notifications
- Real-time dashboards

---

# Production Realities

## Error Handling

Reactive:

```java
.onErrorResume(ex -> fallback())
.retry(3)
.timeout(Duration.ofSeconds(5))
```

Recommended:

```java
Hooks.onOperatorDebug();
```

---

## Performance Considerations

Reactive works best for:

- REST calls
- Database calls
- Messaging

Avoid CPU-heavy loops on event-loop threads.

Move CPU work:

```java
.subscribeOn(Schedulers.boundedElastic())
```

---

## Common Pitfalls

### Avoid

```java
.block()
Thread.sleep()
```

### Risks

- Deadlocks
- Resource starvation
- Event-loop blocking

---

## Testing Reactive Code

Use:

```java
StepVerifier.create(service.call())
            .expectNextCount(1)
            .verifyComplete();
```

For time-based streams:

```java
StepVerifier.withVirtualTime(...)
```

Avoid:

```java
Thread.sleep(5000);
```

---

# Missing Concepts Worth Learning

- Event Loop
- CompletableFuture
- Thread Pools
- Scheduler Switching
- Backpressure
- Mutexes and Locks
- Context Switching
- Callbacks vs Promises

---

# Suggested Improvements

- Replace RestTemplate with WebClient
- Remove `.block()`
- Add timeout and retry
- Add Circuit Breaker (Resilience4j)
- Add structured logging
- Add StepVerifier tests
- Configure WebClient connection pool

---

# Learning Outcomes

After studying this repository you will understand:

- Blocking vs Non-Blocking programming
- Mono vs Flux
- Sequential vs Parallel execution
- Reactive Streams
- Event-driven architecture
- Production considerations in asynchronous systems
