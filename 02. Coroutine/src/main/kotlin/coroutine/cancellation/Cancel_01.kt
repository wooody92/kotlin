package coroutine.cancellation

import coroutine.log
import kotlinx.coroutines.*

fun main() {
//    cancel0101()
//    cancel0102()
    cancel0103()
}

/**
 * working thread : Thread[main,5,main] / log : job: sleeping 0
 * working thread : Thread[main,5,main] / log : job: sleeping 1
 * working thread : Thread[main,5,main] / log : job: sleeping 2
 * working thread : Thread[main,5,main] / log : main: waiting!
 * working thread : Thread[main,5,main] / log : main: canceled
 */
fun cancel0101() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            log("job: sleeping $i")
            delay(500L)
        }
    }
    delay(1300L)
    log("main: waiting!")
    job.cancel()
    job.join()
    log("main: canceled")
}

/**
 * 코루틴 취소를 위해서는 서스펜드 함수가 필요하다.
 * cancel 메서드가 실행되고 yield 메서드가 동작하면 코루틴 내부적으로 JobCancellationException 를 발생시켜 진행 중인 코루틴을 중단한다.
 *
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 0
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 1
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 2
 * working thread : Thread[main,5,main] / log : main: waiting!
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : Exception: kotlinx.coroutines.JobCancellationException: StandaloneCoroutine was cancelled; job=StandaloneCoroutine{Cancelling}@7cf30ff8
 * working thread : Thread[main,5,main] / log : main: canceled
 */
fun cancel0102() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        try {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    yield() // yield 주석 처리하면 코루틴 취소 안됨
                    log("job: sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
        } catch (e: Exception) {
            log("Exception: $e")
        }
    }
    delay(1300L)
    log("main: waiting!")
    job.cancelAndJoin()
    log("main: canceled")
}

/**
 * cancel0102 예제와 다르게 상태값(isActive)을 체크 후 취소하여 예외를 발생시키지 않는다.
 *
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : isActive : true
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 0
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 1
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : job: sleeping 2
 * working thread : Thread[main,5,main] / log : main: waiting!
 * working thread : Thread[DefaultDispatcher-worker-1,5,main] / log : isActive : false
 * working thread : Thread[main,5,main] / log : main: canceled
 */
fun cancel0103() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        try {
            var nextPrintTime = startTime
            var i = 0
            log("isActive : $isActive")
            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    log("job: sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
            log("isActive : $isActive")
        } catch (e: Exception) {
            log("Exeption: $e")
        }
    }
    delay(1300L)
    log("main: waiting!")
    job.cancelAndJoin()
    log("main: canceled")
}
