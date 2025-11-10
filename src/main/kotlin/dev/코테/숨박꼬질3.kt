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
    queue.add(N)
    dist[N] = 0

    while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()

        if (pos == K) {
            println(dist[pos])
            break
        }

        val nextPositions = listOf(pos - 1, pos + 1, pos * 2)

        for (nextPos in nextPositions) {

            if (nextPos !in 0..MAX) continue

            val nextDist = if (nextPos == pos * 2) dist[pos] else dist[pos] + 1

            if (dist[nextPos] == -1 || dist[nextPos] > nextDist) {
                dist[nextPos] = nextDist
                if (nextPos == pos * 2) {
                    queue.addFirst(nextPos)
                } else {
                    queue.addLast(nextPos)
                }
            }
        }
    }
}
