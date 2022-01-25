package coroutine.cancellation

import coroutine.log
import kotlinx.coroutines.*

fun main() {
//    cancel0201()
    cancel0202()
}

/**
 * 자원 반납은 finally 블록을 통해 사용 가능하다.
 *
 * working thread : Thread[main,5,main] / log : job: sleeping 0
 * working thread : Thread[main,5,main] / log : job: sleeping 1
 * working thread : Thread[main,5,main] / log : job: sleeping 2
 * working thread : Thread[main,5,main] / log : main: waiting!
 * working thread : Thread[main,5,main] / log : close resource here
 * working thread : Thread[main,5,main] / log : main: canceled
 */
fun cancel0201() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                log("job: sleeping $i")
                delay(500L)
            }
        } finally {
            // 여기에서 코루틴에 연결 된 connection 등의 resource 해제
            log("close resource here")
        }
    }
    delay(1300L)
    log("main: waiting!")
    job.cancelAndJoin()
    log("main: canceled")
}

/**
 * 희귀한 케이스이지만 종료 시점의 코루틴에서 새롭게 코루틴을 생성할 수 있다.
 */
fun cancel0202() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                log("job: sleeping $i")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                log("close resource here")
                delay(1000L)
                log("job: new coroutine")
            }
        }
    }
    delay(1300L)
    log("main: waiting!")
    job.cancelAndJoin()
    log("main: canceled")
}
