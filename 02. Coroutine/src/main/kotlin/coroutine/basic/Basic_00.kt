package coroutine.basic

import coroutine.log
import kotlinx.coroutines.*

fun main() {
    sample0001()
    sample0002()
//    sample0003()
//    runBlocking { sample0004() }
}

/**
 * GlobalScope : 프로세스가 종료되면 내부 코루틴이 종료되지 않았더라도 스레드 반환
 *
 * (empty)
 */
fun sample0001() {
    GlobalScope.launch {
        log("A")
        delay(1000L)
        log("B")
    }
}

/**
 * runBlocking : 내부 코루틴이 종료 될 때 까지 현재 동작중인 스레드를 block
 *
 * working thread : Thread[main @coroutine#1,5,main] / log : A
 * working thread : Thread[main @coroutine#1,5,main] / log : B
 */
fun sample0002() {
    runBlocking {
        log("A")
        delay(1000L)
        log("B")
    }
}

/**
 * launch : sample0002와 동일한 동작을 하는 것으로 보임
 *
 * working thread : Thread[main @coroutine#1,5,main] / log : A
 * working thread : Thread[main @coroutine#1,5,main] / log : B
 */
fun sample0003() {
    runBlocking {
        launch {
            log("A")
            delay(1000L)
            log("B")
        }
    }
}

/**
 * coroutineScope : 별도로 작성하지 않아도 동일하게 동작 하는 것으로 보임
 *
 * working thread : Thread[main,5,main] / log : A
 * working thread : Thread[main,5,main] / log : B
 * working thread : Thread[main,5,main] / log : C
 * working thread : Thread[main,5,main] / log : D
 */
suspend fun sample0004() {
    coroutineScope {
        log("A")
        delay(1000L)
        log("B")
    }
    log("C")
    delay(1000L)
    log("D")
}

/**
 * launch vs. runBlocking :
 * - launch : 자신을 호출 한 스레드를 blocking 하지 않음
 * - runBlocking : 자신을 호출 한 스레드를 blocking 함
 *
 * working thread : Thread[main,5,main] / log : A : 0
 * working thread : Thread[main,5,main] / log : B : 0
 * working thread : Thread[main,5,main] / log : C : 0
 * working thread : Thread[main,5,main] / log : A : 1
 * working thread : Thread[main,5,main] / log : B : 1
 * working thread : Thread[main,5,main] / log : C : 1
 * working thread : Thread[main,5,main] / log : A : 2
 * working thread : Thread[main,5,main] / log : B : 2
 * working thread : Thread[main,5,main] / log : C : 2
 * working thread : Thread[main,5,main] / log : D : 0
 * working thread : Thread[main,5,main] / log : D : 1
 * working thread : Thread[main,5,main] / log : D : 2
 */
fun sample0005() = runBlocking {
    launch {
        repeat(3) { i ->
            log("A : $i")
            delay(100)
        }
    }
    launch {
        repeat(3) { i ->
            log("B : $i")
            delay(100)
        }
    }
    runBlocking {
        repeat(3) { i ->
            log("C : $i")
            delay(100)
        }
    }
    launch {
        repeat(3) { i ->
            log("D : $i")
            delay(100)
        }
    }
}
