package 코어코틀린

//416p

class Person(var name: String, var age: Int)

fun main() {
    val a = Person("A", 0).apply {
        name = "me"
        age = 30

        val person2 = Person("B", 0).also {
            it.name = "Go"
            it.age = 25

            println("${this@apply.name} ${it.name}") // Prints "me Go"
        }
    }
}