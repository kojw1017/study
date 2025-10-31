package org.example.dev.코테

private fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val t = this[i]
    this[i] = this[j]
    this[j] = t
}

fun main() {

    fun <T : Comparable<T>> MutableList<T>.bubbleSortOptimized() {
        if (size < 2) return
        for (i in 0 until lastIndex) {                 // i: 패스 번호
            var swapped = false
            for (j in 0 until lastIndex - i) {         // j+1이 최대 lastIndex - i까지
                if (this[j] > this[j + 1]) {
                    swap(j, j + 1)
                    swapped = true
                }
            }
            if (!swapped) break
        }
    }

    val b = mutableListOf(1,2,3)
    println("Before: $b")
    b.bubbleSortOptimized()    // 또는 a.bubbleSortClassic()
    println("After : $b")      // [1, 1, 2, 3, 4, 5, 5, 41]

}
