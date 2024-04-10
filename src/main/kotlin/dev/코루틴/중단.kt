package org.example.dev.코루틴

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
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

suspend fun a() = coroutineScope {
    println("before")
    launch {
        delay(3000)
        countinuation?.resume(Unit)
    }
    suspendAndSetContinuation()
    println("after")
}
suspend fun b() {
    suspendCoroutine<Unit> {
        thread {
            println("suspended")
            Thread.sleep(1000)
            it.resume(Unit)
            println("resume")
        }
    }
}
suspend fun c()  {
    println("before")
    suspendAndSetContinuation()
    countinuation?.resume(Unit)
    println("after")
}


suspend fun main(){
   a()
//   b()
}
