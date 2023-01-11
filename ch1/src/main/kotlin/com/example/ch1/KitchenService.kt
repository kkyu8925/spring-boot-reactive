package com.example.ch1

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import java.time.Duration
import java.util.*

@Service
class KitchenService {

    /**
     * 요리 스트림 생성
     */
    fun getDishes(): Flux<Dish> {
        return Flux.generate { sink: SynchronousSink<Dish> ->
            sink.next(randomDish())
        }.delayElements(Duration.ofMillis(250))
    }

    /**
     * 요라 무작위 선택
     */
    private fun randomDish(): Dish {
        return menu[picker.nextInt(menu.size)]
    }

    private val menu: List<Dish> = listOf(
        Dish("Sesame chicken"),
        Dish("Lo mein noodles, plain"),
        Dish("Sweet & sour beef")
    )

    private val picker: Random = Random()
}
