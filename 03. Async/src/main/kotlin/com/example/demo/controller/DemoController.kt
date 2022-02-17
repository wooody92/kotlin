package com.example.demo.controller

import com.example.demo.service.DemoService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(
    private val demoService: DemoService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/async")
    fun callAsync(): String {
        demoService.onAsync()
        log.info("callAsync controller thread : ${Thread.currentThread()}")
        return "async"
    }

    @GetMapping("/sync")
    fun callSync(): String {
        demoService.onSync()
        log.info("callSync controller thread : ${Thread.currentThread()}")
        return "sync"
    }

    @GetMapping("/self")
    fun callSelfInvocation(): String {
        demoService.selfInvocation()
        log.info("callSelfInvocation controller thread : ${Thread.currentThread()}")
        return "selfInvocation"
    }
}
