package com.example.ch2

import com.example.ch2.model.Item
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Component

@Component
class TemplateDatabaseLoader {

    @Bean
    fun initialize(mongo: MongoOperations): CommandLineRunner {
        return CommandLineRunner {
            mongo.save(Item(null, "Alf alarm clock", 19.99))
            mongo.save(Item(null, "Smurf TV tray", 24.99))
        }
    }
}
