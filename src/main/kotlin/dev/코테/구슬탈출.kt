package org.example.dev.코테

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.ArrayDeque

private data class Point(val row: Int, val col: Int)
private data class State(val red: Point, val blue: Point, val moves: Int)

// 상, 하, 좌, 우
private val directions = arrayOf(
    Point(-1, 0),
    Point(1, 0),
    Point(0, -1),
    Point(0, 1)
)

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m) = br.readLine().split(" ").map { it.toInt() }
    val board = Array(n) { CharArray(m) }

    var red = Point(0, 0)
    var blue = Point(0, 0)

    repeat(n) { row ->
        val line = br.readLine().toCharArray()
        for (col in 0 until m) {
            when (line[col]) {
                'R' -> {
                    red = Point(row, col)
                    board[row][col] = '.' // 빈칸으로 바꿔서 처리
                }
                'B' -> {
                    blue = Point(row, col)
                    board[row][col] = '.'
                }
                else -> board[row][col] = line[col]
            }
        }
    }

    println(canRedReachGoal(board, red, blue))
}

/**
 * 빨간 구슬만 구멍에 빠지게 할 수 있으면 1, 아니면 0
 */
private fun canRedReachGoal(
    board: Array<CharArray>,
    redStart: Point,
    blueStart: Point
): Int {
    val n = board.size
    val m = board[0].size

    val visited = Array(n) {
        Array(m) {
            Array(n) {
                BooleanArray(m)
            }
        }
    }

    val queue = ArrayDeque<State>()
    queue.add(State(redStart, blueStart, 0))
    visited[redStart.row][redStart.col][blueStart.row][blueStart.col] = true

    while (queue.isNotEmpty()) {
        val (currentRed, currentBlue, moves) = queue.removeFirst()

        // 10번 이하로만 굴릴 수 있음
        if (moves >= 10) continue

        for (dir in directions) {
            val (nextRed, redInHole) = move(board, currentRed, dir)
            val (nextBlue, blueInHole) = move(board, currentBlue, dir)

            // 파란 구슬이 들어가면 실패
            if (blueInHole) continue
            // 빨간 구슬만 들어가면 성공
            if (redInHole) return 1

            var adjustedRed = nextRed
            var adjustedBlue = nextBlue

            // 같은 위치에 있을 경우, 더 멀리서 온 구슬을 한 칸 뒤로 밀어낸다.
            if (adjustedRed == adjustedBlue) {
                when {
                    dir.row == -1 -> { // 위로 이동
                        if (currentRed.row < currentBlue.row) {
                            adjustedBlue = adjustedBlue.copy(row = adjustedBlue.row + 1)
                        } else {
                            adjustedRed = adjustedRed.copy(row = adjustedRed.row + 1)
                        }
                    }
                    dir.row == 1 -> { // 아래로 이동
                        if (currentRed.row < currentBlue.row) {
                            adjustedRed = adjustedRed.copy(row = adjustedRed.row - 1)
                        } else {
                            adjustedBlue = adjustedBlue.copy(row = adjustedBlue.row - 1)
                        }
                    }
                    dir.col == -1 -> { // 왼쪽으로 이동
                        if (currentRed.col < currentBlue.col) {
                            adjustedBlue = adjustedBlue.copy(col = adjustedBlue.col + 1)
                        } else {
                            adjustedRed = adjustedRed.copy(col = adjustedRed.col + 1)
                        }
                    }
                    dir.col == 1 -> { // 오른쪽으로 이동
                        if (currentRed.col < currentBlue.col) {
                            adjustedRed = adjustedRed.copy(col = adjustedRed.col - 1)
                        } else {
                            adjustedBlue = adjustedBlue.copy(col = adjustedBlue.col - 1)
                        }
                    }
                }
            }

            if (!visited[adjustedRed.row][adjustedRed.col][adjustedBlue.row][adjustedBlue.col]) {
                visited[adjustedRed.row][adjustedRed.col][adjustedBlue.row][adjustedBlue.col] = true
                queue.add(State(adjustedRed, adjustedBlue, moves + 1))
            }
        }
    }

    return 0
}

/**
 * 한 방향으로 쭉 굴렸을 때의 최종 위치와, 구멍에 빠졌는지 여부를 반환
 */
private fun move(
    board: Array<CharArray>,
    start: Point,
    dir: Point
): Pair<Point, Boolean> {
    var row = start.row
    var col = start.col

    while (true) {
        val nextRow = row + dir.row
        val nextCol = col + dir.col

        // 범위를 벗어나면 정지 (방어 코드, 실질적으로는 벽이 있어서 잘 안 걸림)
        if (nextRow !in board.indices || nextCol !in board[0].indices) break

        // 벽이면 바로 앞에서 멈춘다
        if (board[nextRow][nextCol] == '#') break

        row = nextRow
        col = nextCol

        // 구멍이면 바로 true 반환
        if (board[nextRow][nextCol] == 'O') {
            return Point(row, col) to true
        }
    }
    return Point(row, col) to false
}
