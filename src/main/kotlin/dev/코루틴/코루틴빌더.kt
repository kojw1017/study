package org.example.dev.코루틴

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

//fun main(){
//   GlobalScope.launch {
//      delay(1000)
//      println("world")
//   }
//   GlobalScope.launch {
//      delay(1000)
//      println("world")
//   }
//   GlobalScope.launch {
//      delay(1000)
//      println("world")
//   }
//   GlobalScope.launch {
//      delay(1000)
//      println("world")
//   }
//   println("Hello")
//   Thread.sleep(2000L)
//}

//fun main(){
//   thread(isDaemon = true){
//      Thread.sleep(1000L)
//      println("world")
//   }
//   thread(isDaemon = true){
//      Thread.sleep(1000L)
//      println("world")
//   }
//   thread(isDaemon = true){
//      Thread.sleep(1000L)
//      println("world")
//   }
//   println("Hello")
//   Thread.sleep(2000L)
//}

fun main(){
   runBlocking {
      delay(1000L)
      println("a")
   }
   runBlocking {
      delay(1000L)
      println("a")
   }
   runBlocking {
      delay(1000L)
      println("a")
   }
   println("b")
}