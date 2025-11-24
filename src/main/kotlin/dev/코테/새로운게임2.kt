package org.example.dev.코테

// 문제 : https://www.acmicpc.net/problem/17837

import java.util.StringTokenizer

private const val WHITE = 0
private const val BLUE = 2

private enum class Dir(val dx: Int, val dy: Int) {
    RIGHT(0, 1),
    LEFT(0, -1),
    UP(-1, 0),
    DOWN(1, 0);

    companion object {
        operator fun invoke(value: Int): Dir {
            return when (value) {
                1 -> RIGHT
                2 -> LEFT
                3 -> UP
                4 -> DOWN
                else -> throw IllegalArgumentException("Invalid direction value: $value")
            }
        }

        fun reserve(dir: Dir): Dir {
            return when (dir) {
                RIGHT -> LEFT
                LEFT -> RIGHT
                UP -> DOWN
                DOWN -> UP
            }
        }
    }
}

private fun isBounds(x: Int, y: Int, n: Int): Boolean {
    return x in 0 until n && y in 0 until n
}

private class Horse(var x: Int, var y: Int, var dir: Dir)

private fun solution(colors: Array<Array<Int>>, board: Array<Array<MutableList<Int>>>, horses: Array<Horse>): Int {
    val n = colors.size

    for (turn in 1..1000) {

        for (i in horses.indices) {
            val horse = horses[i]
            val x = horse.x
            val y = horse.y
            var d = horse.dir

            var nx = x + d.dx
            var ny = y + d.dy

            var nextColor = if (isBounds(nx, ny, n)) colors[nx][ny] else BLUE

            if (nextColor == BLUE) {
                d = Dir.reserve(d)
                nx = x + d.dx
                ny = y + d.dy
                horse.dir = d

                nextColor = if (isBounds(nx, ny, n)) colors[nx][ny] else BLUE

                if (nextColor == BLUE) continue
            }

            val curBoard = board[x][y]
            val idx = curBoard.indexOf(i)
            val movingHorses = curBoard.subList(idx, curBoard.size)
            val toMove = if (nextColor == WHITE) {
                movingHorses.toList()
            } else {
                movingHorses.reversed()
            }
            movingHorses.clear()

            val nextBoard = board[nx][ny]
            toMove.forEach {
                horses[it].x = nx
                horses[it].y = ny
                nextBoard.add(it)
            }

            if (nextBoard.size >= 4) {
                return turn
            }
        }
    }

    return -1
}

fun main() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())

    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()

    val board = Array(n) { Array(k) { mutableListOf<Int>() } }

    val colors = Array(n) {
        st = StringTokenizer(br.readLine())
        Array(n) {
            st.nextToken().toInt()
        }
    }

    val horses = Array(k) {
        st = StringTokenizer(br.readLine())
        val x = st.nextToken().toInt() - 1
        val y = st.nextToken().toInt() - 1
        val dir = Dir(st.nextToken().toInt())
        board[x][y].add(it)
        Horse(x, y, dir)
    }

    //입력 테스트
    println("Board Colors:")
    for (row in colors) {
        println(row.joinToString(" "))
    }
    println("Horses:")
    for ((index, horse) in horses.withIndex()) {
        println("Horse $index: Position (${horse.x}, ${horse.y}), Direction ${horse.dir}")
    }

    println(solution(colors, board, horses))
}
