package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * subject : suspend function
 */
fun main() {
    sample0201()
}

/**
 * working thread : Thread[main,5,main] / log : hello
 * working thread : Thread[main,5,main] / log : world
 */
fun sample0201() = runBlocking {
    launch { printWorld() }
    log("hello")
}

private suspend fun printWorld() {
    delay(1000L)
    log("world")
}
