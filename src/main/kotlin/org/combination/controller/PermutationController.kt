package org.combination.controller

import org.combination.rule.combination.Criteria
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
class PermutationController {

    @RequestMapping("/permutation")
    fun combination(request: HttpServletRequest, map:ModelMap):String {
        map["words"] = request.getParameter("words")

        return "Permutation"
    }
}