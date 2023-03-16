package org.combination.rule.combination

import org.combination.permutaion.CountLessThanSelectException

class Rules(val select: Int, val ordered: Boolean = false):ArrayList<Rule>() {

    val minCount: Int
        get() = sumOf { it.minCount }

    val maxCount: Int
        get() {
            val noMax = any {
                it.maxCount==Int.MAX_VALUE
            }

            return if(noMax) Int.MAX_VALUE else sumOf { it.maxCount }
        }

    fun validate(words:List<String>):List<String> {
        val errors:MutableList<String> = mutableListOf()
        if(words.size<select)
            errors.add(CountLessThanSelectException(words.size, select).message!!)

        if(isNotEmpty() && select !in minCount..maxCount) {
            errors.add("value of select($select) must be between $minCount(inclusive) and ${if(maxCount==Int.MAX_VALUE) "Any" else maxCount}(inclusive)")
        }

        errors.addAll(
            flatMap {
                it.errors(words)
            }
        )

        return errors
    }

    fun match(words:List<String>):Boolean = isEmpty() || any { it.validate(words) }
}