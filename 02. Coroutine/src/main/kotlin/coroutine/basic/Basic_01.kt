package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    sample0103()
}

/**
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[DefaultDispatcher-worker-1 @coroutine#1,5,main] / log : world
 */
fun sample0101() {
    GlobalScope.launch {
        delay(1000L)
        log("world")
    }
    log("hello")
    Thread.sleep(2000L) // block main thread to JVM keep alive
}

/**
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[DefaultDispatcher-worker-1 @coroutine#1,5,main] / log : world
 */
fun sample0102() {
    GlobalScope.launch {
        delay(1000L)
        log("world")
    }
    log("hello")
    runBlocking {  // block main thread to JVM keep alive
        delay(2000L)
    }
}

/**
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main] / log : world
 */
fun sample0103() = runBlocking {
    GlobalScope.launch {
        delay(1000L)
        log("world")
    }
    log("hello")
    delay(2000L)
}

/**
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main] / log : world
 * working thread : Thread[main @coroutine#1,5,main] / log : !!
 */
fun sample0104() = runBlocking {
    val job = GlobalScope.launch {
        delay(1000L)
        log("world")
    }
    log("hello")
    job.join() // job 객체인 코루틴이 종료될때까지 대기 후 종료
    log("!!")
}

/**
 * structured concurrency
 *
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[main,5,main] / log : world
 */
fun sample0100() = runBlocking {
    launch {
        delay(1000L)
        log("world")
    }
    log("hello")
}
