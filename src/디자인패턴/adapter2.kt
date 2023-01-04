package Adapter

/*
    어댑터 패턴
    변경할수 없는 클래스를 같은 인터페이스 기능으로 사용하고 싶을때
*/
abstract class Animal(protected var name: String){
    abstract fun sound()
}
class Dog(name: String): Animal(name){
    override fun sound() {
        println("$name Barking")
    }
}
class Cat(name: String): Animal(name){
    override fun sound() {
        println("$name Meow")
    }
}
class TigerAdapter(name: String): Animal(name){
    private var tiger = Tiger(name)
    override fun sound(){
        println("${tiger.getName()}   ")
        tiger.roar()
    }
}
class Tiger(private var name: String){
    fun getName() = name
    fun roar() { println("$name growl") }
}
fun main(){
    listOf(Dog("개1"), Dog("개2"), Cat("고양이1"), TigerAdapter("호랑이1"))
        .forEach{ it.sound() }
}