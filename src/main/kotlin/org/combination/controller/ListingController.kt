package org.combination.controller

import org.combination.permutaion.Combination
import org.combination.permutaion.Permutation
import org.combination.permutaion.ValidationErrors
import org.combination.rule.combination.Criteria
import org.combination.rule.combination.Rule
import org.combination.rule.combination.Rules
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
class ListingController {

    @Autowired
    lateinit var combination: Combination

    @Autowired
    lateinit var permutation: Permutation

    val ruleColumns = mapOf(
        Pair("criteria", fun (str:String)=Criteria.valueOf(str)),
        Pair("text", fun (str:String)=str),
        Pair("minCount", fun (str:String)=str.toInt()),
        Pair("maxCount", fun (str:String)=str.toInt().let { if(it==-1) Int.MAX_VALUE else it })
    )

    val ruleConstructor = Rule::class.constructors.toList()[0]

    @RequestMapping("/listingCombination")
    fun combination(request: HttpServletRequest, map: ModelMap): String {
        val ruleCount = request.getParameter("ruleCount").toInt()
        val _words = request.getParameter("words")
        val words = _words.split(",")
            .map { it.trim() }

        val select = request.getParameter("select").toInt()
        val ordered = request.getParameter("ordered")=="on"

        val rules = Rules(select, ordered)

        (0 until ruleCount).forEach {
            if(ruleColumns.keys.all { key-> !request.getParameter("${key}_$it").isNullOrBlank()}) {
                val rule = ruleConstructor.call(
                    *ruleColumns.entries
                        .map {
                            entry->
                            entry.value(request.getParameter("${entry.key}_$it"))
                        }
                        .toTypedArray()
                )

                rules.add(rule)
            }

        }


        try{
            map["results"] = combination.combination(words, rules).toList()

            val reqMap= mutableMapOf(*request.parameterNames.toList()
                .map {
                    Pair(
                        it,
                        request.getParameter(it)
                    )
                }.toTypedArray())

            reqMap["ruleCount"] = rules.size.toString()
            reqMap["ordered"] = ordered.toString()

            map["data"] = reqMap.toList()

            map["url"] = "/combination"

            map["words"] = _words

            map["select"] = select

            map["ordered"] = ordered

            map["rules"] = rules.map { "$it" }

            map["isCombination"] = true

            map["errors"] = listOf<String>()

            return "Listing"

        } catch (e:ValidationErrors) {
            val errors = e.validationErrors

            request.parameterNames.toList()
                .forEach {
                    map[it] = request.getParameter(it)
                }

            map["criteriaText"] =
                Criteria.values()
                    .joinToString(",") { it.name+"|"+it.text }

            map["criteria"] = Criteria.values()

            map["ruleCount"] = ruleCount
            map["ordered"] = ordered

            map["rules"] = rules

            map["errors"] = errors.map { "${it.message}" }

            return "Combination"

        }

    }

    @RequestMapping("/listingPermutation")
    fun permutation(@RequestParam words: String, request: HttpServletRequest, map: ModelMap): String {
        val _words = words.split(",")
            .map { it.trim() }


        map["data"] = request.parameterNames.toList()
            .map {
                Pair(
                    it,
                    request.getParameter(it)
                )
            }


        map["words"] = words

        map["isCombination"] = false

        map["url"] = "/permutation"

        map["results"] = permutation.permutation(_words).toList()

        return "Listing"
    }

}