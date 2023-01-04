package Adapter

interface Print{
    fun printWeak()
    fun printStrong()
}

open class Banner(private var str: String){
    fun showWithParen() = println("($str)")
    fun showWithAster() = println("*$str*")
}

class PrintBanner(str: String): Banner(str), Print{
    override fun printWeak() = showWithParen()
    override fun printStrong()= showWithAster()
}

fun main(){
    val p: Print = PrintBanner("Hello")
    p.printWeak()
    p.printStrong()
}