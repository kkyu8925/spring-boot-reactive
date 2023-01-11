package com.example.ch2.controller

import com.example.ch2.model.Item
import com.example.ch2.repository.ItemRepository
import com.example.ch2.service.CartService
import com.example.ch2.service.InventoryService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono

@Controller
class HomeController(
    private val itemRepository: ItemRepository,
    private val cartService: CartService,
    private val inventoryService: InventoryService
) {

    @GetMapping("/")
    fun home(): Mono<Rendering> {
        return Mono.just(
            Rendering.view("home.html").modelAttribute(
                "items", itemRepository.findAll()
            ).modelAttribute(
                "cart", cartService.getCart("My Cart")
            ).build()
        )
    }

    @PostMapping("/add/{id}")
    fun addToCart(@PathVariable id: String): Mono<String> {
        return cartService.addToCart("My Cart", id).thenReturn("redirect:/")
    }

    @PostMapping
    fun createItem(@ModelAttribute newItem: Item): Mono<String> {
        return itemRepository.save(newItem).thenReturn("redirect:/")
    }

    @DeleteMapping("/delete/{id}")
    fun deleteItem(@PathVariable id: String): Mono<String> {
        return itemRepository.deleteById(id).thenReturn("redirect:/")
    }

    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) name: String,
        @RequestParam(required = false) description: String,
        @RequestParam useAnd: Boolean
    ): Mono<Rendering> {
        return Mono.just(
            Rendering.view("home.html").modelAttribute(
                    "items", inventoryService.searchByExample(name, description, useAnd)
                ).modelAttribute(
                    "cart", cartService.getCart("My Cart")
                ).build()
        )
    }
}
