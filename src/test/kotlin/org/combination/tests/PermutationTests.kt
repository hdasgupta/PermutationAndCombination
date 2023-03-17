package org.combination.tests

import org.combination.permutaion.Permutation
import org.combination.permutaion.Result
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.math.min
import kotlin.test.assertContentEquals

@SpringBootTest
class PermutationTests {

    @Autowired
    lateinit var permutation: Permutation

    val words = listOf("Red", "Green", "Blue")

    @Test
    fun permutation() {
        val lists = permutation.permutation(words)

        print(lists)


        assertContentEquals(lists.toList().map { it.toList() }, listOf(
            listOf("Blue","Green","Red"),listOf("Blue","Red","Green"),listOf("Green","Blue","Red"),listOf("Green","Red","Blue"),listOf("Red","Blue","Green"),
            listOf("Red","Green","Blue"),
        ))
    }

    fun print(lists: TreeSet<Result>, lineCount:Int=5) =
        lists.toList().let {
                list->
            (list.indices step lineCount).forEach {
                (it until min(it+lineCount, list.size)).forEach {
                        i->
                    val l = list[i]
                    print("listOf(")
                    l.forEach {
                            s->
                        print("\"$s\",")
                    }
                    print("\b),")
                }
                println()
            }
        }


}
