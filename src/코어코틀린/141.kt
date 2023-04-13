package 코어코틀린

//336p

fun remoteProcess1(): Boolean = kotlin.random.Random.nextBoolean()
fun remoteProcess2(): Boolean = kotlin.random.Random.nextBoolean()
fun otherProcess(): String? = if(kotlin.random.Random.nextBoolean()) null

else kotlin.random.Random.nextInt().toString()
fun process1() = if (remoteProcess1()) throw Throwable("otherProcess()에서 정상 처리가 되지 않음") else "처리 정상 종료"
fun process2(): String {
    val midResult: Int = otherProcess()?.split("2")?.size ?: return ""
    return "결과는 = $midResult"
}
fun main() {
    outer@ for (i in 2..9) {
        println("start outer loop $i")
        if (i % 2 == 0) continue
        inner@ for (j in 2..9) {
            println("start inner loop $j")
            if (j % 2 == 0) continue@outer
        }
    }
}