package 헤드파스트

import kotlin.concurrent.timer

class DogDoor(private var open: Boolean = false){
    fun open(){
        println("The dog door open")
        open = true
    }
    fun close(){
        println("The dog door close")
        open = false
    }
    fun isOpen() = open
}

class Remote(private var door: DogDoor){
    fun pressButton(){
        println("Pressing the remote control button..")
        if(door.isOpen()) door.close() else door.open()
        timer(period = 1000, initialDelay = 5000){
            door.close()
            cancel()
        }
    }
}

fun main() {
    val door = DogDoor()
    val remote = Remote(door)
    println("1")
    remote.pressButton()
    println("2")
    println("3")
    println("4")
    println("5")
    println("6")
}