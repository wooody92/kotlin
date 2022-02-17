package com.example.demo.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DemoService {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * proxy issue로 '@Async' 메서드는 private 메서드에서는 동작하지 않는다.
     */
    @Async
    fun onAsync() {
        try {
            Thread.sleep(3000)
            log.info("onAsync service thread : ${Thread.currentThread()}")
        } catch (e: Exception) {
            log.error("$e")
        }
    }

    fun onSync() {
        try {
            Thread.sleep(3000)
            log.info("onSync service thread : ${Thread.currentThread()}")
        } catch (e: Exception) {
            log.error("$e")
        }
    }

    /**
     * 동일 클래스 레벨에서 '@Async' 메서드 호출 시 동작하지 않는다.
     */
    fun selfInvocation() {
        try {
            onAsync() // run on main thread
            Thread.sleep(3000)
            log.info("selfInvocation service thread : ${Thread.currentThread()}")
        } catch (e: Exception) {
            log.error("$e")
        }
    }
}
