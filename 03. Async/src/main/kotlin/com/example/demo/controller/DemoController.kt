package com.example.demo.controller

import com.example.demo.service.DemoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(
    private val demoService: DemoService
) {

    @GetMapping("/async")
    fun callAsync(): String {
        demoService.onAsync()
        // print
        return "async"
    }

    @GetMapping("/sync")
    fun callSync(): String {
        demoService.onSync()
        // print
        return "sync"
    }
}