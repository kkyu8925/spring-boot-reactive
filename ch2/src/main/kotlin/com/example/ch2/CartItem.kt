package com.example.ch2

class CartItem(
    val item: Item,
    var quantity: Int = 1
) {
    fun increment() {
        quantity++
    }
}
