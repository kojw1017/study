package 헤드파스트

import kotlin.concurrent.timer

/*
 194p
 */
class DogDoor(private val allowedBark: MutableList<Bark> = mutableListOf(), private var open: Boolean = false){
    fun open(){
        println("The dog door open")
        open = true
        //설계에따라 문이 스스로 닫히게 한다
        timer(period = 1000, initialDelay = 4000){
            close()
            cancel()
        }
    }
    fun close(){
        println("The dog door close")
        open = false
    }
    fun isOpen() = open
    fun addAllowedBark(bark: Bark){ allowedBark += bark }
    fun getAllowedBark() = allowedBark
}

//버튼 눌렀을때
class Remote(private var door: DogDoor){
    fun pressButton(){
        println("Pressing the remote control button..")
        if(door.isOpen()) door.close() else door.open()
    }
}

//개가 짖었을때
class BarkRecognizer(private val door: DogDoor){
    fun recognize(bark: Bark){
        println("BarkRecognizer: Heard a ${bark.getSound()}")
        if(door.getAllowedBark().any { it.equalsBark(bark) }) door.open()
        else println("This dog is not allowed")
    }
}

//개짖는소리
class Bark(private val sound: String){
    fun getSound() = sound
    fun equalsBark(bark: Bark) = bark.sound  == this.sound
}

fun main() {
    val door = DogDoor()
    door.addAllowedBark(Bark("Woof"))
    door.addAllowedBark(Bark("Woof1"))
    door.addAllowedBark(Bark("Woof2"))
    door.addAllowedBark(Bark("Woof3"))
    door.addAllowedBark(Bark("Woof4"))
    val recognizer = BarkRecognizer(door)
    val remote = Remote(door)
    println("Bruce start")
    recognizer.recognize(Bark("Woof1"))
    println("go outside")
    try { Thread.sleep(10000)
    } catch (e: InterruptedException){}
    val smallBark = Bark("yip")
    println("small dog start barking")
    recognizer.recognize(smallBark)
    try { Thread.sleep(10000)
    } catch (e: InterruptedException){}
    println("Bruce start")
    recognizer.recognize(Bark("Woof3"))
}