package design

abstract class DisplayImpl{
    abstract fun rawOpen()
    abstract fun rawPrint()
    abstract fun rawClose()
}

class StringDisplayImpl(private var string: String, private var width: Int = string.toByteArray().size): DisplayImpl(){
    private fun printLine(){
        print("+")
        for (i in 0 until width){ print("-") }
        println("+")
    }

    override fun rawOpen() { printLine() }
    override fun rawPrint() { println("|$string|") }
    override fun rawClose() { printLine() }
}
open class Display(var impl: DisplayImpl){
    fun open(){ impl.rawOpen() }
    fun print(){ impl.rawPrint() }
    fun close(){ impl.rawClose() }
    fun display(){
        open()
        print()
        close()
    }
}

class CountDisplay(impl: DisplayImpl): Display(impl){
    fun multiDisplay(times: Int){
        open()
        for (i in 0 until times){
            print()
        }
        close()
    }
}

fun main(){
    val d1 = Display(StringDisplayImpl("hello korea"))
    val d2 = CountDisplay(StringDisplayImpl("hello world"))
    val d3 = CountDisplay(StringDisplayImpl("hello universe"))

    d1.display()
    d2.display()
    d3.display()
    d3.multiDisplay(5)
}