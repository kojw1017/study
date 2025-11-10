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
    val visited = BooleanArray(MAX + 1)
    val dist = IntArray(MAX + 1)

    queue.add(N)
    visited[N] = true
    dist[N] = 0

    var result = 0
    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()

        if (pos == K) {
            result = dist[pos]
            break
        }

        listOf(pos - 1, pos + 1, pos * 2).forEach { nextPos ->
            if (nextPos in 0..MAX && !visited[nextPos]) {
                visited[nextPos] = true
                queue.add(nextPos)
                dist[nextPos] = dist[pos] + 1
            }
        }
    }
    println(result)
}
