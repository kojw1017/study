package design

import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

enum class Number(str: String, value: Int){
    ONE("one", 1), TWO("two", 2),
}

fun str2Num(str: String){
    val stringBuffer: StringBuffer = StringBuffer()
    for (i in str.indices){
        stringBuffer.append(str[i])
    }
    println(stringBuffer)
}

abstract class Builder{
    abstract fun makeTitle(title: String)
    abstract fun makeString(str: String)
    abstract fun makeItems(items: MutableList<String>)
    abstract fun close()
}

class Director(private val builder: Builder){
    fun construct(){
        builder.makeTitle("Greeting")
        builder.makeString("아침과 낮에")
        builder.makeItems(mutableListOf("좋은 아침입니다.", "안녕하세요."))

        builder.makeString("밤에")
        builder.makeItems(mutableListOf("안녕하세요.", "안녕히 주무세요.", "안녕히 계세요"))
        builder.close()
    }
}
class TextBuilder: Builder() {
    private val buffer = StringBuffer()
    override fun makeTitle(title: String) {
        buffer.append("===============================")
        buffer.append("== $title ==")
        buffer.append("\n")
    }
    override fun makeString(str: String) {
        buffer.append("<< $str >>")
        buffer.append("\n")
    }
    override fun makeItems(items: MutableList<String>) {
        for (i in 0..items.size) {
            buffer.append("|| ${items[i]}")
            buffer.append("\n")
        }
    }
    override fun close() {
        buffer.append("===============================")
    }
    fun getResult() = buffer.toString()
}

class HTMLBuilder(): Builder(){
    private var writer: PrintWriter? = null
    private var fileName: String? = null
    override fun makeTitle(title: String) {
        fileName = "$title.html"
        try {
            writer = PrintWriter(FileWriter(fileName))
        }catch(e: IOException){
            e.printStackTrace()
        }
        writer?.println("<html><head><title> $title </title></head><body>")
        writer?.println("<h1> $title </h1>")
    }

    override fun makeString(str: String) {
        writer?.println("<p> $str </p>")
    }

    override fun makeItems(items: MutableList<String>) {
        writer?.println("<ul>")
        for (i in 1 until  items.size){
            writer?.println("<li> ${items[i]} </li>")
        }
        writer?.println("</ul>")
    }

    override fun close() {
        writer?.println("</body></html>")
        writer?.close()
    }

    fun getResult() = fileName!!
}
fun main(){
    str2Num("one2412")
}