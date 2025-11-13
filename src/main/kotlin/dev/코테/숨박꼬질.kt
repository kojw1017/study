package org.example.dev.코테

//문제
//수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다. 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 1초 후에 2*X의 위치로 이동하게 된다.
//
//수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.
//
//입력
//첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.
//
//출력
//수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.

private const val MIN_POS = 0
private const val MAX_POS = 100_000

fun readNKOrThrow(): Pair<Int, Int> {
    val (n, k) = readLine()
        ?.trim()
        ?.split(Regex("\\s+"))
        ?.takeIf { it.size == 2 }
        ?.mapIndexed { i, token ->
            token.toIntOrNull()
                ?: throw IllegalArgumentException("입력 오류: ${if (i == 0) "N" else "K"}이(가) 정수가 아닙니다. (값='${token}')")
        }
        ?: throw IllegalArgumentException("입력 오류: N과 K를 공백으로 구분해 입력하세요.")

    require(n in MIN_POS..MAX_POS) { "입력 범위 오류: N=$n (허용 범위: $MIN_POS..$MAX_POS)" }
    require(k in MIN_POS..MAX_POS) { "입력 범위 오류: K=$k (허용 범위: $MIN_POS..$MAX_POS)" }

    return n to k
}

fun main() {
    val (N, K) = readNKOrThrow()
    val q = ArrayDeque<Int>()
    val dist = IntArray(MAX_POS + 1) { -1 }
    q.add(N)
    dist[N] = 0
    while (q.isNotEmpty()) {
        val x = q.removeFirst()
        if (x == K) {
            println(dist[x]); break
        }
        for (nx in intArrayOf(x - 1, x + 1, x * 2)) {
            if (nx in MIN_POS..MAX_POS && dist[nx] == -1) {
                dist[nx] = dist[x] + 1
                q.add(nx)
            }
        }
    }
}
