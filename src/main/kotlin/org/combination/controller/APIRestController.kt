package org.combination.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextUInt

@RestController
class APIRestController {


    val lines = APIRestController::class.java.classLoader
        .getResourceAsStream("words_alpha.txt")
        .bufferedReader()
        .lines()
        .toList()
        .filter { it.length>=4 }

    @RequestMapping(value = ["/words/{query}/{count}"])
    fun getWords(@PathVariable query: String, @PathVariable count: Int): ResponseEntity<List<String>> {
        val out = lines
            .filter {
                    line ->
                    line.startsWith(query.trim(), true)
            }

        val indices = mutableListOf<Int>()
        val random = Random(out.size)


        (0 until count).forEach { _ ->
            inner@do {
                val rand = random.nextInt(out.indices)
                if(!indices.contains(rand) || out.size<count) {
                    indices.add(rand)
                    break@inner
                }
            } while (true)


        }

        val newList = indices.map {
                out[it]
            }

        return ResponseEntity.ok(
            newList
        )
    }

}