package coroutine

import java.time.LocalDateTime

fun log(message: String) =
    println("time : ${LocalDateTime.now()} / working thread : ${Thread.currentThread()} / log : $message")

class Main {
}