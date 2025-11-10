package org.example.dev.코테

/**
 * 라인 인턴 채용 코테 문제 - 나잡아봐라
 *
 * 연인 코니와 브라운은 들판에서 나잡아봐라 게임을한다
 * 이 게임은 브라운이 코니를 잡거나, 코니가 일정 거리 이상 도망가면 끝난다
 * 게임이 끝나는데 걸리는 최소 시간을 구하는 프로그램을 작성하라
 *
 * 조건은 다음과 같다
 * 코니는 처음 위치 C에서 1초후 1만큼 움직이고
 * 이후 가속이 붙어 매초마다 이전 이동거리 + 1만큼 움직인다
 * 시간에 따른 코니의 위치는 C, C + 1, C + 3, C + 6, C + 10, ...
 * 즉 n초 후 코니의 위치는 C + (n * (n + 1)) / 2 이다
 * C가 1일때
 * 1초 후 2
 * 2초 후 4
 * 3초 후 7
 * 4초 후 11
 * 5초 후 16
 * 3 초 후 1 + (3 * (3 + 1)) / 2
 * 4 초 후 1 + (4 * (4 + 1)) / 2
 * N 초 후 C + (N * (N + 1)) / 2
 *
 * 브라운은 현재 위치 B에서 다음 순간 3가지 방법으로 움직일 수 있다
 * B - 1, B + 1, B * 2
 * 브라운은 코니를 잡기 위해 최적의 경로로 움직인다
 * 코니와 브라운의 위치 P는 조건 (0 <= P <= 200,000)을 만족한다
 *
 * 브라운은 범위를 벗어나는 위치로 이동할 수 없다
 * 코니가 범위를 벗어나면 게임이 끝난다
 *
 */
import java.util.ArrayDeque

private const val MAX = 200_000

fun catchConyFast(c: Int, b: Int, verbose: Boolean = true): Int {
   if(b == c) return 0

    val visited = Array(2){ BooleanArray(MAX + 1) }
    val queue = ArrayDeque<Pair<Int, Int>>()
    queue.add(b to 0)
    visited[0][b] = true

    var time = 0
    while (queue.isNotEmpty()) {
        val conyPos = c + (time * (time + 1)) / 2
        if (conyPos > MAX) {
            if (verbose) println("코니가 범위를 벗어났습니다. (시간: $time, 위치: $conyPos)")
            break
        }
        if (visited[time % 2][conyPos]) {
            if (verbose) println("코니를 잡았습니다! (시간: $time, 위치: $conyPos)")
            return time
        }

        val size = queue.size
        for (i in 0 until size) {
            val (brownPos, t) = queue.removeFirst()
            val nextTime = t + 1
            val nextMod = nextTime % 2

            val nextPositions = listOf(brownPos - 1, brownPos + 1, brownPos * 2)
            for (nextPos in nextPositions) {
                if (nextPos in 0..MAX && !visited[nextMod][nextPos]) {
                    visited[nextMod][nextPos] = true
                    queue.add(nextPos to nextTime)
                }
            }
        }
        time++
    }
    return -1
}

fun main() {
    val c = 11
    val b = 2
    println("입력: 코니=$c, 브라운=$b")
    val result = catchConyFast(c, b, verbose = true)
    println("최소 시간: $result")
}
