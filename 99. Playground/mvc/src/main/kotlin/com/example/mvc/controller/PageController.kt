package com.example.mvc.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PageController {

    /**
     * CONTROLLER - 1
     *
     * resource/static 내부 page를 view resolver가 처리
     */
    @GetMapping("/main")
    fun main(): String {
        println("init main")
        println()
        return "main.html"
    }

    /**
     * CONTROLLER - 2
     *
     * controller에서 object를 응답해야하는 경우
우    */
    @ResponseBody
    @GetMapping("/main2")
    fun response(): String {
        return "main.html"
    }
}