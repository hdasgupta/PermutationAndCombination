package org.combination.rule.combination

import kotlinx.coroutines.yield
import kotlin.jvm.Throws
import kotlin.math.max

class Rule {
    val criteria: Criteria
    val string: String
    val minCount: Int
    val maxCount: Int
    private val matchString: String
    private val toString: String
    private val range: IntRange

    @Throws(Throwable::class)
    constructor(criteria: Criteria, string: String, minCount: Int = 0, maxCount: Int = Int.MAX_VALUE) {

        this.criteria = criteria
        this.string = string
        this.minCount = minCount
        this.maxCount = maxCount
        this.range = minCount..maxCount
        matchString = "${criteria.text} \'$string\'"
        toString = "${if(minCount==0) "" else "minimum $minCount"} " +
                "${if(minCount>0 && maxCount!=Int.MAX_VALUE) "and " else ""}" +
                "${if(maxCount==Int.MAX_VALUE) "" else "maximum $maxCount"}" +
                " of words $matchString"
    }

    fun filter(words: List<String>): List<String> =
        words.filter { match(it) }

    fun validate(words: List<String>): Boolean =
        filter(words).size in range

    fun errors(words: List<String>): List<String> = filter(words).let {
        val errors = mutableListOf<String>()
        if (maxCount < minCount)
            errors.add("Maximum count(${if(maxCount==Int.MAX_VALUE) "Any" else maxCount}) can not be lesser than Minimum count($minCount) for $matchString")
        if (it.size < minCount) errors.add("Minimum count($minCount) of $string in [${words.joinToString(", ")}] can not be greater than matching words ($matchString) count")
        if (it.size < maxCount && maxCount != Int.MAX_VALUE) errors.add(
            "Maximum count(${if(maxCount==Int.MAX_VALUE) "Any" else maxCount}) of $string in [${
                words.joinToString(
                    ", "
                )
            }] can not be Lesser than matching words ($matchString) count"
        )
        errors
    }

    fun match(word: String): Boolean =
        when (criteria) {
            Criteria.Contains -> word.contains(string)
            Criteria.StartsWith -> word.startsWith(string)
            else -> word.endsWith(string)
        }

    override fun toString(): String = toString

}