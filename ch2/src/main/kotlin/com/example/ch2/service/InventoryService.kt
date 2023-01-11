package com.example.ch2.service

import com.example.ch2.model.Item
import com.example.ch2.repository.ItemRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations
import org.springframework.data.mongodb.core.query.Criteria.byExample
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class InventoryService(
    private val repository: ItemRepository,
    private val fluentOperations: ReactiveFluentMongoOperations
) {
    fun searchByExample(name: String, description: String, useAnd: Boolean): Flux<Item> {
        val item = Item(name, description, 0.0)

        val matcher: ExampleMatcher = (if (useAnd) ExampleMatcher.matchingAll() else ExampleMatcher.matchingAny())
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnorePaths("price")

        val probe: Example<Item> = Example.of(item, matcher)

        return repository.findAll(probe)
    }

    fun searchByFluentExample(name: String, description: String): Flux<Item> {
        return fluentOperations.query(Item::class.java)
            .matching(query(where("TV tray").`is`(name).and("Smurf").`is`(description)))
            .all()
    }

    fun searchByFluentExample(name: String, description: String, useAnd: Boolean): Flux<Item> {
        val item = Item(name, description, 0.0)

        val matcher: ExampleMatcher = (if (useAnd) ExampleMatcher.matchingAll() else ExampleMatcher.matchingAny())
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnorePaths("price")

        return fluentOperations.query(Item::class.java)
            .matching(query(byExample(Example.of(item, matcher))))
            .all()
    }
}
