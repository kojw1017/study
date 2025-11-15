package org.example.dev.코테

// 문제 : https://www.acmicpc.net/problem/17837

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

private const val WHITE = 0
private const val BLUE = 2

private enum class Dir(val dx: Int, val dy: Int) {
    RIGHT(0, 1),
    LEFT(0, -1),
    UP(-1, 0),
    DOWN(1, 0);

    companion object {
        operator fun invoke(d: Int): Dir {
            return when (d) {
                1 -> RIGHT
                2 -> LEFT
                3 -> UP
                4 -> DOWN
                else -> throw IllegalArgumentException("Unknown dir: $d")
            }
        }

        fun reverse(d: Dir): Dir {
            return when (d) {
                RIGHT -> LEFT
                LEFT -> RIGHT
                UP -> DOWN
                DOWN -> UP
            }
        }
    }
}

private data class Horse(var x: Int, var y: Int, var d: Dir)

private fun isBounds(x: Int, y: Int, n: Int) = x in 0 until n && y in 0 until n

private fun solution(colors: Array<IntArray>, board: Array<Array<MutableList<Int>>>, horses: Array<Horse>): Int {
    val n = colors.size

    for (turn in 1..1000) {
        for (i in horses.indices) {
            val horse = horses[i]

            val x = horse.x
            val y = horse.y
            var d = horse.d

            var nx = x + d.dx
            var ny = y + d.dy

            var nextColor = if (isBounds(nx, ny, n)) colors[nx][ny] else BLUE

            if (nextColor == BLUE) {
                d = Dir.reverse(d)
                horse.d = d

                nx = x + d.dx
                ny = y + d.dy

                nextColor = if (isBounds(nx, ny, n)) colors[nx][ny] else BLUE

                if (nextColor == BLUE) continue
            }

            val currBoard = board[x][y]
            val from = currBoard.indexOf(i)

            val horsesSubList = currBoard.subList(from, currBoard.size)
            val moveHorses = (if (nextColor == WHITE) horsesSubList else horsesSubList.asReversed()).toList()
            horsesSubList.clear()

            val nextBoard = board[nx][ny]

            for (horseIdx in moveHorses) {
                horses[horseIdx].apply {
                    this.x = nx
                    this.y = ny
                }
                nextBoard.add(horseIdx)
            }

            if (nextBoard.size >= 4) {
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
        Horse(x, y, Dir(d))
    }

    println(solution(colors, board, horses))
}
