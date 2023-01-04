package Adapter


/*
    프록시(대리인) 패턴

    해야하는 무거운 일을 위임하고
    그일이 필요로 할때 인스턴스를 생성하고
    인스턴스를 재생성 하지 않는다


*/
interface Display{
    fun print(content: String)
}
class BufferDisplay(private val bufferSize: Int): Display{
    private val buffer = mutableListOf<String>()
    private var screen: ScreenDisplay? = null
    override fun print(content: String) {
        buffer.add(content)
        if(buffer.size == bufferSize) flush()
    }
    fun flush(){
        if(screen == null) screen = ScreenDisplay()
        val lines = buffer.joinToString("\n")
        screen?.print(lines)
        buffer.clear()
    }
}
class ScreenDisplay: Display{
    override fun print(content: String) {
        try {
            Thread.sleep(500)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }
        println(content)
    }
}

fun main() {
    val display = BufferDisplay(6)

    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.print("안녕하세요")
    display.flush()
}
