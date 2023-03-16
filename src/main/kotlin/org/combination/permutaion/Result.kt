package org.combination.permutaion

class Result(list: List<String> = listOf()):ArrayList<String>(list), Comparable<Result> {
    override fun compareTo(other: Result): Int =
        "$this".compareTo("$other")

    override fun equals(other: Any?): Boolean =
        other is Result &&
                "$this"=="$other"

    override fun toString(): String =
        joinToString(",")
}