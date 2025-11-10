package org.example.dev.코테

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer


private const val MAX = 100_000

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val N = st.nextToken().toInt()
    val K = st.nextToken().toInt()

    if (N == K) {
        println(0)
        println(N)
        return
    }

    val dq = ArrayDeque<Int>()
    val visited = BooleanArray(MAX + 1)
    val dist = IntArray(MAX + 1) { 0 }
    val prev = IntArray(MAX + 1) { -1 } // 경로 복원용: next의 부모가 누군지

    dq.add(N)
    visited[N] = true
    prev[N] = -1

    while (dq.isNotEmpty()) {
        val pos = dq.removeFirst()
        for (next in intArrayOf(pos - 1, pos + 1, pos * 2)) {
            if (next !in 0..MAX || visited[next]) continue
            visited[next] = true
            dist[next] = dist[pos] + 1
            prev[next] = pos
            dq.add(next)
            if (next == K) { // 찾았으면 더 탐색할 필요 없음
                dq.clear()
                break
            }
        }
    }

    // 시간 출력
    println(dist[K])

    // 경로 복원: K -> ... -> N 을 역으로 따라간 뒤 뒤집기
    val path = ArrayList<Int>()
    var cur = K
    while (cur != -1) {
        path.add(cur)
        cur = prev[cur]
    }
    path.reverse()
    println(path.joinToString(" "))
}