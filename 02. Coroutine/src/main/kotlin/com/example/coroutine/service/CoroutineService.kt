package com.example.coroutine.service

import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CoroutineService {

    fun log(message: String) =
        println("time : ${LocalDateTime.now()} / working thread : ${Thread.currentThread()} / log : ${message}")

    fun run(): String {
        log("start main()")

        launch()
//        async()
//        yield()

        log("end main()")
        return "pong"
    }

    fun launch() {
//        runBlocking {
//            launch {
//                sleep1()
//                sleep2()
//            }
//        }
        GlobalScope.launch { thread1() }
        GlobalScope.launch(Dispatchers.Unconfined) { thread1() }
        GlobalScope.launch(Dispatchers.Default) { thread1() }
        GlobalScope.launch(newSingleThreadContext("myThread")) { thread1() }
    }

    fun async() {
        runBlocking {
            val d1 = GlobalScope.async { delay(1000); 1 }
            log("after async delay 1")
            val d2 = GlobalScope.async { delay(2000); 2 }
            log("after async delay 2")
            val d3 = GlobalScope.async { delay(3000); 3 }
            log("after async delay 3")

            log("total : ${d1.await() + d2.await() + d3.await()}")
        }
    }

    fun yield() {
        GlobalScope.launch { yield1() }
        GlobalScope.launch { yield2() }
    }


    private suspend fun sleep1() {
        log("start sleep1()")
        Thread.sleep(2000)
        log("end sleep1()")
    }

    private suspend fun sleep2() {
        log("start sleep2()")
        Thread.sleep(1000)
        log("end sleep2()")
    }

    private suspend fun thread1() {
        log("start thread1()")
    }

    private suspend fun yield1() {
        log("1")
        yield()
        log("3")
        yield()
        log("5")
    }

    private suspend fun yield2() {
        log("2")
        yield()
        log("4")
        yield()
        log("6")
    }
}