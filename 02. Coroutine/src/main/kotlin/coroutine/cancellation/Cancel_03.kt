package coroutine.cancellation

import coroutine.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
//    cancel0301()
    cancel0302()
}

/**
 * launch 메서드를 사용하지 않고 단순 runBlocking에서 실행하여 예외 발생
 *
 * working thread : Thread[main,5,main] / log : sleeping 0
 * working thread : Thread[main,5,main] / log : sleeping 1
 * working thread : Thread[main,5,main] / log : sleeping 2
 * Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 1300 ms
 *   at kotlinx.coroutines.TimeoutKt.TimeoutCancellationException(Timeout.kt:186)
 */
fun cancel0301() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            log("sleeping $i")
            delay(500L)
        }
    }
}

/**
 * 예외 대신 null 처리 할 수 있다.
 *
 * working thread : Thread[main,5,main] / log : sleeping 0
 * working thread : Thread[main,5,main] / log : sleeping 1
 * working thread : Thread[main,5,main] / log : sleeping 2
 * working thread : Thread[main,5,main] / log : result is null
 */
fun cancel0302() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            log("sleeping $i")
            delay(500L)
        }
        "done"
    }
    log("result is $result")
}
