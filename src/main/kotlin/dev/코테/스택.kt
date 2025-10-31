package org.example.dev.코테


fun main() {
    fun findFirstQueueNumber(deque: ArrayDeque<Int>, n: Int): Int {
        while (deque.isNotEmpty()) {
            val last = deque.removeLastOrNull()
            val number = last ?: break
            if (number >= n) {
                return deque.indexOf(number) + 1
            }
        }
        return 0
    }

    val deque = ArrayDeque<Int>()
    deque.addLast(6)
    deque.addLast(9)
    deque.addLast(5)
    deque.addLast(7)
    deque.addLast(4)
    println(deque)  // 출력: [1, 2, 3]

    val result = mutableListOf<Int>()
    while (deque.isNotEmpty()) {
        val last = deque.removeLast()
        val findLast = deque.findLast{
            it >= last
        }
        if(findLast == null){
            result.add(0)
            continue
        }else{
            result.add(deque.indexOf(findLast) + 1)
        }
    }
    println(result.reversed())
}
