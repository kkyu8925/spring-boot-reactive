package com.example.ch2.service

import com.example.ch2.Cart
import com.example.ch2.CartItem
import com.example.ch2.repository.CartRepository
import com.example.ch2.repository.ItemRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CartService(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    fun getCart(cartId: String): Mono<Cart> {
        return cartRepository.findById(cartId).defaultIfEmpty(Cart(cartId))
    }

    fun addToCart(cartId: String, id: String): Mono<Cart> {
        return getCart(cartId)
            .flatMap { cart ->
                cart.cartItems.stream()
                    .filter { cartItem ->
                        cartItem.item.id.equals(id)
                    }
                    .findAny()
                    .map { cartItem ->
                        cartItem.increment()
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
            }
            .flatMap(cartRepository::save)
    }
}
