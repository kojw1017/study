package design

class Singleton{
    private companion object{ private val singleton = Singleton() }
    init { println("인스턴스 생성 완료") }
    fun getInstance() = singleton
}
fun main(){
    val obj1 = Singleton().getInstance()
    val obj2 = Singleton().getInstance()
    if(obj1 === obj2) println("같어~")
    else println("달러~")
}