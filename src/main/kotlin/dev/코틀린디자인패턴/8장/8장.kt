package org.example.dev.코틀린디자인패턴.`8장`

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


fun main() {
    val pool = Executors.newFixedThreadPool(100)

    val counter = AtomicInteger(0)
    val start = System.currentTimeMillis()
    for (i in 1..10_000){
        pool.submit{
            counter.incrementAndGet()

            Thread.sleep(100)

            counter.incrementAndGet()
        }
    }

    pool.awaitTermination(20, TimeUnit.SECONDS)
    pool.shutdown()
    println("${System.currentTimeMillis() - start}밀리초 동안 ${counter.get() / 2}의 작업을 완료함")
}