package org.combination.tests

import org.combination.permutaion.Combination
import org.combination.permutaion.Result
import org.combination.rule.combination.Criteria
import org.combination.rule.combination.Rule
import org.combination.rule.combination.Rules
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.TreeSet
import kotlin.math.min
import kotlin.test.assertContentEquals

@SpringBootTest
class CombinationTests {

    @Autowired
    lateinit var combination: Combination

    @Test
    fun simpleUnorderedCombination() {
        val words = listOf("Red", "Green", "Blue")

        val lists = combination.combination(words, Rules(2))

        print(lists)

        assertContentEquals(lists.toList().map { it.toList() }, listOf(
            listOf("Blue","Green"),listOf("Blue","Red"),listOf("Green","Blue"),listOf("Green","Red"),listOf("Red","Blue"),
            listOf("Red","Green"),
        ))
    }

    @Test
    fun simpleOrderedCombination() {
        val words = listOf("Red", "Green", "Blue")

        val lists = combination.combination(words, Rules(2, true))

        print(lists)

        assertContentEquals(lists.toList().map { it.toList() }, listOf(
            listOf("Green","Blue"),listOf("Red","Blue"),listOf("Red","Green"),
        ))
    }

    @Test
    fun rulesUnorderedCombination() {
        val words = listOf("Red1", "Red2", "Red3", "Green1", "Green2", "Blue1", "Blue2", "Blue3")

        val rules = Rules(4)

        rules.add(Rule(Criteria.StartsWith, "Red", maxCount = 2))
        rules.add(Rule(Criteria.StartsWith, "Green", minCount = 1))
        rules.add(Rule(Criteria.StartsWith, "Blue", minCount = 2))

        val lists = combination.combination(words, rules)

        print(lists)

        assertContentEquals(lists.toList().map { it.toList() }, ruleUnorderedOutput)
    }

    @Test
    fun rulesOrderedCombination() {
        val words = listOf("Red1", "Red2", "Red3", "Green1", "Green2", "Blue1", "Blue2", "Blue3")

        val rules = Rules(4, true)

        rules.add(Rule(Criteria.StartsWith, "Red", maxCount = 2))
        rules.add(Rule(Criteria.StartsWith, "Green", minCount = 1))
        rules.add(Rule(Criteria.StartsWith, "Blue", minCount = 2))

        val lists = combination.combination(words, rules)

        print(lists)

        assertContentEquals(lists.toList().map { it.toList() }, listOf(
            listOf("Green1","Blue1","Blue2","Blue3"),listOf("Green1","Green2","Blue1","Blue2"),listOf("Green1","Green2","Blue1","Blue3"),listOf("Green1","Green2","Blue2","Blue3"),listOf("Green2","Blue1","Blue2","Blue3"),
            listOf("Red1","Blue1","Blue2","Blue3"),listOf("Red1","Green1","Blue1","Blue2"),listOf("Red1","Green1","Blue1","Blue3"),listOf("Red1","Green1","Blue2","Blue3"),listOf("Red1","Green1","Green2","Blue1"),
            listOf("Red1","Green1","Green2","Blue2"),listOf("Red1","Green1","Green2","Blue3"),listOf("Red1","Green2","Blue1","Blue2"),listOf("Red1","Green2","Blue1","Blue3"),listOf("Red1","Green2","Blue2","Blue3"),
            listOf("Red1","Red2","Blue1","Blue2"),listOf("Red1","Red2","Blue1","Blue3"),listOf("Red1","Red2","Blue2","Blue3"),listOf("Red1","Red2","Green1","Blue1"),listOf("Red1","Red2","Green1","Blue2"),
            listOf("Red1","Red2","Green1","Blue3"),listOf("Red1","Red2","Green1","Green2"),listOf("Red1","Red2","Green2","Blue1"),listOf("Red1","Red2","Green2","Blue2"),listOf("Red1","Red2","Green2","Blue3"),
            listOf("Red1","Red2","Red3","Green1"),listOf("Red1","Red2","Red3","Green2"),listOf("Red1","Red3","Blue1","Blue2"),listOf("Red1","Red3","Blue1","Blue3"),listOf("Red1","Red3","Blue2","Blue3"),
            listOf("Red1","Red3","Green1","Blue1"),listOf("Red1","Red3","Green1","Blue2"),listOf("Red1","Red3","Green1","Blue3"),listOf("Red1","Red3","Green1","Green2"),listOf("Red1","Red3","Green2","Blue1"),
            listOf("Red1","Red3","Green2","Blue2"),listOf("Red1","Red3","Green2","Blue3"),listOf("Red2","Blue1","Blue2","Blue3"),listOf("Red2","Green1","Blue1","Blue2"),listOf("Red2","Green1","Blue1","Blue3"),
            listOf("Red2","Green1","Blue2","Blue3"),listOf("Red2","Green1","Green2","Blue1"),listOf("Red2","Green1","Green2","Blue2"),listOf("Red2","Green1","Green2","Blue3"),listOf("Red2","Green2","Blue1","Blue2"),
            listOf("Red2","Green2","Blue1","Blue3"),listOf("Red2","Green2","Blue2","Blue3"),listOf("Red2","Red3","Blue1","Blue2"),listOf("Red2","Red3","Blue1","Blue3"),listOf("Red2","Red3","Blue2","Blue3"),
            listOf("Red2","Red3","Green1","Blue1"),listOf("Red2","Red3","Green1","Blue2"),listOf("Red2","Red3","Green1","Blue3"),listOf("Red2","Red3","Green1","Green2"),listOf("Red2","Red3","Green2","Blue1"),
            listOf("Red2","Red3","Green2","Blue2"),listOf("Red2","Red3","Green2","Blue3"),listOf("Red3","Blue1","Blue2","Blue3"),listOf("Red3","Green1","Blue1","Blue2"),listOf("Red3","Green1","Blue1","Blue3"),
            listOf("Red3","Green1","Blue2","Blue3"),listOf("Red3","Green1","Green2","Blue1"),listOf("Red3","Green1","Green2","Blue2"),listOf("Red3","Green1","Green2","Blue3"),listOf("Red3","Green2","Blue1","Blue2"),
            listOf("Red3","Green2","Blue1","Blue3"),listOf("Red3","Green2","Blue2","Blue3"),
        ))
    }

    fun print(lists:TreeSet<Result>, lineCount:Int=5) =
        lists.toList().let {
            list->
            (list.indices step lineCount).forEach {
                (it until min(it+lineCount, list.size)).forEach {
                        i->
                    val l = list[i]
                    print("listOf(")
                    l.forEach {
                            s->print("\"$s\",")
                    }
                    print("\b),")
                }
                println()
            }
        }


    val ruleUnorderedOutput = listOf(
        listOf("Blue1","Blue2","Blue3","Green1"),listOf("Blue1","Blue2","Blue3","Green2"),listOf("Blue1","Blue2","Blue3","Red1"),listOf("Blue1","Blue2","Blue3","Red2"),listOf("Blue1","Blue2","Blue3","Red3"),
        listOf("Blue1","Blue2","Green1","Blue3"),listOf("Blue1","Blue2","Green1","Green2"),listOf("Blue1","Blue2","Green1","Red1"),listOf("Blue1","Blue2","Green1","Red2"),listOf("Blue1","Blue2","Green1","Red3"),
        listOf("Blue1","Blue2","Green2","Blue3"),listOf("Blue1","Blue2","Green2","Green1"),listOf("Blue1","Blue2","Green2","Red1"),listOf("Blue1","Blue2","Green2","Red2"),listOf("Blue1","Blue2","Green2","Red3"),
        listOf("Blue1","Blue2","Red1","Blue3"),listOf("Blue1","Blue2","Red1","Green1"),listOf("Blue1","Blue2","Red1","Green2"),listOf("Blue1","Blue2","Red1","Red2"),listOf("Blue1","Blue2","Red1","Red3"),
        listOf("Blue1","Blue2","Red2","Blue3"),listOf("Blue1","Blue2","Red2","Green1"),listOf("Blue1","Blue2","Red2","Green2"),listOf("Blue1","Blue2","Red2","Red1"),listOf("Blue1","Blue2","Red2","Red3"),
        listOf("Blue1","Blue2","Red3","Blue3"),listOf("Blue1","Blue2","Red3","Green1"),listOf("Blue1","Blue2","Red3","Green2"),listOf("Blue1","Blue2","Red3","Red1"),listOf("Blue1","Blue2","Red3","Red2"),
        listOf("Blue1","Blue3","Blue2","Green1"),listOf("Blue1","Blue3","Blue2","Green2"),listOf("Blue1","Blue3","Blue2","Red1"),listOf("Blue1","Blue3","Blue2","Red2"),listOf("Blue1","Blue3","Blue2","Red3"),
        listOf("Blue1","Blue3","Green1","Blue2"),listOf("Blue1","Blue3","Green1","Green2"),listOf("Blue1","Blue3","Green1","Red1"),listOf("Blue1","Blue3","Green1","Red2"),listOf("Blue1","Blue3","Green1","Red3"),
        listOf("Blue1","Blue3","Green2","Blue2"),listOf("Blue1","Blue3","Green2","Green1"),listOf("Blue1","Blue3","Green2","Red1"),listOf("Blue1","Blue3","Green2","Red2"),listOf("Blue1","Blue3","Green2","Red3"),
        listOf("Blue1","Blue3","Red1","Blue2"),listOf("Blue1","Blue3","Red1","Green1"),listOf("Blue1","Blue3","Red1","Green2"),listOf("Blue1","Blue3","Red1","Red2"),listOf("Blue1","Blue3","Red1","Red3"),
        listOf("Blue1","Blue3","Red2","Blue2"),listOf("Blue1","Blue3","Red2","Green1"),listOf("Blue1","Blue3","Red2","Green2"),listOf("Blue1","Blue3","Red2","Red1"),listOf("Blue1","Blue3","Red2","Red3"),
        listOf("Blue1","Blue3","Red3","Blue2"),listOf("Blue1","Blue3","Red3","Green1"),listOf("Blue1","Blue3","Red3","Green2"),listOf("Blue1","Blue3","Red3","Red1"),listOf("Blue1","Blue3","Red3","Red2"),
        listOf("Blue1","Green1","Blue2","Blue3"),listOf("Blue1","Green1","Blue2","Green2"),listOf("Blue1","Green1","Blue2","Red1"),listOf("Blue1","Green1","Blue2","Red2"),listOf("Blue1","Green1","Blue2","Red3"),
        listOf("Blue1","Green1","Blue3","Blue2"),listOf("Blue1","Green1","Blue3","Green2"),listOf("Blue1","Green1","Blue3","Red1"),listOf("Blue1","Green1","Blue3","Red2"),listOf("Blue1","Green1","Blue3","Red3"),
        listOf("Blue1","Green1","Green2","Blue2"),listOf("Blue1","Green1","Green2","Blue3"),listOf("Blue1","Green1","Green2","Red1"),listOf("Blue1","Green1","Green2","Red2"),listOf("Blue1","Green1","Green2","Red3"),
        listOf("Blue1","Green1","Red1","Blue2"),listOf("Blue1","Green1","Red1","Blue3"),listOf("Blue1","Green1","Red1","Green2"),listOf("Blue1","Green1","Red1","Red2"),listOf("Blue1","Green1","Red1","Red3"),
        listOf("Blue1","Green1","Red2","Blue2"),listOf("Blue1","Green1","Red2","Blue3"),listOf("Blue1","Green1","Red2","Green2"),listOf("Blue1","Green1","Red2","Red1"),listOf("Blue1","Green1","Red2","Red3"),
        listOf("Blue1","Green1","Red3","Blue2"),listOf("Blue1","Green1","Red3","Blue3"),listOf("Blue1","Green1","Red3","Green2"),listOf("Blue1","Green1","Red3","Red1"),listOf("Blue1","Green1","Red3","Red2"),
        listOf("Blue1","Green2","Blue2","Blue3"),listOf("Blue1","Green2","Blue2","Green1"),listOf("Blue1","Green2","Blue2","Red1"),listOf("Blue1","Green2","Blue2","Red2"),listOf("Blue1","Green2","Blue2","Red3"),
        listOf("Blue1","Green2","Blue3","Blue2"),listOf("Blue1","Green2","Blue3","Green1"),listOf("Blue1","Green2","Blue3","Red1"),listOf("Blue1","Green2","Blue3","Red2"),listOf("Blue1","Green2","Blue3","Red3"),
        listOf("Blue1","Green2","Green1","Blue2"),listOf("Blue1","Green2","Green1","Blue3"),listOf("Blue1","Green2","Green1","Red1"),listOf("Blue1","Green2","Green1","Red2"),listOf("Blue1","Green2","Green1","Red3"),
        listOf("Blue1","Green2","Red1","Blue2"),listOf("Blue1","Green2","Red1","Blue3"),listOf("Blue1","Green2","Red1","Green1"),listOf("Blue1","Green2","Red1","Red2"),listOf("Blue1","Green2","Red1","Red3"),
        listOf("Blue1","Green2","Red2","Blue2"),listOf("Blue1","Green2","Red2","Blue3"),listOf("Blue1","Green2","Red2","Green1"),listOf("Blue1","Green2","Red2","Red1"),listOf("Blue1","Green2","Red2","Red3"),
        listOf("Blue1","Green2","Red3","Blue2"),listOf("Blue1","Green2","Red3","Blue3"),listOf("Blue1","Green2","Red3","Green1"),listOf("Blue1","Green2","Red3","Red1"),listOf("Blue1","Green2","Red3","Red2"),
        listOf("Blue1","Red1","Blue2","Blue3"),listOf("Blue1","Red1","Blue2","Green1"),listOf("Blue1","Red1","Blue2","Green2"),listOf("Blue1","Red1","Blue2","Red2"),listOf("Blue1","Red1","Blue2","Red3"),
        listOf("Blue1","Red1","Blue3","Blue2"),listOf("Blue1","Red1","Blue3","Green1"),listOf("Blue1","Red1","Blue3","Green2"),listOf("Blue1","Red1","Blue3","Red2"),listOf("Blue1","Red1","Blue3","Red3"),
        listOf("Blue1","Red1","Green1","Blue2"),listOf("Blue1","Red1","Green1","Blue3"),listOf("Blue1","Red1","Green1","Green2"),listOf("Blue1","Red1","Green1","Red2"),listOf("Blue1","Red1","Green1","Red3"),
        listOf("Blue1","Red1","Green2","Blue2"),listOf("Blue1","Red1","Green2","Blue3"),listOf("Blue1","Red1","Green2","Green1"),listOf("Blue1","Red1","Green2","Red2"),listOf("Blue1","Red1","Green2","Red3"),
        listOf("Blue1","Red1","Red2","Blue2"),listOf("Blue1","Red1","Red2","Blue3"),listOf("Blue1","Red1","Red2","Green1"),listOf("Blue1","Red1","Red2","Green2"),listOf("Blue1","Red1","Red3","Blue2"),
        listOf("Blue1","Red1","Red3","Blue3"),listOf("Blue1","Red1","Red3","Green1"),listOf("Blue1","Red1","Red3","Green2"),listOf("Blue1","Red2","Blue2","Blue3"),listOf("Blue1","Red2","Blue2","Green1"),
        listOf("Blue1","Red2","Blue2","Green2"),listOf("Blue1","Red2","Blue2","Red1"),listOf("Blue1","Red2","Blue2","Red3"),listOf("Blue1","Red2","Blue3","Blue2"),listOf("Blue1","Red2","Blue3","Green1"),
        listOf("Blue1","Red2","Blue3","Green2"),listOf("Blue1","Red2","Blue3","Red1"),listOf("Blue1","Red2","Blue3","Red3"),listOf("Blue1","Red2","Green1","Blue2"),listOf("Blue1","Red2","Green1","Blue3"),
        listOf("Blue1","Red2","Green1","Green2"),listOf("Blue1","Red2","Green1","Red1"),listOf("Blue1","Red2","Green1","Red3"),listOf("Blue1","Red2","Green2","Blue2"),listOf("Blue1","Red2","Green2","Blue3"),
        listOf("Blue1","Red2","Green2","Green1"),listOf("Blue1","Red2","Green2","Red1"),listOf("Blue1","Red2","Green2","Red3"),listOf("Blue1","Red2","Red1","Blue2"),listOf("Blue1","Red2","Red1","Blue3"),
        listOf("Blue1","Red2","Red1","Green1"),listOf("Blue1","Red2","Red1","Green2"),listOf("Blue1","Red2","Red3","Blue2"),listOf("Blue1","Red2","Red3","Blue3"),listOf("Blue1","Red2","Red3","Green1"),
        listOf("Blue1","Red2","Red3","Green2"),listOf("Blue1","Red3","Blue2","Blue3"),listOf("Blue1","Red3","Blue2","Green1"),listOf("Blue1","Red3","Blue2","Green2"),listOf("Blue1","Red3","Blue2","Red1"),
        listOf("Blue1","Red3","Blue2","Red2"),listOf("Blue1","Red3","Blue3","Blue2"),listOf("Blue1","Red3","Blue3","Green1"),listOf("Blue1","Red3","Blue3","Green2"),listOf("Blue1","Red3","Blue3","Red1"),
        listOf("Blue1","Red3","Blue3","Red2"),listOf("Blue1","Red3","Green1","Blue2"),listOf("Blue1","Red3","Green1","Blue3"),listOf("Blue1","Red3","Green1","Green2"),listOf("Blue1","Red3","Green1","Red1"),
        listOf("Blue1","Red3","Green1","Red2"),listOf("Blue1","Red3","Green2","Blue2"),listOf("Blue1","Red3","Green2","Blue3"),listOf("Blue1","Red3","Green2","Green1"),listOf("Blue1","Red3","Green2","Red1"),
        listOf("Blue1","Red3","Green2","Red2"),listOf("Blue1","Red3","Red1","Blue2"),listOf("Blue1","Red3","Red1","Blue3"),listOf("Blue1","Red3","Red1","Green1"),listOf("Blue1","Red3","Red1","Green2"),
        listOf("Blue1","Red3","Red2","Blue2"),listOf("Blue1","Red3","Red2","Blue3"),listOf("Blue1","Red3","Red2","Green1"),listOf("Blue1","Red3","Red2","Green2"),listOf("Blue2","Blue1","Blue3","Green1"),
        listOf("Blue2","Blue1","Blue3","Green2"),listOf("Blue2","Blue1","Blue3","Red1"),listOf("Blue2","Blue1","Blue3","Red2"),listOf("Blue2","Blue1","Blue3","Red3"),listOf("Blue2","Blue1","Green1","Blue3"),
        listOf("Blue2","Blue1","Green1","Green2"),listOf("Blue2","Blue1","Green1","Red1"),listOf("Blue2","Blue1","Green1","Red2"),listOf("Blue2","Blue1","Green1","Red3"),listOf("Blue2","Blue1","Green2","Blue3"),
        listOf("Blue2","Blue1","Green2","Green1"),listOf("Blue2","Blue1","Green2","Red1"),listOf("Blue2","Blue1","Green2","Red2"),listOf("Blue2","Blue1","Green2","Red3"),listOf("Blue2","Blue1","Red1","Blue3"),
        listOf("Blue2","Blue1","Red1","Green1"),listOf("Blue2","Blue1","Red1","Green2"),listOf("Blue2","Blue1","Red1","Red2"),listOf("Blue2","Blue1","Red1","Red3"),listOf("Blue2","Blue1","Red2","Blue3"),
        listOf("Blue2","Blue1","Red2","Green1"),listOf("Blue2","Blue1","Red2","Green2"),listOf("Blue2","Blue1","Red2","Red1"),listOf("Blue2","Blue1","Red2","Red3"),listOf("Blue2","Blue1","Red3","Blue3"),
        listOf("Blue2","Blue1","Red3","Green1"),listOf("Blue2","Blue1","Red3","Green2"),listOf("Blue2","Blue1","Red3","Red1"),listOf("Blue2","Blue1","Red3","Red2"),listOf("Blue2","Blue3","Blue1","Green1"),
        listOf("Blue2","Blue3","Blue1","Green2"),listOf("Blue2","Blue3","Blue1","Red1"),listOf("Blue2","Blue3","Blue1","Red2"),listOf("Blue2","Blue3","Blue1","Red3"),listOf("Blue2","Blue3","Green1","Blue1"),
        listOf("Blue2","Blue3","Green1","Green2"),listOf("Blue2","Blue3","Green1","Red1"),listOf("Blue2","Blue3","Green1","Red2"),listOf("Blue2","Blue3","Green1","Red3"),listOf("Blue2","Blue3","Green2","Blue1"),
        listOf("Blue2","Blue3","Green2","Green1"),listOf("Blue2","Blue3","Green2","Red1"),listOf("Blue2","Blue3","Green2","Red2"),listOf("Blue2","Blue3","Green2","Red3"),listOf("Blue2","Blue3","Red1","Blue1"),
        listOf("Blue2","Blue3","Red1","Green1"),listOf("Blue2","Blue3","Red1","Green2"),listOf("Blue2","Blue3","Red1","Red2"),listOf("Blue2","Blue3","Red1","Red3"),listOf("Blue2","Blue3","Red2","Blue1"),
        listOf("Blue2","Blue3","Red2","Green1"),listOf("Blue2","Blue3","Red2","Green2"),listOf("Blue2","Blue3","Red2","Red1"),listOf("Blue2","Blue3","Red2","Red3"),listOf("Blue2","Blue3","Red3","Blue1"),
        listOf("Blue2","Blue3","Red3","Green1"),listOf("Blue2","Blue3","Red3","Green2"),listOf("Blue2","Blue3","Red3","Red1"),listOf("Blue2","Blue3","Red3","Red2"),listOf("Blue2","Green1","Blue1","Blue3"),
        listOf("Blue2","Green1","Blue1","Green2"),listOf("Blue2","Green1","Blue1","Red1"),listOf("Blue2","Green1","Blue1","Red2"),listOf("Blue2","Green1","Blue1","Red3"),listOf("Blue2","Green1","Blue3","Blue1"),
        listOf("Blue2","Green1","Blue3","Green2"),listOf("Blue2","Green1","Blue3","Red1"),listOf("Blue2","Green1","Blue3","Red2"),listOf("Blue2","Green1","Blue3","Red3"),listOf("Blue2","Green1","Green2","Blue1"),
        listOf("Blue2","Green1","Green2","Blue3"),listOf("Blue2","Green1","Green2","Red1"),listOf("Blue2","Green1","Green2","Red2"),listOf("Blue2","Green1","Green2","Red3"),listOf("Blue2","Green1","Red1","Blue1"),
        listOf("Blue2","Green1","Red1","Blue3"),listOf("Blue2","Green1","Red1","Green2"),listOf("Blue2","Green1","Red1","Red2"),listOf("Blue2","Green1","Red1","Red3"),listOf("Blue2","Green1","Red2","Blue1"),
        listOf("Blue2","Green1","Red2","Blue3"),listOf("Blue2","Green1","Red2","Green2"),listOf("Blue2","Green1","Red2","Red1"),listOf("Blue2","Green1","Red2","Red3"),listOf("Blue2","Green1","Red3","Blue1"),
        listOf("Blue2","Green1","Red3","Blue3"),listOf("Blue2","Green1","Red3","Green2"),listOf("Blue2","Green1","Red3","Red1"),listOf("Blue2","Green1","Red3","Red2"),listOf("Blue2","Green2","Blue1","Blue3"),
        listOf("Blue2","Green2","Blue1","Green1"),listOf("Blue2","Green2","Blue1","Red1"),listOf("Blue2","Green2","Blue1","Red2"),listOf("Blue2","Green2","Blue1","Red3"),listOf("Blue2","Green2","Blue3","Blue1"),
        listOf("Blue2","Green2","Blue3","Green1"),listOf("Blue2","Green2","Blue3","Red1"),listOf("Blue2","Green2","Blue3","Red2"),listOf("Blue2","Green2","Blue3","Red3"),listOf("Blue2","Green2","Green1","Blue1"),
        listOf("Blue2","Green2","Green1","Blue3"),listOf("Blue2","Green2","Green1","Red1"),listOf("Blue2","Green2","Green1","Red2"),listOf("Blue2","Green2","Green1","Red3"),listOf("Blue2","Green2","Red1","Blue1"),
        listOf("Blue2","Green2","Red1","Blue3"),listOf("Blue2","Green2","Red1","Green1"),listOf("Blue2","Green2","Red1","Red2"),listOf("Blue2","Green2","Red1","Red3"),listOf("Blue2","Green2","Red2","Blue1"),
        listOf("Blue2","Green2","Red2","Blue3"),listOf("Blue2","Green2","Red2","Green1"),listOf("Blue2","Green2","Red2","Red1"),listOf("Blue2","Green2","Red2","Red3"),listOf("Blue2","Green2","Red3","Blue1"),
        listOf("Blue2","Green2","Red3","Blue3"),listOf("Blue2","Green2","Red3","Green1"),listOf("Blue2","Green2","Red3","Red1"),listOf("Blue2","Green2","Red3","Red2"),listOf("Blue2","Red1","Blue1","Blue3"),
        listOf("Blue2","Red1","Blue1","Green1"),listOf("Blue2","Red1","Blue1","Green2"),listOf("Blue2","Red1","Blue1","Red2"),listOf("Blue2","Red1","Blue1","Red3"),listOf("Blue2","Red1","Blue3","Blue1"),
        listOf("Blue2","Red1","Blue3","Green1"),listOf("Blue2","Red1","Blue3","Green2"),listOf("Blue2","Red1","Blue3","Red2"),listOf("Blue2","Red1","Blue3","Red3"),listOf("Blue2","Red1","Green1","Blue1"),
        listOf("Blue2","Red1","Green1","Blue3"),listOf("Blue2","Red1","Green1","Green2"),listOf("Blue2","Red1","Green1","Red2"),listOf("Blue2","Red1","Green1","Red3"),listOf("Blue2","Red1","Green2","Blue1"),
        listOf("Blue2","Red1","Green2","Blue3"),listOf("Blue2","Red1","Green2","Green1"),listOf("Blue2","Red1","Green2","Red2"),listOf("Blue2","Red1","Green2","Red3"),listOf("Blue2","Red1","Red2","Blue1"),
        listOf("Blue2","Red1","Red2","Blue3"),listOf("Blue2","Red1","Red2","Green1"),listOf("Blue2","Red1","Red2","Green2"),listOf("Blue2","Red1","Red3","Blue1"),listOf("Blue2","Red1","Red3","Blue3"),
        listOf("Blue2","Red1","Red3","Green1"),listOf("Blue2","Red1","Red3","Green2"),listOf("Blue2","Red2","Blue1","Blue3"),listOf("Blue2","Red2","Blue1","Green1"),listOf("Blue2","Red2","Blue1","Green2"),
        listOf("Blue2","Red2","Blue1","Red1"),listOf("Blue2","Red2","Blue1","Red3"),listOf("Blue2","Red2","Blue3","Blue1"),listOf("Blue2","Red2","Blue3","Green1"),listOf("Blue2","Red2","Blue3","Green2"),
        listOf("Blue2","Red2","Blue3","Red1"),listOf("Blue2","Red2","Blue3","Red3"),listOf("Blue2","Red2","Green1","Blue1"),listOf("Blue2","Red2","Green1","Blue3"),listOf("Blue2","Red2","Green1","Green2"),
        listOf("Blue2","Red2","Green1","Red1"),listOf("Blue2","Red2","Green1","Red3"),listOf("Blue2","Red2","Green2","Blue1"),listOf("Blue2","Red2","Green2","Blue3"),listOf("Blue2","Red2","Green2","Green1"),
        listOf("Blue2","Red2","Green2","Red1"),listOf("Blue2","Red2","Green2","Red3"),listOf("Blue2","Red2","Red1","Blue1"),listOf("Blue2","Red2","Red1","Blue3"),listOf("Blue2","Red2","Red1","Green1"),
        listOf("Blue2","Red2","Red1","Green2"),listOf("Blue2","Red2","Red3","Blue1"),listOf("Blue2","Red2","Red3","Blue3"),listOf("Blue2","Red2","Red3","Green1"),listOf("Blue2","Red2","Red3","Green2"),
        listOf("Blue2","Red3","Blue1","Blue3"),listOf("Blue2","Red3","Blue1","Green1"),listOf("Blue2","Red3","Blue1","Green2"),listOf("Blue2","Red3","Blue1","Red1"),listOf("Blue2","Red3","Blue1","Red2"),
        listOf("Blue2","Red3","Blue3","Blue1"),listOf("Blue2","Red3","Blue3","Green1"),listOf("Blue2","Red3","Blue3","Green2"),listOf("Blue2","Red3","Blue3","Red1"),listOf("Blue2","Red3","Blue3","Red2"),
        listOf("Blue2","Red3","Green1","Blue1"),listOf("Blue2","Red3","Green1","Blue3"),listOf("Blue2","Red3","Green1","Green2"),listOf("Blue2","Red3","Green1","Red1"),listOf("Blue2","Red3","Green1","Red2"),
        listOf("Blue2","Red3","Green2","Blue1"),listOf("Blue2","Red3","Green2","Blue3"),listOf("Blue2","Red3","Green2","Green1"),listOf("Blue2","Red3","Green2","Red1"),listOf("Blue2","Red3","Green2","Red2"),
        listOf("Blue2","Red3","Red1","Blue1"),listOf("Blue2","Red3","Red1","Blue3"),listOf("Blue2","Red3","Red1","Green1"),listOf("Blue2","Red3","Red1","Green2"),listOf("Blue2","Red3","Red2","Blue1"),
        listOf("Blue2","Red3","Red2","Blue3"),listOf("Blue2","Red3","Red2","Green1"),listOf("Blue2","Red3","Red2","Green2"),listOf("Blue3","Blue1","Blue2","Green1"),listOf("Blue3","Blue1","Blue2","Green2"),
        listOf("Blue3","Blue1","Blue2","Red1"),listOf("Blue3","Blue1","Blue2","Red2"),listOf("Blue3","Blue1","Blue2","Red3"),listOf("Blue3","Blue1","Green1","Blue2"),listOf("Blue3","Blue1","Green1","Green2"),
        listOf("Blue3","Blue1","Green1","Red1"),listOf("Blue3","Blue1","Green1","Red2"),listOf("Blue3","Blue1","Green1","Red3"),listOf("Blue3","Blue1","Green2","Blue2"),listOf("Blue3","Blue1","Green2","Green1"),
        listOf("Blue3","Blue1","Green2","Red1"),listOf("Blue3","Blue1","Green2","Red2"),listOf("Blue3","Blue1","Green2","Red3"),listOf("Blue3","Blue1","Red1","Blue2"),listOf("Blue3","Blue1","Red1","Green1"),
        listOf("Blue3","Blue1","Red1","Green2"),listOf("Blue3","Blue1","Red1","Red2"),listOf("Blue3","Blue1","Red1","Red3"),listOf("Blue3","Blue1","Red2","Blue2"),listOf("Blue3","Blue1","Red2","Green1"),
        listOf("Blue3","Blue1","Red2","Green2"),listOf("Blue3","Blue1","Red2","Red1"),listOf("Blue3","Blue1","Red2","Red3"),listOf("Blue3","Blue1","Red3","Blue2"),listOf("Blue3","Blue1","Red3","Green1"),
        listOf("Blue3","Blue1","Red3","Green2"),listOf("Blue3","Blue1","Red3","Red1"),listOf("Blue3","Blue1","Red3","Red2"),listOf("Blue3","Blue2","Blue1","Green1"),listOf("Blue3","Blue2","Blue1","Green2"),
        listOf("Blue3","Blue2","Blue1","Red1"),listOf("Blue3","Blue2","Blue1","Red2"),listOf("Blue3","Blue2","Blue1","Red3"),listOf("Blue3","Blue2","Green1","Blue1"),listOf("Blue3","Blue2","Green1","Green2"),
        listOf("Blue3","Blue2","Green1","Red1"),listOf("Blue3","Blue2","Green1","Red2"),listOf("Blue3","Blue2","Green1","Red3"),listOf("Blue3","Blue2","Green2","Blue1"),listOf("Blue3","Blue2","Green2","Green1"),
        listOf("Blue3","Blue2","Green2","Red1"),listOf("Blue3","Blue2","Green2","Red2"),listOf("Blue3","Blue2","Green2","Red3"),listOf("Blue3","Blue2","Red1","Blue1"),listOf("Blue3","Blue2","Red1","Green1"),
        listOf("Blue3","Blue2","Red1","Green2"),listOf("Blue3","Blue2","Red1","Red2"),listOf("Blue3","Blue2","Red1","Red3"),listOf("Blue3","Blue2","Red2","Blue1"),listOf("Blue3","Blue2","Red2","Green1"),
        listOf("Blue3","Blue2","Red2","Green2"),listOf("Blue3","Blue2","Red2","Red1"),listOf("Blue3","Blue2","Red2","Red3"),listOf("Blue3","Blue2","Red3","Blue1"),listOf("Blue3","Blue2","Red3","Green1"),
        listOf("Blue3","Blue2","Red3","Green2"),listOf("Blue3","Blue2","Red3","Red1"),listOf("Blue3","Blue2","Red3","Red2"),listOf("Blue3","Green1","Blue1","Blue2"),listOf("Blue3","Green1","Blue1","Green2"),
        listOf("Blue3","Green1","Blue1","Red1"),listOf("Blue3","Green1","Blue1","Red2"),listOf("Blue3","Green1","Blue1","Red3"),listOf("Blue3","Green1","Blue2","Blue1"),listOf("Blue3","Green1","Blue2","Green2"),
        listOf("Blue3","Green1","Blue2","Red1"),listOf("Blue3","Green1","Blue2","Red2"),listOf("Blue3","Green1","Blue2","Red3"),listOf("Blue3","Green1","Green2","Blue1"),listOf("Blue3","Green1","Green2","Blue2"),
        listOf("Blue3","Green1","Green2","Red1"),listOf("Blue3","Green1","Green2","Red2"),listOf("Blue3","Green1","Green2","Red3"),listOf("Blue3","Green1","Red1","Blue1"),listOf("Blue3","Green1","Red1","Blue2"),
        listOf("Blue3","Green1","Red1","Green2"),listOf("Blue3","Green1","Red1","Red2"),listOf("Blue3","Green1","Red1","Red3"),listOf("Blue3","Green1","Red2","Blue1"),listOf("Blue3","Green1","Red2","Blue2"),
        listOf("Blue3","Green1","Red2","Green2"),listOf("Blue3","Green1","Red2","Red1"),listOf("Blue3","Green1","Red2","Red3"),listOf("Blue3","Green1","Red3","Blue1"),listOf("Blue3","Green1","Red3","Blue2"),
        listOf("Blue3","Green1","Red3","Green2"),listOf("Blue3","Green1","Red3","Red1"),listOf("Blue3","Green1","Red3","Red2"),listOf("Blue3","Green2","Blue1","Blue2"),listOf("Blue3","Green2","Blue1","Green1"),
        listOf("Blue3","Green2","Blue1","Red1"),listOf("Blue3","Green2","Blue1","Red2"),listOf("Blue3","Green2","Blue1","Red3"),listOf("Blue3","Green2","Blue2","Blue1"),listOf("Blue3","Green2","Blue2","Green1"),
        listOf("Blue3","Green2","Blue2","Red1"),listOf("Blue3","Green2","Blue2","Red2"),listOf("Blue3","Green2","Blue2","Red3"),listOf("Blue3","Green2","Green1","Blue1"),listOf("Blue3","Green2","Green1","Blue2"),
        listOf("Blue3","Green2","Green1","Red1"),listOf("Blue3","Green2","Green1","Red2"),listOf("Blue3","Green2","Green1","Red3"),listOf("Blue3","Green2","Red1","Blue1"),listOf("Blue3","Green2","Red1","Blue2"),
        listOf("Blue3","Green2","Red1","Green1"),listOf("Blue3","Green2","Red1","Red2"),listOf("Blue3","Green2","Red1","Red3"),listOf("Blue3","Green2","Red2","Blue1"),listOf("Blue3","Green2","Red2","Blue2"),
        listOf("Blue3","Green2","Red2","Green1"),listOf("Blue3","Green2","Red2","Red1"),listOf("Blue3","Green2","Red2","Red3"),listOf("Blue3","Green2","Red3","Blue1"),listOf("Blue3","Green2","Red3","Blue2"),
        listOf("Blue3","Green2","Red3","Green1"),listOf("Blue3","Green2","Red3","Red1"),listOf("Blue3","Green2","Red3","Red2"),listOf("Blue3","Red1","Blue1","Blue2"),listOf("Blue3","Red1","Blue1","Green1"),
        listOf("Blue3","Red1","Blue1","Green2"),listOf("Blue3","Red1","Blue1","Red2"),listOf("Blue3","Red1","Blue1","Red3"),listOf("Blue3","Red1","Blue2","Blue1"),listOf("Blue3","Red1","Blue2","Green1"),
        listOf("Blue3","Red1","Blue2","Green2"),listOf("Blue3","Red1","Blue2","Red2"),listOf("Blue3","Red1","Blue2","Red3"),listOf("Blue3","Red1","Green1","Blue1"),listOf("Blue3","Red1","Green1","Blue2"),
        listOf("Blue3","Red1","Green1","Green2"),listOf("Blue3","Red1","Green1","Red2"),listOf("Blue3","Red1","Green1","Red3"),listOf("Blue3","Red1","Green2","Blue1"),listOf("Blue3","Red1","Green2","Blue2"),
        listOf("Blue3","Red1","Green2","Green1"),listOf("Blue3","Red1","Green2","Red2"),listOf("Blue3","Red1","Green2","Red3"),listOf("Blue3","Red1","Red2","Blue1"),listOf("Blue3","Red1","Red2","Blue2"),
        listOf("Blue3","Red1","Red2","Green1"),listOf("Blue3","Red1","Red2","Green2"),listOf("Blue3","Red1","Red3","Blue1"),listOf("Blue3","Red1","Red3","Blue2"),listOf("Blue3","Red1","Red3","Green1"),
        listOf("Blue3","Red1","Red3","Green2"),listOf("Blue3","Red2","Blue1","Blue2"),listOf("Blue3","Red2","Blue1","Green1"),listOf("Blue3","Red2","Blue1","Green2"),listOf("Blue3","Red2","Blue1","Red1"),
        listOf("Blue3","Red2","Blue1","Red3"),listOf("Blue3","Red2","Blue2","Blue1"),listOf("Blue3","Red2","Blue2","Green1"),listOf("Blue3","Red2","Blue2","Green2"),listOf("Blue3","Red2","Blue2","Red1"),
        listOf("Blue3","Red2","Blue2","Red3"),listOf("Blue3","Red2","Green1","Blue1"),listOf("Blue3","Red2","Green1","Blue2"),listOf("Blue3","Red2","Green1","Green2"),listOf("Blue3","Red2","Green1","Red1"),
        listOf("Blue3","Red2","Green1","Red3"),listOf("Blue3","Red2","Green2","Blue1"),listOf("Blue3","Red2","Green2","Blue2"),listOf("Blue3","Red2","Green2","Green1"),listOf("Blue3","Red2","Green2","Red1"),
        listOf("Blue3","Red2","Green2","Red3"),listOf("Blue3","Red2","Red1","Blue1"),listOf("Blue3","Red2","Red1","Blue2"),listOf("Blue3","Red2","Red1","Green1"),listOf("Blue3","Red2","Red1","Green2"),
        listOf("Blue3","Red2","Red3","Blue1"),listOf("Blue3","Red2","Red3","Blue2"),listOf("Blue3","Red2","Red3","Green1"),listOf("Blue3","Red2","Red3","Green2"),listOf("Blue3","Red3","Blue1","Blue2"),
        listOf("Blue3","Red3","Blue1","Green1"),listOf("Blue3","Red3","Blue1","Green2"),listOf("Blue3","Red3","Blue1","Red1"),listOf("Blue3","Red3","Blue1","Red2"),listOf("Blue3","Red3","Blue2","Blue1"),
        listOf("Blue3","Red3","Blue2","Green1"),listOf("Blue3","Red3","Blue2","Green2"),listOf("Blue3","Red3","Blue2","Red1"),listOf("Blue3","Red3","Blue2","Red2"),listOf("Blue3","Red3","Green1","Blue1"),
        listOf("Blue3","Red3","Green1","Blue2"),listOf("Blue3","Red3","Green1","Green2"),listOf("Blue3","Red3","Green1","Red1"),listOf("Blue3","Red3","Green1","Red2"),listOf("Blue3","Red3","Green2","Blue1"),
        listOf("Blue3","Red3","Green2","Blue2"),listOf("Blue3","Red3","Green2","Green1"),listOf("Blue3","Red3","Green2","Red1"),listOf("Blue3","Red3","Green2","Red2"),listOf("Blue3","Red3","Red1","Blue1"),
        listOf("Blue3","Red3","Red1","Blue2"),listOf("Blue3","Red3","Red1","Green1"),listOf("Blue3","Red3","Red1","Green2"),listOf("Blue3","Red3","Red2","Blue1"),listOf("Blue3","Red3","Red2","Blue2"),
        listOf("Blue3","Red3","Red2","Green1"),listOf("Blue3","Red3","Red2","Green2"),listOf("Green1","Blue1","Blue2","Blue3"),listOf("Green1","Blue1","Blue2","Green2"),listOf("Green1","Blue1","Blue2","Red1"),
        listOf("Green1","Blue1","Blue2","Red2"),listOf("Green1","Blue1","Blue2","Red3"),listOf("Green1","Blue1","Blue3","Blue2"),listOf("Green1","Blue1","Blue3","Green2"),listOf("Green1","Blue1","Blue3","Red1"),
        listOf("Green1","Blue1","Blue3","Red2"),listOf("Green1","Blue1","Blue3","Red3"),listOf("Green1","Blue1","Green2","Blue2"),listOf("Green1","Blue1","Green2","Blue3"),listOf("Green1","Blue1","Green2","Red1"),
        listOf("Green1","Blue1","Green2","Red2"),listOf("Green1","Blue1","Green2","Red3"),listOf("Green1","Blue1","Red1","Blue2"),listOf("Green1","Blue1","Red1","Blue3"),listOf("Green1","Blue1","Red1","Green2"),
        listOf("Green1","Blue1","Red1","Red2"),listOf("Green1","Blue1","Red1","Red3"),listOf("Green1","Blue1","Red2","Blue2"),listOf("Green1","Blue1","Red2","Blue3"),listOf("Green1","Blue1","Red2","Green2"),
        listOf("Green1","Blue1","Red2","Red1"),listOf("Green1","Blue1","Red2","Red3"),listOf("Green1","Blue1","Red3","Blue2"),listOf("Green1","Blue1","Red3","Blue3"),listOf("Green1","Blue1","Red3","Green2"),
        listOf("Green1","Blue1","Red3","Red1"),listOf("Green1","Blue1","Red3","Red2"),listOf("Green1","Blue2","Blue1","Blue3"),listOf("Green1","Blue2","Blue1","Green2"),listOf("Green1","Blue2","Blue1","Red1"),
        listOf("Green1","Blue2","Blue1","Red2"),listOf("Green1","Blue2","Blue1","Red3"),listOf("Green1","Blue2","Blue3","Blue1"),listOf("Green1","Blue2","Blue3","Green2"),listOf("Green1","Blue2","Blue3","Red1"),
        listOf("Green1","Blue2","Blue3","Red2"),listOf("Green1","Blue2","Blue3","Red3"),listOf("Green1","Blue2","Green2","Blue1"),listOf("Green1","Blue2","Green2","Blue3"),listOf("Green1","Blue2","Green2","Red1"),
        listOf("Green1","Blue2","Green2","Red2"),listOf("Green1","Blue2","Green2","Red3"),listOf("Green1","Blue2","Red1","Blue1"),listOf("Green1","Blue2","Red1","Blue3"),listOf("Green1","Blue2","Red1","Green2"),
        listOf("Green1","Blue2","Red1","Red2"),listOf("Green1","Blue2","Red1","Red3"),listOf("Green1","Blue2","Red2","Blue1"),listOf("Green1","Blue2","Red2","Blue3"),listOf("Green1","Blue2","Red2","Green2"),
        listOf("Green1","Blue2","Red2","Red1"),listOf("Green1","Blue2","Red2","Red3"),listOf("Green1","Blue2","Red3","Blue1"),listOf("Green1","Blue2","Red3","Blue3"),listOf("Green1","Blue2","Red3","Green2"),
        listOf("Green1","Blue2","Red3","Red1"),listOf("Green1","Blue2","Red3","Red2"),listOf("Green1","Blue3","Blue1","Blue2"),listOf("Green1","Blue3","Blue1","Green2"),listOf("Green1","Blue3","Blue1","Red1"),
        listOf("Green1","Blue3","Blue1","Red2"),listOf("Green1","Blue3","Blue1","Red3"),listOf("Green1","Blue3","Blue2","Blue1"),listOf("Green1","Blue3","Blue2","Green2"),listOf("Green1","Blue3","Blue2","Red1"),
        listOf("Green1","Blue3","Blue2","Red2"),listOf("Green1","Blue3","Blue2","Red3"),listOf("Green1","Blue3","Green2","Blue1"),listOf("Green1","Blue3","Green2","Blue2"),listOf("Green1","Blue3","Green2","Red1"),
        listOf("Green1","Blue3","Green2","Red2"),listOf("Green1","Blue3","Green2","Red3"),listOf("Green1","Blue3","Red1","Blue1"),listOf("Green1","Blue3","Red1","Blue2"),listOf("Green1","Blue3","Red1","Green2"),
        listOf("Green1","Blue3","Red1","Red2"),listOf("Green1","Blue3","Red1","Red3"),listOf("Green1","Blue3","Red2","Blue1"),listOf("Green1","Blue3","Red2","Blue2"),listOf("Green1","Blue3","Red2","Green2"),
        listOf("Green1","Blue3","Red2","Red1"),listOf("Green1","Blue3","Red2","Red3"),listOf("Green1","Blue3","Red3","Blue1"),listOf("Green1","Blue3","Red3","Blue2"),listOf("Green1","Blue3","Red3","Green2"),
        listOf("Green1","Blue3","Red3","Red1"),listOf("Green1","Blue3","Red3","Red2"),listOf("Green1","Green2","Blue1","Blue2"),listOf("Green1","Green2","Blue1","Blue3"),listOf("Green1","Green2","Blue1","Red1"),
        listOf("Green1","Green2","Blue1","Red2"),listOf("Green1","Green2","Blue1","Red3"),listOf("Green1","Green2","Blue2","Blue1"),listOf("Green1","Green2","Blue2","Blue3"),listOf("Green1","Green2","Blue2","Red1"),
        listOf("Green1","Green2","Blue2","Red2"),listOf("Green1","Green2","Blue2","Red3"),listOf("Green1","Green2","Blue3","Blue1"),listOf("Green1","Green2","Blue3","Blue2"),listOf("Green1","Green2","Blue3","Red1"),
        listOf("Green1","Green2","Blue3","Red2"),listOf("Green1","Green2","Blue3","Red3"),listOf("Green1","Green2","Red1","Blue1"),listOf("Green1","Green2","Red1","Blue2"),listOf("Green1","Green2","Red1","Blue3"),
        listOf("Green1","Green2","Red1","Red2"),listOf("Green1","Green2","Red1","Red3"),listOf("Green1","Green2","Red2","Blue1"),listOf("Green1","Green2","Red2","Blue2"),listOf("Green1","Green2","Red2","Blue3"),
        listOf("Green1","Green2","Red2","Red1"),listOf("Green1","Green2","Red2","Red3"),listOf("Green1","Green2","Red3","Blue1"),listOf("Green1","Green2","Red3","Blue2"),listOf("Green1","Green2","Red3","Blue3"),
        listOf("Green1","Green2","Red3","Red1"),listOf("Green1","Green2","Red3","Red2"),listOf("Green1","Red1","Blue1","Blue2"),listOf("Green1","Red1","Blue1","Blue3"),listOf("Green1","Red1","Blue1","Green2"),
        listOf("Green1","Red1","Blue1","Red2"),listOf("Green1","Red1","Blue1","Red3"),listOf("Green1","Red1","Blue2","Blue1"),listOf("Green1","Red1","Blue2","Blue3"),listOf("Green1","Red1","Blue2","Green2"),
        listOf("Green1","Red1","Blue2","Red2"),listOf("Green1","Red1","Blue2","Red3"),listOf("Green1","Red1","Blue3","Blue1"),listOf("Green1","Red1","Blue3","Blue2"),listOf("Green1","Red1","Blue3","Green2"),
        listOf("Green1","Red1","Blue3","Red2"),listOf("Green1","Red1","Blue3","Red3"),listOf("Green1","Red1","Green2","Blue1"),listOf("Green1","Red1","Green2","Blue2"),listOf("Green1","Red1","Green2","Blue3"),
        listOf("Green1","Red1","Green2","Red2"),listOf("Green1","Red1","Green2","Red3"),listOf("Green1","Red1","Red2","Blue1"),listOf("Green1","Red1","Red2","Blue2"),listOf("Green1","Red1","Red2","Blue3"),
        listOf("Green1","Red1","Red2","Green2"),listOf("Green1","Red1","Red2","Red3"),listOf("Green1","Red1","Red3","Blue1"),listOf("Green1","Red1","Red3","Blue2"),listOf("Green1","Red1","Red3","Blue3"),
        listOf("Green1","Red1","Red3","Green2"),listOf("Green1","Red1","Red3","Red2"),listOf("Green1","Red2","Blue1","Blue2"),listOf("Green1","Red2","Blue1","Blue3"),listOf("Green1","Red2","Blue1","Green2"),
        listOf("Green1","Red2","Blue1","Red1"),listOf("Green1","Red2","Blue1","Red3"),listOf("Green1","Red2","Blue2","Blue1"),listOf("Green1","Red2","Blue2","Blue3"),listOf("Green1","Red2","Blue2","Green2"),
        listOf("Green1","Red2","Blue2","Red1"),listOf("Green1","Red2","Blue2","Red3"),listOf("Green1","Red2","Blue3","Blue1"),listOf("Green1","Red2","Blue3","Blue2"),listOf("Green1","Red2","Blue3","Green2"),
        listOf("Green1","Red2","Blue3","Red1"),listOf("Green1","Red2","Blue3","Red3"),listOf("Green1","Red2","Green2","Blue1"),listOf("Green1","Red2","Green2","Blue2"),listOf("Green1","Red2","Green2","Blue3"),
        listOf("Green1","Red2","Green2","Red1"),listOf("Green1","Red2","Green2","Red3"),listOf("Green1","Red2","Red1","Blue1"),listOf("Green1","Red2","Red1","Blue2"),listOf("Green1","Red2","Red1","Blue3"),
        listOf("Green1","Red2","Red1","Green2"),listOf("Green1","Red2","Red1","Red3"),listOf("Green1","Red2","Red3","Blue1"),listOf("Green1","Red2","Red3","Blue2"),listOf("Green1","Red2","Red3","Blue3"),
        listOf("Green1","Red2","Red3","Green2"),listOf("Green1","Red2","Red3","Red1"),listOf("Green1","Red3","Blue1","Blue2"),listOf("Green1","Red3","Blue1","Blue3"),listOf("Green1","Red3","Blue1","Green2"),
        listOf("Green1","Red3","Blue1","Red1"),listOf("Green1","Red3","Blue1","Red2"),listOf("Green1","Red3","Blue2","Blue1"),listOf("Green1","Red3","Blue2","Blue3"),listOf("Green1","Red3","Blue2","Green2"),
        listOf("Green1","Red3","Blue2","Red1"),listOf("Green1","Red3","Blue2","Red2"),listOf("Green1","Red3","Blue3","Blue1"),listOf("Green1","Red3","Blue3","Blue2"),listOf("Green1","Red3","Blue3","Green2"),
        listOf("Green1","Red3","Blue3","Red1"),listOf("Green1","Red3","Blue3","Red2"),listOf("Green1","Red3","Green2","Blue1"),listOf("Green1","Red3","Green2","Blue2"),listOf("Green1","Red3","Green2","Blue3"),
        listOf("Green1","Red3","Green2","Red1"),listOf("Green1","Red3","Green2","Red2"),listOf("Green1","Red3","Red1","Blue1"),listOf("Green1","Red3","Red1","Blue2"),listOf("Green1","Red3","Red1","Blue3"),
        listOf("Green1","Red3","Red1","Green2"),listOf("Green1","Red3","Red1","Red2"),listOf("Green1","Red3","Red2","Blue1"),listOf("Green1","Red3","Red2","Blue2"),listOf("Green1","Red3","Red2","Blue3"),
        listOf("Green1","Red3","Red2","Green2"),listOf("Green1","Red3","Red2","Red1"),listOf("Green2","Blue1","Blue2","Blue3"),listOf("Green2","Blue1","Blue2","Green1"),listOf("Green2","Blue1","Blue2","Red1"),
        listOf("Green2","Blue1","Blue2","Red2"),listOf("Green2","Blue1","Blue2","Red3"),listOf("Green2","Blue1","Blue3","Blue2"),listOf("Green2","Blue1","Blue3","Green1"),listOf("Green2","Blue1","Blue3","Red1"),
        listOf("Green2","Blue1","Blue3","Red2"),listOf("Green2","Blue1","Blue3","Red3"),listOf("Green2","Blue1","Green1","Blue2"),listOf("Green2","Blue1","Green1","Blue3"),listOf("Green2","Blue1","Green1","Red1"),
        listOf("Green2","Blue1","Green1","Red2"),listOf("Green2","Blue1","Green1","Red3"),listOf("Green2","Blue1","Red1","Blue2"),listOf("Green2","Blue1","Red1","Blue3"),listOf("Green2","Blue1","Red1","Green1"),
        listOf("Green2","Blue1","Red1","Red2"),listOf("Green2","Blue1","Red1","Red3"),listOf("Green2","Blue1","Red2","Blue2"),listOf("Green2","Blue1","Red2","Blue3"),listOf("Green2","Blue1","Red2","Green1"),
        listOf("Green2","Blue1","Red2","Red1"),listOf("Green2","Blue1","Red2","Red3"),listOf("Green2","Blue1","Red3","Blue2"),listOf("Green2","Blue1","Red3","Blue3"),listOf("Green2","Blue1","Red3","Green1"),
        listOf("Green2","Blue1","Red3","Red1"),listOf("Green2","Blue1","Red3","Red2"),listOf("Green2","Blue2","Blue1","Blue3"),listOf("Green2","Blue2","Blue1","Green1"),listOf("Green2","Blue2","Blue1","Red1"),
        listOf("Green2","Blue2","Blue1","Red2"),listOf("Green2","Blue2","Blue1","Red3"),listOf("Green2","Blue2","Blue3","Blue1"),listOf("Green2","Blue2","Blue3","Green1"),listOf("Green2","Blue2","Blue3","Red1"),
        listOf("Green2","Blue2","Blue3","Red2"),listOf("Green2","Blue2","Blue3","Red3"),listOf("Green2","Blue2","Green1","Blue1"),listOf("Green2","Blue2","Green1","Blue3"),listOf("Green2","Blue2","Green1","Red1"),
        listOf("Green2","Blue2","Green1","Red2"),listOf("Green2","Blue2","Green1","Red3"),listOf("Green2","Blue2","Red1","Blue1"),listOf("Green2","Blue2","Red1","Blue3"),listOf("Green2","Blue2","Red1","Green1"),
        listOf("Green2","Blue2","Red1","Red2"),listOf("Green2","Blue2","Red1","Red3"),listOf("Green2","Blue2","Red2","Blue1"),listOf("Green2","Blue2","Red2","Blue3"),listOf("Green2","Blue2","Red2","Green1"),
        listOf("Green2","Blue2","Red2","Red1"),listOf("Green2","Blue2","Red2","Red3"),listOf("Green2","Blue2","Red3","Blue1"),listOf("Green2","Blue2","Red3","Blue3"),listOf("Green2","Blue2","Red3","Green1"),
        listOf("Green2","Blue2","Red3","Red1"),listOf("Green2","Blue2","Red3","Red2"),listOf("Green2","Blue3","Blue1","Blue2"),listOf("Green2","Blue3","Blue1","Green1"),listOf("Green2","Blue3","Blue1","Red1"),
        listOf("Green2","Blue3","Blue1","Red2"),listOf("Green2","Blue3","Blue1","Red3"),listOf("Green2","Blue3","Blue2","Blue1"),listOf("Green2","Blue3","Blue2","Green1"),listOf("Green2","Blue3","Blue2","Red1"),
        listOf("Green2","Blue3","Blue2","Red2"),listOf("Green2","Blue3","Blue2","Red3"),listOf("Green2","Blue3","Green1","Blue1"),listOf("Green2","Blue3","Green1","Blue2"),listOf("Green2","Blue3","Green1","Red1"),
        listOf("Green2","Blue3","Green1","Red2"),listOf("Green2","Blue3","Green1","Red3"),listOf("Green2","Blue3","Red1","Blue1"),listOf("Green2","Blue3","Red1","Blue2"),listOf("Green2","Blue3","Red1","Green1"),
        listOf("Green2","Blue3","Red1","Red2"),listOf("Green2","Blue3","Red1","Red3"),listOf("Green2","Blue3","Red2","Blue1"),listOf("Green2","Blue3","Red2","Blue2"),listOf("Green2","Blue3","Red2","Green1"),
        listOf("Green2","Blue3","Red2","Red1"),listOf("Green2","Blue3","Red2","Red3"),listOf("Green2","Blue3","Red3","Blue1"),listOf("Green2","Blue3","Red3","Blue2"),listOf("Green2","Blue3","Red3","Green1"),
        listOf("Green2","Blue3","Red3","Red1"),listOf("Green2","Blue3","Red3","Red2"),listOf("Green2","Green1","Blue1","Blue2"),listOf("Green2","Green1","Blue1","Blue3"),listOf("Green2","Green1","Blue1","Red1"),
        listOf("Green2","Green1","Blue1","Red2"),listOf("Green2","Green1","Blue1","Red3"),listOf("Green2","Green1","Blue2","Blue1"),listOf("Green2","Green1","Blue2","Blue3"),listOf("Green2","Green1","Blue2","Red1"),
        listOf("Green2","Green1","Blue2","Red2"),listOf("Green2","Green1","Blue2","Red3"),listOf("Green2","Green1","Blue3","Blue1"),listOf("Green2","Green1","Blue3","Blue2"),listOf("Green2","Green1","Blue3","Red1"),
        listOf("Green2","Green1","Blue3","Red2"),listOf("Green2","Green1","Blue3","Red3"),listOf("Green2","Green1","Red1","Blue1"),listOf("Green2","Green1","Red1","Blue2"),listOf("Green2","Green1","Red1","Blue3"),
        listOf("Green2","Green1","Red1","Red2"),listOf("Green2","Green1","Red1","Red3"),listOf("Green2","Green1","Red2","Blue1"),listOf("Green2","Green1","Red2","Blue2"),listOf("Green2","Green1","Red2","Blue3"),
        listOf("Green2","Green1","Red2","Red1"),listOf("Green2","Green1","Red2","Red3"),listOf("Green2","Green1","Red3","Blue1"),listOf("Green2","Green1","Red3","Blue2"),listOf("Green2","Green1","Red3","Blue3"),
        listOf("Green2","Green1","Red3","Red1"),listOf("Green2","Green1","Red3","Red2"),listOf("Green2","Red1","Blue1","Blue2"),listOf("Green2","Red1","Blue1","Blue3"),listOf("Green2","Red1","Blue1","Green1"),
        listOf("Green2","Red1","Blue1","Red2"),listOf("Green2","Red1","Blue1","Red3"),listOf("Green2","Red1","Blue2","Blue1"),listOf("Green2","Red1","Blue2","Blue3"),listOf("Green2","Red1","Blue2","Green1"),
        listOf("Green2","Red1","Blue2","Red2"),listOf("Green2","Red1","Blue2","Red3"),listOf("Green2","Red1","Blue3","Blue1"),listOf("Green2","Red1","Blue3","Blue2"),listOf("Green2","Red1","Blue3","Green1"),
        listOf("Green2","Red1","Blue3","Red2"),listOf("Green2","Red1","Blue3","Red3"),listOf("Green2","Red1","Green1","Blue1"),listOf("Green2","Red1","Green1","Blue2"),listOf("Green2","Red1","Green1","Blue3"),
        listOf("Green2","Red1","Green1","Red2"),listOf("Green2","Red1","Green1","Red3"),listOf("Green2","Red1","Red2","Blue1"),listOf("Green2","Red1","Red2","Blue2"),listOf("Green2","Red1","Red2","Blue3"),
        listOf("Green2","Red1","Red2","Green1"),listOf("Green2","Red1","Red2","Red3"),listOf("Green2","Red1","Red3","Blue1"),listOf("Green2","Red1","Red3","Blue2"),listOf("Green2","Red1","Red3","Blue3"),
        listOf("Green2","Red1","Red3","Green1"),listOf("Green2","Red1","Red3","Red2"),listOf("Green2","Red2","Blue1","Blue2"),listOf("Green2","Red2","Blue1","Blue3"),listOf("Green2","Red2","Blue1","Green1"),
        listOf("Green2","Red2","Blue1","Red1"),listOf("Green2","Red2","Blue1","Red3"),listOf("Green2","Red2","Blue2","Blue1"),listOf("Green2","Red2","Blue2","Blue3"),listOf("Green2","Red2","Blue2","Green1"),
        listOf("Green2","Red2","Blue2","Red1"),listOf("Green2","Red2","Blue2","Red3"),listOf("Green2","Red2","Blue3","Blue1"),listOf("Green2","Red2","Blue3","Blue2"),listOf("Green2","Red2","Blue3","Green1"),
        listOf("Green2","Red2","Blue3","Red1"),listOf("Green2","Red2","Blue3","Red3"),listOf("Green2","Red2","Green1","Blue1"),listOf("Green2","Red2","Green1","Blue2"),listOf("Green2","Red2","Green1","Blue3"),
        listOf("Green2","Red2","Green1","Red1"),listOf("Green2","Red2","Green1","Red3"),listOf("Green2","Red2","Red1","Blue1"),listOf("Green2","Red2","Red1","Blue2"),listOf("Green2","Red2","Red1","Blue3"),
        listOf("Green2","Red2","Red1","Green1"),listOf("Green2","Red2","Red1","Red3"),listOf("Green2","Red2","Red3","Blue1"),listOf("Green2","Red2","Red3","Blue2"),listOf("Green2","Red2","Red3","Blue3"),
        listOf("Green2","Red2","Red3","Green1"),listOf("Green2","Red2","Red3","Red1"),listOf("Green2","Red3","Blue1","Blue2"),listOf("Green2","Red3","Blue1","Blue3"),listOf("Green2","Red3","Blue1","Green1"),
        listOf("Green2","Red3","Blue1","Red1"),listOf("Green2","Red3","Blue1","Red2"),listOf("Green2","Red3","Blue2","Blue1"),listOf("Green2","Red3","Blue2","Blue3"),listOf("Green2","Red3","Blue2","Green1"),
        listOf("Green2","Red3","Blue2","Red1"),listOf("Green2","Red3","Blue2","Red2"),listOf("Green2","Red3","Blue3","Blue1"),listOf("Green2","Red3","Blue3","Blue2"),listOf("Green2","Red3","Blue3","Green1"),
        listOf("Green2","Red3","Blue3","Red1"),listOf("Green2","Red3","Blue3","Red2"),listOf("Green2","Red3","Green1","Blue1"),listOf("Green2","Red3","Green1","Blue2"),listOf("Green2","Red3","Green1","Blue3"),
        listOf("Green2","Red3","Green1","Red1"),listOf("Green2","Red3","Green1","Red2"),listOf("Green2","Red3","Red1","Blue1"),listOf("Green2","Red3","Red1","Blue2"),listOf("Green2","Red3","Red1","Blue3"),
        listOf("Green2","Red3","Red1","Green1"),listOf("Green2","Red3","Red1","Red2"),listOf("Green2","Red3","Red2","Blue1"),listOf("Green2","Red3","Red2","Blue2"),listOf("Green2","Red3","Red2","Blue3"),
        listOf("Green2","Red3","Red2","Green1"),listOf("Green2","Red3","Red2","Red1"),listOf("Red1","Blue1","Blue2","Blue3"),listOf("Red1","Blue1","Blue2","Green1"),listOf("Red1","Blue1","Blue2","Green2"),
        listOf("Red1","Blue1","Blue2","Red2"),listOf("Red1","Blue1","Blue2","Red3"),listOf("Red1","Blue1","Blue3","Blue2"),listOf("Red1","Blue1","Blue3","Green1"),listOf("Red1","Blue1","Blue3","Green2"),
        listOf("Red1","Blue1","Blue3","Red2"),listOf("Red1","Blue1","Blue3","Red3"),listOf("Red1","Blue1","Green1","Blue2"),listOf("Red1","Blue1","Green1","Blue3"),listOf("Red1","Blue1","Green1","Green2"),
        listOf("Red1","Blue1","Green1","Red2"),listOf("Red1","Blue1","Green1","Red3"),listOf("Red1","Blue1","Green2","Blue2"),listOf("Red1","Blue1","Green2","Blue3"),listOf("Red1","Blue1","Green2","Green1"),
        listOf("Red1","Blue1","Green2","Red2"),listOf("Red1","Blue1","Green2","Red3"),listOf("Red1","Blue1","Red2","Blue2"),listOf("Red1","Blue1","Red2","Blue3"),listOf("Red1","Blue1","Red2","Green1"),
        listOf("Red1","Blue1","Red2","Green2"),listOf("Red1","Blue1","Red3","Blue2"),listOf("Red1","Blue1","Red3","Blue3"),listOf("Red1","Blue1","Red3","Green1"),listOf("Red1","Blue1","Red3","Green2"),
        listOf("Red1","Blue2","Blue1","Blue3"),listOf("Red1","Blue2","Blue1","Green1"),listOf("Red1","Blue2","Blue1","Green2"),listOf("Red1","Blue2","Blue1","Red2"),listOf("Red1","Blue2","Blue1","Red3"),
        listOf("Red1","Blue2","Blue3","Blue1"),listOf("Red1","Blue2","Blue3","Green1"),listOf("Red1","Blue2","Blue3","Green2"),listOf("Red1","Blue2","Blue3","Red2"),listOf("Red1","Blue2","Blue3","Red3"),
        listOf("Red1","Blue2","Green1","Blue1"),listOf("Red1","Blue2","Green1","Blue3"),listOf("Red1","Blue2","Green1","Green2"),listOf("Red1","Blue2","Green1","Red2"),listOf("Red1","Blue2","Green1","Red3"),
        listOf("Red1","Blue2","Green2","Blue1"),listOf("Red1","Blue2","Green2","Blue3"),listOf("Red1","Blue2","Green2","Green1"),listOf("Red1","Blue2","Green2","Red2"),listOf("Red1","Blue2","Green2","Red3"),
        listOf("Red1","Blue2","Red2","Blue1"),listOf("Red1","Blue2","Red2","Blue3"),listOf("Red1","Blue2","Red2","Green1"),listOf("Red1","Blue2","Red2","Green2"),listOf("Red1","Blue2","Red3","Blue1"),
        listOf("Red1","Blue2","Red3","Blue3"),listOf("Red1","Blue2","Red3","Green1"),listOf("Red1","Blue2","Red3","Green2"),listOf("Red1","Blue3","Blue1","Blue2"),listOf("Red1","Blue3","Blue1","Green1"),
        listOf("Red1","Blue3","Blue1","Green2"),listOf("Red1","Blue3","Blue1","Red2"),listOf("Red1","Blue3","Blue1","Red3"),listOf("Red1","Blue3","Blue2","Blue1"),listOf("Red1","Blue3","Blue2","Green1"),
        listOf("Red1","Blue3","Blue2","Green2"),listOf("Red1","Blue3","Blue2","Red2"),listOf("Red1","Blue3","Blue2","Red3"),listOf("Red1","Blue3","Green1","Blue1"),listOf("Red1","Blue3","Green1","Blue2"),
        listOf("Red1","Blue3","Green1","Green2"),listOf("Red1","Blue3","Green1","Red2"),listOf("Red1","Blue3","Green1","Red3"),listOf("Red1","Blue3","Green2","Blue1"),listOf("Red1","Blue3","Green2","Blue2"),
        listOf("Red1","Blue3","Green2","Green1"),listOf("Red1","Blue3","Green2","Red2"),listOf("Red1","Blue3","Green2","Red3"),listOf("Red1","Blue3","Red2","Blue1"),listOf("Red1","Blue3","Red2","Blue2"),
        listOf("Red1","Blue3","Red2","Green1"),listOf("Red1","Blue3","Red2","Green2"),listOf("Red1","Blue3","Red3","Blue1"),listOf("Red1","Blue3","Red3","Blue2"),listOf("Red1","Blue3","Red3","Green1"),
        listOf("Red1","Blue3","Red3","Green2"),listOf("Red1","Green1","Blue1","Blue2"),listOf("Red1","Green1","Blue1","Blue3"),listOf("Red1","Green1","Blue1","Green2"),listOf("Red1","Green1","Blue1","Red2"),
        listOf("Red1","Green1","Blue1","Red3"),listOf("Red1","Green1","Blue2","Blue1"),listOf("Red1","Green1","Blue2","Blue3"),listOf("Red1","Green1","Blue2","Green2"),listOf("Red1","Green1","Blue2","Red2"),
        listOf("Red1","Green1","Blue2","Red3"),listOf("Red1","Green1","Blue3","Blue1"),listOf("Red1","Green1","Blue3","Blue2"),listOf("Red1","Green1","Blue3","Green2"),listOf("Red1","Green1","Blue3","Red2"),
        listOf("Red1","Green1","Blue3","Red3"),listOf("Red1","Green1","Green2","Blue1"),listOf("Red1","Green1","Green2","Blue2"),listOf("Red1","Green1","Green2","Blue3"),listOf("Red1","Green1","Green2","Red2"),
        listOf("Red1","Green1","Green2","Red3"),listOf("Red1","Green1","Red2","Blue1"),listOf("Red1","Green1","Red2","Blue2"),listOf("Red1","Green1","Red2","Blue3"),listOf("Red1","Green1","Red2","Green2"),
        listOf("Red1","Green1","Red2","Red3"),listOf("Red1","Green1","Red3","Blue1"),listOf("Red1","Green1","Red3","Blue2"),listOf("Red1","Green1","Red3","Blue3"),listOf("Red1","Green1","Red3","Green2"),
        listOf("Red1","Green1","Red3","Red2"),listOf("Red1","Green2","Blue1","Blue2"),listOf("Red1","Green2","Blue1","Blue3"),listOf("Red1","Green2","Blue1","Green1"),listOf("Red1","Green2","Blue1","Red2"),
        listOf("Red1","Green2","Blue1","Red3"),listOf("Red1","Green2","Blue2","Blue1"),listOf("Red1","Green2","Blue2","Blue3"),listOf("Red1","Green2","Blue2","Green1"),listOf("Red1","Green2","Blue2","Red2"),
        listOf("Red1","Green2","Blue2","Red3"),listOf("Red1","Green2","Blue3","Blue1"),listOf("Red1","Green2","Blue3","Blue2"),listOf("Red1","Green2","Blue3","Green1"),listOf("Red1","Green2","Blue3","Red2"),
        listOf("Red1","Green2","Blue3","Red3"),listOf("Red1","Green2","Green1","Blue1"),listOf("Red1","Green2","Green1","Blue2"),listOf("Red1","Green2","Green1","Blue3"),listOf("Red1","Green2","Green1","Red2"),
        listOf("Red1","Green2","Green1","Red3"),listOf("Red1","Green2","Red2","Blue1"),listOf("Red1","Green2","Red2","Blue2"),listOf("Red1","Green2","Red2","Blue3"),listOf("Red1","Green2","Red2","Green1"),
        listOf("Red1","Green2","Red2","Red3"),listOf("Red1","Green2","Red3","Blue1"),listOf("Red1","Green2","Red3","Blue2"),listOf("Red1","Green2","Red3","Blue3"),listOf("Red1","Green2","Red3","Green1"),
        listOf("Red1","Green2","Red3","Red2"),listOf("Red1","Red2","Blue1","Blue2"),listOf("Red1","Red2","Blue1","Blue3"),listOf("Red1","Red2","Blue1","Green1"),listOf("Red1","Red2","Blue1","Green2"),
        listOf("Red1","Red2","Blue2","Blue1"),listOf("Red1","Red2","Blue2","Blue3"),listOf("Red1","Red2","Blue2","Green1"),listOf("Red1","Red2","Blue2","Green2"),listOf("Red1","Red2","Blue3","Blue1"),
        listOf("Red1","Red2","Blue3","Blue2"),listOf("Red1","Red2","Blue3","Green1"),listOf("Red1","Red2","Blue3","Green2"),listOf("Red1","Red2","Green1","Blue1"),listOf("Red1","Red2","Green1","Blue2"),
        listOf("Red1","Red2","Green1","Blue3"),listOf("Red1","Red2","Green1","Green2"),listOf("Red1","Red2","Green1","Red3"),listOf("Red1","Red2","Green2","Blue1"),listOf("Red1","Red2","Green2","Blue2"),
        listOf("Red1","Red2","Green2","Blue3"),listOf("Red1","Red2","Green2","Green1"),listOf("Red1","Red2","Green2","Red3"),listOf("Red1","Red2","Red3","Green1"),listOf("Red1","Red2","Red3","Green2"),
        listOf("Red1","Red3","Blue1","Blue2"),listOf("Red1","Red3","Blue1","Blue3"),listOf("Red1","Red3","Blue1","Green1"),listOf("Red1","Red3","Blue1","Green2"),listOf("Red1","Red3","Blue2","Blue1"),
        listOf("Red1","Red3","Blue2","Blue3"),listOf("Red1","Red3","Blue2","Green1"),listOf("Red1","Red3","Blue2","Green2"),listOf("Red1","Red3","Blue3","Blue1"),listOf("Red1","Red3","Blue3","Blue2"),
        listOf("Red1","Red3","Blue3","Green1"),listOf("Red1","Red3","Blue3","Green2"),listOf("Red1","Red3","Green1","Blue1"),listOf("Red1","Red3","Green1","Blue2"),listOf("Red1","Red3","Green1","Blue3"),
        listOf("Red1","Red3","Green1","Green2"),listOf("Red1","Red3","Green1","Red2"),listOf("Red1","Red3","Green2","Blue1"),listOf("Red1","Red3","Green2","Blue2"),listOf("Red1","Red3","Green2","Blue3"),
        listOf("Red1","Red3","Green2","Green1"),listOf("Red1","Red3","Green2","Red2"),listOf("Red1","Red3","Red2","Green1"),listOf("Red1","Red3","Red2","Green2"),listOf("Red2","Blue1","Blue2","Blue3"),
        listOf("Red2","Blue1","Blue2","Green1"),listOf("Red2","Blue1","Blue2","Green2"),listOf("Red2","Blue1","Blue2","Red1"),listOf("Red2","Blue1","Blue2","Red3"),listOf("Red2","Blue1","Blue3","Blue2"),
        listOf("Red2","Blue1","Blue3","Green1"),listOf("Red2","Blue1","Blue3","Green2"),listOf("Red2","Blue1","Blue3","Red1"),listOf("Red2","Blue1","Blue3","Red3"),listOf("Red2","Blue1","Green1","Blue2"),
        listOf("Red2","Blue1","Green1","Blue3"),listOf("Red2","Blue1","Green1","Green2"),listOf("Red2","Blue1","Green1","Red1"),listOf("Red2","Blue1","Green1","Red3"),listOf("Red2","Blue1","Green2","Blue2"),
        listOf("Red2","Blue1","Green2","Blue3"),listOf("Red2","Blue1","Green2","Green1"),listOf("Red2","Blue1","Green2","Red1"),listOf("Red2","Blue1","Green2","Red3"),listOf("Red2","Blue1","Red1","Blue2"),
        listOf("Red2","Blue1","Red1","Blue3"),listOf("Red2","Blue1","Red1","Green1"),listOf("Red2","Blue1","Red1","Green2"),listOf("Red2","Blue1","Red3","Blue2"),listOf("Red2","Blue1","Red3","Blue3"),
        listOf("Red2","Blue1","Red3","Green1"),listOf("Red2","Blue1","Red3","Green2"),listOf("Red2","Blue2","Blue1","Blue3"),listOf("Red2","Blue2","Blue1","Green1"),listOf("Red2","Blue2","Blue1","Green2"),
        listOf("Red2","Blue2","Blue1","Red1"),listOf("Red2","Blue2","Blue1","Red3"),listOf("Red2","Blue2","Blue3","Blue1"),listOf("Red2","Blue2","Blue3","Green1"),listOf("Red2","Blue2","Blue3","Green2"),
        listOf("Red2","Blue2","Blue3","Red1"),listOf("Red2","Blue2","Blue3","Red3"),listOf("Red2","Blue2","Green1","Blue1"),listOf("Red2","Blue2","Green1","Blue3"),listOf("Red2","Blue2","Green1","Green2"),
        listOf("Red2","Blue2","Green1","Red1"),listOf("Red2","Blue2","Green1","Red3"),listOf("Red2","Blue2","Green2","Blue1"),listOf("Red2","Blue2","Green2","Blue3"),listOf("Red2","Blue2","Green2","Green1"),
        listOf("Red2","Blue2","Green2","Red1"),listOf("Red2","Blue2","Green2","Red3"),listOf("Red2","Blue2","Red1","Blue1"),listOf("Red2","Blue2","Red1","Blue3"),listOf("Red2","Blue2","Red1","Green1"),
        listOf("Red2","Blue2","Red1","Green2"),listOf("Red2","Blue2","Red3","Blue1"),listOf("Red2","Blue2","Red3","Blue3"),listOf("Red2","Blue2","Red3","Green1"),listOf("Red2","Blue2","Red3","Green2"),
        listOf("Red2","Blue3","Blue1","Blue2"),listOf("Red2","Blue3","Blue1","Green1"),listOf("Red2","Blue3","Blue1","Green2"),listOf("Red2","Blue3","Blue1","Red1"),listOf("Red2","Blue3","Blue1","Red3"),
        listOf("Red2","Blue3","Blue2","Blue1"),listOf("Red2","Blue3","Blue2","Green1"),listOf("Red2","Blue3","Blue2","Green2"),listOf("Red2","Blue3","Blue2","Red1"),listOf("Red2","Blue3","Blue2","Red3"),
        listOf("Red2","Blue3","Green1","Blue1"),listOf("Red2","Blue3","Green1","Blue2"),listOf("Red2","Blue3","Green1","Green2"),listOf("Red2","Blue3","Green1","Red1"),listOf("Red2","Blue3","Green1","Red3"),
        listOf("Red2","Blue3","Green2","Blue1"),listOf("Red2","Blue3","Green2","Blue2"),listOf("Red2","Blue3","Green2","Green1"),listOf("Red2","Blue3","Green2","Red1"),listOf("Red2","Blue3","Green2","Red3"),
        listOf("Red2","Blue3","Red1","Blue1"),listOf("Red2","Blue3","Red1","Blue2"),listOf("Red2","Blue3","Red1","Green1"),listOf("Red2","Blue3","Red1","Green2"),listOf("Red2","Blue3","Red3","Blue1"),
        listOf("Red2","Blue3","Red3","Blue2"),listOf("Red2","Blue3","Red3","Green1"),listOf("Red2","Blue3","Red3","Green2"),listOf("Red2","Green1","Blue1","Blue2"),listOf("Red2","Green1","Blue1","Blue3"),
        listOf("Red2","Green1","Blue1","Green2"),listOf("Red2","Green1","Blue1","Red1"),listOf("Red2","Green1","Blue1","Red3"),listOf("Red2","Green1","Blue2","Blue1"),listOf("Red2","Green1","Blue2","Blue3"),
        listOf("Red2","Green1","Blue2","Green2"),listOf("Red2","Green1","Blue2","Red1"),listOf("Red2","Green1","Blue2","Red3"),listOf("Red2","Green1","Blue3","Blue1"),listOf("Red2","Green1","Blue3","Blue2"),
        listOf("Red2","Green1","Blue3","Green2"),listOf("Red2","Green1","Blue3","Red1"),listOf("Red2","Green1","Blue3","Red3"),listOf("Red2","Green1","Green2","Blue1"),listOf("Red2","Green1","Green2","Blue2"),
        listOf("Red2","Green1","Green2","Blue3"),listOf("Red2","Green1","Green2","Red1"),listOf("Red2","Green1","Green2","Red3"),listOf("Red2","Green1","Red1","Blue1"),listOf("Red2","Green1","Red1","Blue2"),
        listOf("Red2","Green1","Red1","Blue3"),listOf("Red2","Green1","Red1","Green2"),listOf("Red2","Green1","Red1","Red3"),listOf("Red2","Green1","Red3","Blue1"),listOf("Red2","Green1","Red3","Blue2"),
        listOf("Red2","Green1","Red3","Blue3"),listOf("Red2","Green1","Red3","Green2"),listOf("Red2","Green1","Red3","Red1"),listOf("Red2","Green2","Blue1","Blue2"),listOf("Red2","Green2","Blue1","Blue3"),
        listOf("Red2","Green2","Blue1","Green1"),listOf("Red2","Green2","Blue1","Red1"),listOf("Red2","Green2","Blue1","Red3"),listOf("Red2","Green2","Blue2","Blue1"),listOf("Red2","Green2","Blue2","Blue3"),
        listOf("Red2","Green2","Blue2","Green1"),listOf("Red2","Green2","Blue2","Red1"),listOf("Red2","Green2","Blue2","Red3"),listOf("Red2","Green2","Blue3","Blue1"),listOf("Red2","Green2","Blue3","Blue2"),
        listOf("Red2","Green2","Blue3","Green1"),listOf("Red2","Green2","Blue3","Red1"),listOf("Red2","Green2","Blue3","Red3"),listOf("Red2","Green2","Green1","Blue1"),listOf("Red2","Green2","Green1","Blue2"),
        listOf("Red2","Green2","Green1","Blue3"),listOf("Red2","Green2","Green1","Red1"),listOf("Red2","Green2","Green1","Red3"),listOf("Red2","Green2","Red1","Blue1"),listOf("Red2","Green2","Red1","Blue2"),
        listOf("Red2","Green2","Red1","Blue3"),listOf("Red2","Green2","Red1","Green1"),listOf("Red2","Green2","Red1","Red3"),listOf("Red2","Green2","Red3","Blue1"),listOf("Red2","Green2","Red3","Blue2"),
        listOf("Red2","Green2","Red3","Blue3"),listOf("Red2","Green2","Red3","Green1"),listOf("Red2","Green2","Red3","Red1"),listOf("Red2","Red1","Blue1","Blue2"),listOf("Red2","Red1","Blue1","Blue3"),
        listOf("Red2","Red1","Blue1","Green1"),listOf("Red2","Red1","Blue1","Green2"),listOf("Red2","Red1","Blue2","Blue1"),listOf("Red2","Red1","Blue2","Blue3"),listOf("Red2","Red1","Blue2","Green1"),
        listOf("Red2","Red1","Blue2","Green2"),listOf("Red2","Red1","Blue3","Blue1"),listOf("Red2","Red1","Blue3","Blue2"),listOf("Red2","Red1","Blue3","Green1"),listOf("Red2","Red1","Blue3","Green2"),
        listOf("Red2","Red1","Green1","Blue1"),listOf("Red2","Red1","Green1","Blue2"),listOf("Red2","Red1","Green1","Blue3"),listOf("Red2","Red1","Green1","Green2"),listOf("Red2","Red1","Green1","Red3"),
        listOf("Red2","Red1","Green2","Blue1"),listOf("Red2","Red1","Green2","Blue2"),listOf("Red2","Red1","Green2","Blue3"),listOf("Red2","Red1","Green2","Green1"),listOf("Red2","Red1","Green2","Red3"),
        listOf("Red2","Red1","Red3","Green1"),listOf("Red2","Red1","Red3","Green2"),listOf("Red2","Red3","Blue1","Blue2"),listOf("Red2","Red3","Blue1","Blue3"),listOf("Red2","Red3","Blue1","Green1"),
        listOf("Red2","Red3","Blue1","Green2"),listOf("Red2","Red3","Blue2","Blue1"),listOf("Red2","Red3","Blue2","Blue3"),listOf("Red2","Red3","Blue2","Green1"),listOf("Red2","Red3","Blue2","Green2"),
        listOf("Red2","Red3","Blue3","Blue1"),listOf("Red2","Red3","Blue3","Blue2"),listOf("Red2","Red3","Blue3","Green1"),listOf("Red2","Red3","Blue3","Green2"),listOf("Red2","Red3","Green1","Blue1"),
        listOf("Red2","Red3","Green1","Blue2"),listOf("Red2","Red3","Green1","Blue3"),listOf("Red2","Red3","Green1","Green2"),listOf("Red2","Red3","Green1","Red1"),listOf("Red2","Red3","Green2","Blue1"),
        listOf("Red2","Red3","Green2","Blue2"),listOf("Red2","Red3","Green2","Blue3"),listOf("Red2","Red3","Green2","Green1"),listOf("Red2","Red3","Green2","Red1"),listOf("Red2","Red3","Red1","Green1"),
        listOf("Red2","Red3","Red1","Green2"),listOf("Red3","Blue1","Blue2","Blue3"),listOf("Red3","Blue1","Blue2","Green1"),listOf("Red3","Blue1","Blue2","Green2"),listOf("Red3","Blue1","Blue2","Red1"),
        listOf("Red3","Blue1","Blue2","Red2"),listOf("Red3","Blue1","Blue3","Blue2"),listOf("Red3","Blue1","Blue3","Green1"),listOf("Red3","Blue1","Blue3","Green2"),listOf("Red3","Blue1","Blue3","Red1"),
        listOf("Red3","Blue1","Blue3","Red2"),listOf("Red3","Blue1","Green1","Blue2"),listOf("Red3","Blue1","Green1","Blue3"),listOf("Red3","Blue1","Green1","Green2"),listOf("Red3","Blue1","Green1","Red1"),
        listOf("Red3","Blue1","Green1","Red2"),listOf("Red3","Blue1","Green2","Blue2"),listOf("Red3","Blue1","Green2","Blue3"),listOf("Red3","Blue1","Green2","Green1"),listOf("Red3","Blue1","Green2","Red1"),
        listOf("Red3","Blue1","Green2","Red2"),listOf("Red3","Blue1","Red1","Blue2"),listOf("Red3","Blue1","Red1","Blue3"),listOf("Red3","Blue1","Red1","Green1"),listOf("Red3","Blue1","Red1","Green2"),
        listOf("Red3","Blue1","Red2","Blue2"),listOf("Red3","Blue1","Red2","Blue3"),listOf("Red3","Blue1","Red2","Green1"),listOf("Red3","Blue1","Red2","Green2"),listOf("Red3","Blue2","Blue1","Blue3"),
        listOf("Red3","Blue2","Blue1","Green1"),listOf("Red3","Blue2","Blue1","Green2"),listOf("Red3","Blue2","Blue1","Red1"),listOf("Red3","Blue2","Blue1","Red2"),listOf("Red3","Blue2","Blue3","Blue1"),
        listOf("Red3","Blue2","Blue3","Green1"),listOf("Red3","Blue2","Blue3","Green2"),listOf("Red3","Blue2","Blue3","Red1"),listOf("Red3","Blue2","Blue3","Red2"),listOf("Red3","Blue2","Green1","Blue1"),
        listOf("Red3","Blue2","Green1","Blue3"),listOf("Red3","Blue2","Green1","Green2"),listOf("Red3","Blue2","Green1","Red1"),listOf("Red3","Blue2","Green1","Red2"),listOf("Red3","Blue2","Green2","Blue1"),
        listOf("Red3","Blue2","Green2","Blue3"),listOf("Red3","Blue2","Green2","Green1"),listOf("Red3","Blue2","Green2","Red1"),listOf("Red3","Blue2","Green2","Red2"),listOf("Red3","Blue2","Red1","Blue1"),
        listOf("Red3","Blue2","Red1","Blue3"),listOf("Red3","Blue2","Red1","Green1"),listOf("Red3","Blue2","Red1","Green2"),listOf("Red3","Blue2","Red2","Blue1"),listOf("Red3","Blue2","Red2","Blue3"),
        listOf("Red3","Blue2","Red2","Green1"),listOf("Red3","Blue2","Red2","Green2"),listOf("Red3","Blue3","Blue1","Blue2"),listOf("Red3","Blue3","Blue1","Green1"),listOf("Red3","Blue3","Blue1","Green2"),
        listOf("Red3","Blue3","Blue1","Red1"),listOf("Red3","Blue3","Blue1","Red2"),listOf("Red3","Blue3","Blue2","Blue1"),listOf("Red3","Blue3","Blue2","Green1"),listOf("Red3","Blue3","Blue2","Green2"),
        listOf("Red3","Blue3","Blue2","Red1"),listOf("Red3","Blue3","Blue2","Red2"),listOf("Red3","Blue3","Green1","Blue1"),listOf("Red3","Blue3","Green1","Blue2"),listOf("Red3","Blue3","Green1","Green2"),
        listOf("Red3","Blue3","Green1","Red1"),listOf("Red3","Blue3","Green1","Red2"),listOf("Red3","Blue3","Green2","Blue1"),listOf("Red3","Blue3","Green2","Blue2"),listOf("Red3","Blue3","Green2","Green1"),
        listOf("Red3","Blue3","Green2","Red1"),listOf("Red3","Blue3","Green2","Red2"),listOf("Red3","Blue3","Red1","Blue1"),listOf("Red3","Blue3","Red1","Blue2"),listOf("Red3","Blue3","Red1","Green1"),
        listOf("Red3","Blue3","Red1","Green2"),listOf("Red3","Blue3","Red2","Blue1"),listOf("Red3","Blue3","Red2","Blue2"),listOf("Red3","Blue3","Red2","Green1"),listOf("Red3","Blue3","Red2","Green2"),
        listOf("Red3","Green1","Blue1","Blue2"),listOf("Red3","Green1","Blue1","Blue3"),listOf("Red3","Green1","Blue1","Green2"),listOf("Red3","Green1","Blue1","Red1"),listOf("Red3","Green1","Blue1","Red2"),
        listOf("Red3","Green1","Blue2","Blue1"),listOf("Red3","Green1","Blue2","Blue3"),listOf("Red3","Green1","Blue2","Green2"),listOf("Red3","Green1","Blue2","Red1"),listOf("Red3","Green1","Blue2","Red2"),
        listOf("Red3","Green1","Blue3","Blue1"),listOf("Red3","Green1","Blue3","Blue2"),listOf("Red3","Green1","Blue3","Green2"),listOf("Red3","Green1","Blue3","Red1"),listOf("Red3","Green1","Blue3","Red2"),
        listOf("Red3","Green1","Green2","Blue1"),listOf("Red3","Green1","Green2","Blue2"),listOf("Red3","Green1","Green2","Blue3"),listOf("Red3","Green1","Green2","Red1"),listOf("Red3","Green1","Green2","Red2"),
        listOf("Red3","Green1","Red1","Blue1"),listOf("Red3","Green1","Red1","Blue2"),listOf("Red3","Green1","Red1","Blue3"),listOf("Red3","Green1","Red1","Green2"),listOf("Red3","Green1","Red1","Red2"),
        listOf("Red3","Green1","Red2","Blue1"),listOf("Red3","Green1","Red2","Blue2"),listOf("Red3","Green1","Red2","Blue3"),listOf("Red3","Green1","Red2","Green2"),listOf("Red3","Green1","Red2","Red1"),
        listOf("Red3","Green2","Blue1","Blue2"),listOf("Red3","Green2","Blue1","Blue3"),listOf("Red3","Green2","Blue1","Green1"),listOf("Red3","Green2","Blue1","Red1"),listOf("Red3","Green2","Blue1","Red2"),
        listOf("Red3","Green2","Blue2","Blue1"),listOf("Red3","Green2","Blue2","Blue3"),listOf("Red3","Green2","Blue2","Green1"),listOf("Red3","Green2","Blue2","Red1"),listOf("Red3","Green2","Blue2","Red2"),
        listOf("Red3","Green2","Blue3","Blue1"),listOf("Red3","Green2","Blue3","Blue2"),listOf("Red3","Green2","Blue3","Green1"),listOf("Red3","Green2","Blue3","Red1"),listOf("Red3","Green2","Blue3","Red2"),
        listOf("Red3","Green2","Green1","Blue1"),listOf("Red3","Green2","Green1","Blue2"),listOf("Red3","Green2","Green1","Blue3"),listOf("Red3","Green2","Green1","Red1"),listOf("Red3","Green2","Green1","Red2"),
        listOf("Red3","Green2","Red1","Blue1"),listOf("Red3","Green2","Red1","Blue2"),listOf("Red3","Green2","Red1","Blue3"),listOf("Red3","Green2","Red1","Green1"),listOf("Red3","Green2","Red1","Red2"),
        listOf("Red3","Green2","Red2","Blue1"),listOf("Red3","Green2","Red2","Blue2"),listOf("Red3","Green2","Red2","Blue3"),listOf("Red3","Green2","Red2","Green1"),listOf("Red3","Green2","Red2","Red1"),
        listOf("Red3","Red1","Blue1","Blue2"),listOf("Red3","Red1","Blue1","Blue3"),listOf("Red3","Red1","Blue1","Green1"),listOf("Red3","Red1","Blue1","Green2"),listOf("Red3","Red1","Blue2","Blue1"),
        listOf("Red3","Red1","Blue2","Blue3"),listOf("Red3","Red1","Blue2","Green1"),listOf("Red3","Red1","Blue2","Green2"),listOf("Red3","Red1","Blue3","Blue1"),listOf("Red3","Red1","Blue3","Blue2"),
        listOf("Red3","Red1","Blue3","Green1"),listOf("Red3","Red1","Blue3","Green2"),listOf("Red3","Red1","Green1","Blue1"),listOf("Red3","Red1","Green1","Blue2"),listOf("Red3","Red1","Green1","Blue3"),
        listOf("Red3","Red1","Green1","Green2"),listOf("Red3","Red1","Green1","Red2"),listOf("Red3","Red1","Green2","Blue1"),listOf("Red3","Red1","Green2","Blue2"),listOf("Red3","Red1","Green2","Blue3"),
        listOf("Red3","Red1","Green2","Green1"),listOf("Red3","Red1","Green2","Red2"),listOf("Red3","Red1","Red2","Green1"),listOf("Red3","Red1","Red2","Green2"),listOf("Red3","Red2","Blue1","Blue2"),
        listOf("Red3","Red2","Blue1","Blue3"),listOf("Red3","Red2","Blue1","Green1"),listOf("Red3","Red2","Blue1","Green2"),listOf("Red3","Red2","Blue2","Blue1"),listOf("Red3","Red2","Blue2","Blue3"),
        listOf("Red3","Red2","Blue2","Green1"),listOf("Red3","Red2","Blue2","Green2"),listOf("Red3","Red2","Blue3","Blue1"),listOf("Red3","Red2","Blue3","Blue2"),listOf("Red3","Red2","Blue3","Green1"),
        listOf("Red3","Red2","Blue3","Green2"),listOf("Red3","Red2","Green1","Blue1"),listOf("Red3","Red2","Green1","Blue2"),listOf("Red3","Red2","Green1","Blue3"),listOf("Red3","Red2","Green1","Green2"),
        listOf("Red3","Red2","Green1","Red1"),listOf("Red3","Red2","Green2","Blue1"),listOf("Red3","Red2","Green2","Blue2"),listOf("Red3","Red2","Green2","Blue3"),listOf("Red3","Red2","Green2","Green1"),
        listOf("Red3","Red2","Green2","Red1"),listOf("Red3","Red2","Red1","Green1"),listOf("Red3","Red2","Red1","Green2"),
    )
}