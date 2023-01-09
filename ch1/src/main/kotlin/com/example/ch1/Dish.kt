package com.example.ch1

data class Dish(
    var description: String
) {
    var isDelivered = false

    fun deliver(): Dish {
        val deliveredDish = Dish(description)
        deliveredDish.isDelivered = true
        return deliveredDish
    }
}
