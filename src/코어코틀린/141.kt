package 코어코틀린

//416p

class Person(val name:String, val age: Int) {
    override fun hashCode() = (name.hashCode() * 31 + age) * 31
    fun greeting(s:String): String = "$s $name"
}
fun reverseName(p:Person) = p.name.reversed()

fun main() {
    val personList = listOf(Person("Injoo Oh", 31), Person("Inkyeong Oh",
        29), Person("Inhye Oh", 17))

    val sortedByAge = personList.sortedBy{it.name}.map { it.name }
    val sortedByHashCode = personList.sortedBy{it.hashCode()}
    val sortedByGreeting = personList.sortedBy{it.greeting("Hello")}
    val sortedByReverseName = personList.sortedBy{reverseName(it)}

    println(sortedByAge.joinToString())
    println(sortedByHashCode)
    println(sortedByGreeting)
    println(sortedByReverseName)
}