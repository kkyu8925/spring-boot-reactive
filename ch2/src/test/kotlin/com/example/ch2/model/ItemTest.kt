package com.example.ch2.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ItemTest {

    @Test
    fun itemBasicsShouldWork() {
        // given

        // when
        val sampleItem = Item("item1", "TV tray", 19.99)

        // then
        assertThat(sampleItem.id).isEqualTo("item1")
        assertThat(sampleItem.name).isEqualTo("TV tray")
        assertThat(sampleItem.price).isEqualTo(19.99)
        assertThat(sampleItem.toString()).isEqualTo(
            "Item(id=item1, name=TV tray, price=19.99)"
        )

        val sampleItem2 = Item("item1", "TV tray", 19.99)
        assertThat(sampleItem).isEqualTo(sampleItem2)
    }
}
