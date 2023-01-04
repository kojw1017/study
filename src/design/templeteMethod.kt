package design

abstract class AbstractDisplay(){
    abstract fun open()
    abstract fun print()
    abstract fun close()
    fun display(){
        open()
        for (i in 1..5){
            print()
        }
        close()
    }
}

class CharDisplay(private val ch: String): AbstractDisplay(){
    override fun open() = print("<<")
    override fun print() =print(ch)
    override fun close() = println(">>")
}

class StringDisplay(private val string: String): AbstractDisplay(){
    private var width = string.length.toByte()
    override fun open() = printLine()
    override fun print() = println("| $string |")
    override fun close() = printLine()
    fun printLine(){
        print("+")
        for (i in 0..width){
            print("-")
        }
        println("+")
    }
}

fun main(){
    val d1:AbstractDisplay = CharDisplay("H")
    val d2:AbstractDisplay = StringDisplay("Hello World")
    val d3:AbstractDisplay = StringDisplay("안뇽하세요")
    d1.display()
    d2.display()
    d3.display()
}
