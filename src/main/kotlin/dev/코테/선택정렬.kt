package org.example.dev.코테


// swap 확장 함수
private fun <T> MutableList<T>.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

fun main() {
    val a = mutableListOf(5, 1, 2, 3, 4, 41, 1, 5)

    fun <T : Comparable<T>> MutableList<T>.selectionSort() {
        for (i in 0 until lastIndex) {
            var minIdx = i
            for (j in i + 1..lastIndex) {
                if (this[j] < this[minIdx]) minIdx = j
            }
            if (minIdx != i) swap(i, minIdx)
        }
    }

    println("\n최종 결과: $a")
}
