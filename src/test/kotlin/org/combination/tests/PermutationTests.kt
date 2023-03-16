package org.combination.tests

import org.combination.permutaion.Permutation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertContentEquals

@SpringBootTest
class PermutationTests {

    @Autowired
    lateinit var permutation: Permutation

    val words = listOf("Red", "Green", "Blue")

    @Test
    fun permutation() {
        val lists = permutation.permutation(words)

        assertContentEquals(lists, listOf(
            listOf("Red","Green","Blue"),
            listOf("Red","Blue","Green"),
            listOf("Blue","Red","Green"),
            listOf("Green","Red","Blue"),
            listOf("Green","Blue","Red"),
            listOf("Blue","Green","Red"),
        ))
    }
}
