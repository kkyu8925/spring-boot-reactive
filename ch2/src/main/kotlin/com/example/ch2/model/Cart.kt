package com.example.ch2.model

import org.springframework.data.annotation.Id

class Cart(
    @Id
    val id: String? = null,
    val cartItems: MutableList<CartItem> = mutableListOf()
)
