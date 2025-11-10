package org.example.dev.코테

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


private const val MAX = 100_000

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val N = st.nextToken().toInt()
    val K = st.nextToken().toInt()

    val queue = ArrayDeque<Int>()
    val dist = IntArray(MAX + 1) { -1 }
    val count = IntArray(MAX + 1) { 0 }

    queue.add(N)
    dist[N] = 0
    count[N] = 1

    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()

        for (nextPos in listOf(pos - 1, pos + 1, pos * 2)) {

            if (nextPos !in 0..MAX) continue

            if (dist[nextPos] == -1) {
                dist[nextPos] = dist[pos] + 1
                count[nextPos] = count[pos]
                queue.add(nextPos)
            } else if (dist[nextPos] == dist[pos] + 1) {
                count[nextPos] += count[pos]
            }
        }
    }

    println(dist[K])
    println(count[K])
}
