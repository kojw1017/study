package 코어코틀린

//270P

class IterOneToFive: Iterable<Int> {
    override fun iterator(): Iterator<Int> = iterator {
        (1..5).forEach { yield(it) }
    }
}
fun main() {
    val iterOne2Five = iterator {
        yield(1)
        yield(2)
        yield(3)
        yield(4)
        yield(5)
    }
    for (i in iterOne2Five) {
        println(i)
    }
}