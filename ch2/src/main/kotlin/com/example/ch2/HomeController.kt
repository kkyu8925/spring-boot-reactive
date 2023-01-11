package com.example.ch2

import com.example.ch2.repository.CartRepository
import com.example.ch2.repository.ItemRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono

@Controller
class HomeController(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {

    @GetMapping("/")
    fun home(): Mono<Rendering> {
        return Mono.just(
            Rendering.view("home.html").modelAttribute(
                "items", itemRepository.findAll()
            ).modelAttribute(
                "cart", cartRepository.findById("My Cart").defaultIfEmpty(Cart("My Cart"))
            ).build()
        )
    }

    @PostMapping("/add/{id}")
    fun addToCart(@PathVariable id: String): Mono<String> {
        return cartRepository.findById("My Cart")
            .defaultIfEmpty(Cart("My Cart"))
            .flatMap { cart ->
                cart.cartItems.stream()
                    .filter { it.item.id.equals(id) }
                    .findAny()
                    .map {
                        it.increment()
                        Mono.just(cart)
                    }
                    .orElseGet {
                        itemRepository.findById(id)
                            .map { CartItem(it) }
                            .map {
                                cart.cartItems.add(it)
                                cart
                            }
                    }
            }.flatMap { cartRepository.save(it) }.thenReturn("redirect:/")
    }

    @PostMapping
    fun createItem(@ModelAttribute newItem: Item): Mono<String> {
        return itemRepository.save(newItem).thenReturn("redirect:/")
    }

    @DeleteMapping("/delete/{id}")
    fun deleteItem(@PathVariable id: String): Mono<String> {
        return itemRepository.deleteById(id).thenReturn("redirect:/")
    }
}
