package com.example.demo.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class DemoService {

//    private val log = LoggerFactory.getLogger(this::class)

    /**
     * proxy issue로 '@Async' 메서드는 private 메서드에서는 동작하지 않는다.
     */
    @Async
    fun onAsync() {
        try {
            Thread.sleep(3000)
            // print
        } catch (e: Exception) {
            // println("$e"))
        }
    }

    fun onSync() {
        try {
            Thread.sleep(3000)
            // print
        } catch (e: Exception) {
            // println("$e"))
        }
    }
}
