package com.example.reactive

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ServerController(
    private val kitchen: KitchenService
) {
    @GetMapping("/server", MediaType.TEXT_EVENT_STREAM_VALUE)
    fun serveDishes(): Flux<Dish> {
        return kitchen.getDishes()
    }

    @GetMapping("/served-dishes", MediaType.TEXT_EVENT_STREAM_VALUE)
    fun deliverDishes(): Flux<Dish> {
        return kitchen.getDishes().map { it.deliver() }
    }
}
