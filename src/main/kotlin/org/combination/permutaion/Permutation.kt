package org.combination.permutaion

import org.springframework.stereotype.Component
import java.util.TreeSet

@Component
class Permutation {

    val mapPermutation = mutableMapOf<Int, List<List<Int>>>()
    val mapResult = mutableMapOf<String, TreeSet<Result>>()

    fun permutation(words:List<String>):TreeSet<Result> {
        val key = words.joinToString(",")
        if(mapResult.contains(key)) return mapResult[key]!!
        val result = TreeSet(
            permutation(words.size)
            .map {
                Result(
                    it.map { i ->
                        words[i]
                    }
                )
            }
        )

        mapResult[key] = result

        return result
    }

    private fun permutation(count: Int):List<List<Int>> {
        if(mapPermutation.contains(count))  {
            return mapPermutation[count]!!
        }
        val out: MutableList<MutableList<Int>> = mutableListOf()
        permutation(count, out)
        mapPermutation[count] = out
        return out
    }

    private fun permutation(count:Int, out: MutableList<MutableList<Int>>, index:Int = 0) {
        if(index==count) return
        else if(out.isEmpty()) {
            out.add(mutableListOf(index))
        } else {
            val _out = mutableListOf<MutableList<Int>>()

            for(array in out) {
                for(idx in (0..array.size).reversed()) {
                    val _array = array.toMutableList()
                    _array.add(idx, index)
                    _out.add(_array)
                }
            }
            out.clear()
            out.addAll(_out)
        }
        permutation(count, out, index+1)
    }
}