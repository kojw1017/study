package org.example.dev.코루틴

import kotlinx.coroutines.*

suspend fun main() = coroutineScope {
    val job = launch {
        repeat(10) { i ->
            delay(100)
            Thread.sleep(100)
            println(i)
        }
    }

    delay(1000)
    job.cancel()
    println("Cancelled")
}