package 코어코틀린

//193P

class LongIterator(private val from: Long, private val to: Long): Iterator<Long>{
    private var curr = from
    override fun hasNext(): Boolean = curr <= to
    override fun next(): Long = curr++
}

fun main() {
    val iter = LongIterator(0L, 10L)
    while (iter.hasNext()){
        print("${iter.next()},")
    }
    println()
}
