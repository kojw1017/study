package org.example.dev.코테

// 문제 : https://www.acmicpc.net/problem/17837

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer


private const val WHITE = 0
private const val BLUE = 2


private fun inBounds(x: Int, y: Int, n: Int): Boolean =
    x in 0 until n && y in 0 until n

private enum class Direction(val dx: Int, val dy: Int) {
    RIGHT(0, 1),
    LEFT(0, -1),
    UP(-1, 0),
    DOWN(1, 0);

    companion object {
        operator fun invoke(d: Int): Direction = when (d) {
            1 -> RIGHT
            2 -> LEFT
            3 -> UP
            4 -> DOWN
            else -> throw IllegalArgumentException("Unknown direction: $d")
        }

        fun reverse(d: Direction): Direction = when (d) {
            RIGHT -> LEFT
            LEFT -> RIGHT
            UP -> DOWN
            DOWN -> UP
        }
    }
}

private data class Horse(
    var x: Int,
    var y: Int,
    var d: Direction
)

/**
 * (nx, ny)가 보드 밖이면 BLUE 취급, 아니면 실제 색 반환
 */
private fun colorAtOrBlue(colors: Array<IntArray>, x: Int, y: Int): Int {
    val n = colors.size
    return if (inBounds(x, y, n)) colors[x][y] else BLUE
}

/**
 * 특정 말(idx)부터 위에 있는 말들까지 (x, y) → (nx, ny)로 옮긴다.
 * 색이 RED이면 이동 스택의 순서를 뒤집어서 옮긴다.
 * 반환값: 목적지 칸에 쌓인 말 개수
 */
private fun Array<Array<MutableList<Int>>>.moveGroup(
    horses: Array<Horse>,
    idx: Int,
    nx: Int,
    ny: Int,
    color: Int
): Int {
    val horse = horses[idx]
    val curCell = this[horse.x][horse.y]

    val from = curCell.indexOf(idx)
    // 이 말부터 위에 있는 말들만 이동 대상 (subList는 뷰라서 복사본을 따로 만든다)
    val moving = curCell.subList(from, curCell.size)
    val ordered = (if (color == WHITE) moving else moving.asReversed()).toList()
    moving.clear()

    val destCell = this[nx][ny]
    ordered.forEach { hIdx ->
        destCell.add(hIdx)
        horses[hIdx].apply {
            x = nx
            y = ny
        }
    }
    return destCell.size
}

private fun solve(
    colors: Array<IntArray>,
    board: Array<Array<MutableList<Int>>>,
    horses: Array<Horse>
): Int {
    outer@ for (turn in 1..1000) {
        for (i in horses.indices) {
            val h = horses[i]
            val x = h.x
            val y = h.y
            var d = h.d

            var nx = x + d.dx
            var ny = y + d.dy

            if (board[x][y].firstOrNull() != i) continue

            var nextColor = colorAtOrBlue(colors, nx, ny)

            // 파란색 or 보드 밖이면 방향 반전 후 한 번 더 시도
            if (nextColor == BLUE) {
                d = Direction.reverse(d)
                h.d = d

                nx = x + d.dx
                ny = y + d.dy
                nextColor = colorAtOrBlue(colors, nx, ny)

                if (nextColor == BLUE) continue   // 여전히 파란색/밖이면 이동 X
            }

            val height = board.moveGroup(horses, i, nx, ny, nextColor)
            if (height >= 4) {
                return turn
            }
        }
    }

    return -1
}

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var st = StringTokenizer(br.readLine())

    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()

    val colors = Array(n) {
        st = StringTokenizer(br.readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    val board = Array(n) { Array(n) { mutableListOf<Int>() } }

    val horses = Array(k) { idx ->
        st = StringTokenizer(br.readLine())
        val x = st.nextToken().toInt() - 1
        val y = st.nextToken().toInt() - 1
        val d = st.nextToken().toInt()
        board[x][y].add(idx)
        Horse(x, y, Direction(d))
    }

    println(solve(colors, board, horses))
}
