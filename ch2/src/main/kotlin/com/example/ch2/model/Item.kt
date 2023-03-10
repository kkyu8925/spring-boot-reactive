package com.example.ch2.model

import org.springframework.data.annotation.Id

data class Item(
    @Id
    var id: String? = null,
    var name: String,
    var price: Double
)
