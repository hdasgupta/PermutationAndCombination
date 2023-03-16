package org.combination.permutaion

import org.combination.rule.combination.Rules
import org.springframework.stereotype.Component
import java.util.TreeSet
import kotlin.jvm.Throws

@Component
class Combination {

    val mapCombination = mutableMapOf<String, List<List<Int>>>()

    @Throws(ValidationErrors::class)
    fun combination(words:List<String>, rules: Rules): TreeSet<Result> {
        val errors = rules.validate(words)
        if(errors.isNotEmpty()) {
            throw ValidationErrors(errors)
        }

        val combination = combination(words.size, rules.select, rules.ordered)

        return TreeSet(
            combination
            .map {
                it.map {
                        i->words[i]
                }
            }
            .filter {
                rules.match(it)
            }
            .map {
                Result(it)
            }
        )
    }

    private fun combination(count: Int, select: Int, ordered: Boolean=false):List<List<Int>> {
        var  key = "$count,$select,$ordered"
        val out: MutableList<MutableList<Int>> = mutableListOf()
        if(mapCombination.contains(key))  {
            return mapCombination[key]!!
        }
        if(count>=select) {

            combination(count, select, ordered, out)
            mapCombination[key] = out
            return out
        } else {
            throw CountLessThanSelectException(count, select)
        }
    }

    private fun combination(count:Int, select: Int, ordered:Boolean, out: MutableList<MutableList<Int>>, selectIndex:Int=0) {
        if (selectIndex==select) return

        val _out: MutableList<MutableList<Int>> = mutableListOf()

        (0 until count).forEach {
            if(out.isEmpty()) {
                _out.add(mutableListOf(it))
            } else {
                inner@for(array in out) {
                    if(!array.contains(it)) {
                        if(ordered && array.max() > it) break@inner
                        val _array = array.toMutableList()
                        _array.add(it)
                        _out.add(_array)
                    }
                }
            }
        }

        out.clear()
        out.addAll(_out)

        combination(count, select, ordered, out, selectIndex+1)
    }
}