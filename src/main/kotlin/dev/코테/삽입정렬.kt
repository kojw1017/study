package org.example.dev.코테



fun main() {
    fun <T : Comparable<T>> MutableList<T>.insertionSortTrace() {
        if (size < 2) return

        println("초기: $this")
        for (i in 1..lastIndex) {
            val key = this[i]
            var j = i - 1
            println("j = $j 에서 시작")
            println("\n[i=$i] key=$key, 정렬된 구간=[0..${i-1}]")
            while (j >= 0 && this[j] > key) {
                println("  ${this[j]} > $key → 오른쪽으로 밀기: a[${j+1}] = a[$j]")
                this[j + 1] = this[j]
                j--
            }
            this[j + 1] = key
            println("  삽입: a[${j+1}] = $key")
            println("  상태: $this")
        }
        println("\n완료: $this")
    }
    val b = mutableListOf(5, 1, 2, 3, 4, 41, 1, 5)
    println("Before: $b")
    b.insertionSortTrace()    // 또는 a.bubbleSortClassic()
    println("After : $b")      // [1, 1, 2, 3, 4, 5, 5, 41]

}
