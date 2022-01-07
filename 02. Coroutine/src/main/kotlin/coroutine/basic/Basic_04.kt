package coroutine.basic

import coroutine.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * subject : global coroutine are like daemon thread
 */
fun main() {
    sample0401()
}

/**
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : 0
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : 1
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : 2
 */
fun sample0401() = runBlocking {
    GlobalScope.launch {
        repeat(1000) { i ->
            log("$i")
            delay(500L)
        }
    }
    delay(1300L)
}
