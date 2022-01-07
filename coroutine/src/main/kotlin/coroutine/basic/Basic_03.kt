package coroutine.basic

import coroutine.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

/**
 * subject : coroutine vs. thread
 */
fun main() {
    sample0302()
}

/**
 * coroutine : light
 *
 * time : 2022-01-07T18:24:53.158353 / working thread : Thread[main @coroutine#99841,5,main] / log : .
 * time : 2022-01-07T18:24:53.158364 / working thread : Thread[main @coroutine#99842,5,main] / log : .
 * time : 2022-01-07T18:24:53.158375 / working thread : Thread[main @coroutine#99843,5,main] / log : .
 * ...
 * */
fun sample0301() = runBlocking {
    repeat(100_000) {
        launch {
            delay(1000L)
            log(".")
        }
    }
}

/**
 * thread : heavy (could be out-of-memory)
 *
 * time : 2022-01-07T18:25:39.507251 / working thread : Thread[Thread-18942,5,main] / log : .
 * time : 2022-01-07T18:25:39.508593 / working thread : Thread[Thread-18943,5,main] / log : .
 * time : 2022-01-07T18:25:39.509168 / working thread : Thread[Thread-18944,5,main] / log : .
 * ...
 */
fun sample0302() {
    repeat(100_000) {
        thread {
            Thread.sleep(1000L)
            log(".")
        }
    }
}
