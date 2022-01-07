package coroutine.basic

import coroutine.log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
 * subject : yield
 */
fun main() {
    sample0501()
}

/**
 * working thread : Thread[main @coroutine#1,5,main] / log : coroutine C
 * working thread : Thread[main @coroutine#2,5,main] / log : coroutine A 0
 * working thread : Thread[main @coroutine#3,5,main] / log : coroutine B 0
 * working thread : Thread[main @coroutine#2,5,main] / log : coroutine A 1
 * working thread : Thread[main @coroutine#3,5,main] / log : coroutine B 1
 * working thread : Thread[main @coroutine#2,5,main] / log : coroutine A 2
 * working thread : Thread[main @coroutine#3,5,main] / log : coroutine B 2
 * working thread : Thread[main @coroutine#2,5,main] / log : coroutine A 3
 * working thread : Thread[main @coroutine#3,5,main] / log : coroutine B 3
 * working thread : Thread[main @coroutine#2,5,main] / log : coroutine A 4
 * working thread : Thread[main @coroutine#3,5,main] / log : coroutine B 4
 */
fun sample0501() = runBlocking {
    launch {
        repeat(5) { i ->
            log("coroutine A $i")
            yield()
        }
    }
    launch {
        repeat(5) { i ->
            log("coroutine B $i")
            yield()
        }
    }
    log("coroutine C")
}