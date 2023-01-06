package 헤드파스트

import kotlin.concurrent.timer

/*
 194p
 유스케이스
 */
class DogDoor(private var open: Boolean = false){
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
    fun recognize(bark: String){
        println("BarkRecognizer: Heard a $bark")
        door.open()
    }
}

//개짖는소리
class Bark(private val sound: String){
    fun getSound() = sound
    fun equalsBark(bark: Any) = if(bark is Bark) bark.sound  == this.sound else false
}

fun main() {
    val door = DogDoor()
    val recognizer = BarkRecognizer(door)
    val remote = Remote(door)
    println("Fido starts barking")
    recognizer.recognize("Woof")
//    remote.pressButton()
    println("2")
    println("3")
    println("4")
    println("5")
    println("6")
}