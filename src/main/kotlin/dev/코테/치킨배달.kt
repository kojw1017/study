package org.example.dev.코테

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.abs
import kotlin.math.min

private data class Pos(val r: Int, val c: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val houses = mutableListOf<Pos>()
    val chickens = mutableListOf<Pos>()

    repeat(n) { r ->
        st = StringTokenizer(readLine())
        for (c in 0 until n) {
            when (st.nextToken().toInt()) {
                1 -> houses.add(Pos(r, c))
                2 -> chickens.add(Pos(r, c))
            }
        }
    }

    println(solution(houses, chickens, m))
}

private fun solution(
    houses: List<Pos>,
    chickens: List<Pos>,
    m: Int
): Int {
    var answer = Int.MAX_VALUE
    val selected = IntArray(m) // 선택된 치킨집 인덱스

    // 현재 selected 조합으로 도시 치킨 거리 계산
    fun computeCityDistance(): Int {
        var total = 0
        for (house in houses) {
            var minDist = Int.MAX_VALUE
            // 선택된 M개의 치킨집 중 최소 거리
            for (i in 0 until m) {
                val chicken = chickens[selected[i]]
                val d = abs(house.r - chicken.r) + abs(house.c - chicken.c)
                minDist = min(minDist, d)
            }
            total += minDist

            // 이미 현재 최솟값보다 크거나 같으면 더 계산할 필요 없음 (가지치기)
            if (total >= answer) return total
        }
        return total
    }

    fun dfs(start: Int, depth: Int) {
        // M개 다 골랐으면 거리 계산
        if (depth == m) {
            val cityDist = computeCityDistance()
            if (cityDist < answer) answer = cityDist
            return
        }

        // start부터 순서대로 골라서 조합 생성
        for (i in start until chickens.size) {
            selected[depth] = i
            dfs(i + 1, depth + 1)
        }
    }

    dfs(0, 0)
    return answer
}
