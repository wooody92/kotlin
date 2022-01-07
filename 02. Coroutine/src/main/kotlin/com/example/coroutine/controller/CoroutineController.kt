package com.example.coroutine.controller

import com.example.coroutine.service.CoroutineService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CoroutineController(
    private val coroutineService: CoroutineService
) {

    @GetMapping("/ping")
    fun hello(): String {
        return coroutineService.run()
    }
}
