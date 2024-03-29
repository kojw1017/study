package org.example.dev.코루틴

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


private val executor = Executors.newSingleThreadScheduledExecutor{
    Thread(it, "scheduler").apply {
        isDaemon = true
    }
}
suspend fun delay(time:Long) = suspendCoroutine {
    executor.schedule({
        it.resume(Unit)
    }, time, TimeUnit.NANOSECONDS)
}

var countinuation: Continuation<Unit>? = null
suspend fun suspendAndSetContinuation(){
    suspendCoroutine<Unit> {
        countinuation = it
    }
}

suspend fun main() = coroutineScope {
    val job1 = launch {
        // 백그라운드 작업 1
    }
    val job2 = launch {
        // 백그라운드 작업 2
    }
    // job1과 job2가 모두 종료될 때까지 기다림
    job1.join()
    job2.join()
    // UI 업데이트
    println("before")
    //    suspendCoroutine<Unit> {
    //        thread {
    //            println("suspended")
    //            Thread.sleep(1000)
    //            it.resume(Unit)
    //            println("resume")
    //        }
    //    }
    suspendAndSetContinuation()
    countinuation?.resume(Unit)
    println("after")
}
