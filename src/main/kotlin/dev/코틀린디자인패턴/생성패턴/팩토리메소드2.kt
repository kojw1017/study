package 코틀린디자인패턴.생성패턴33

import java.lang.reflect.Member
import kotlin.reflect.KClass
import kotlin.reflect.KFunction2


interface Test
class Test1:Test
class Test2(val a:Int, b:Int):Test


fun interface Factories<T: Any>:()->T
class NoArgFactory<T: Any> @PublishedApi internal constructor(
    val type: KClass<T>, val factory: ()->T
): Factories<T>{
    companion object{
        suspend inline operator fun <reified T: Any>invoke (noinline factory: ()->T): Factories<T> {
            return NoArgFactory(T:: class, factory)
        }
    }
    override inline fun invoke(): T= factory()
}

suspend fun main(){

    val memberFactory: Factories<Test1> = NoArgFactory(::Test1)
    suspend fun getMember() = memberFactory
    data class Person(val name: String, var age: Int)

    val person = Person("Alice", 20)
    val ageProperty = Person::age
    println(ageProperty.get(person)) // 출력: 20
    ageProperty.set(person, 21)
    println(person.age) // 출력: 21
}