package org.combination.controller

import org.combination.rule.combination.Criteria
import org.combination.rule.combination.Rule
import org.combination.rule.combination.Rules
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
class CombinationController {

    val ruleColumns = mapOf(
        Pair("criteria", fun (str:String)=Criteria.valueOf(str)),
        Pair("text", fun (str:String)=str),
        Pair("minCount", fun (str:String)=str.toInt()),
        Pair("maxCount", fun (str:String)=str.toInt().let { if(it==-1) Int.MAX_VALUE else it })
    )

    val ruleConstructor = Rule::class.constructors.toList()[0]

    @RequestMapping("/combination")
    fun combination(request: HttpServletRequest, map:ModelMap):String {
        val ruleCount = request.getParameter("ruleCount").let {
            if(it.isNullOrEmpty()) 0 else it.toInt()
        }
        val _words = request.getParameter("words")
        val select = request.getParameter("select").let {
            if(it.isNullOrBlank()) 0 else it.toInt()
        }
        val ordered = request.getParameter("ordered").toBoolean()

        map["criteriaText"] =
            Criteria.values()
            .joinToString(",") { it.name+"|"+it.text }

        map["criteria"] = Criteria.values()

        map["words"] = _words
        map["select"] = select
        map["ordered"] = ordered

        map["ruleCount"] = ruleCount

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

        map["rules"] = rules

        map["errors"] = if(_words.isNullOrBlank())
            listOf()
        else
            rules.validate(_words.split(",").map { it.trim() } )

        return "Combination"
    }
}