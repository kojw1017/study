package org.example.dev.코테


/**
 * 방 청소 시뮬레이션.
 *
 * @param startR 시작 행
 * @param startC 시작 열
 * @param startD 시작 방향(0:북,1:동,2:남,3:서)
 * @param room   0:미청소, 1:벽, 2:청소됨
 * @return 청소한 칸 수
 *
 * ⚠️ room 배열을 **직접 수정**합니다(청소 칸을 2로 표시). 원본 보존이 필요하면 호출 전 깊은 복사를 하세요.
 */
private const val EMPTY = 0
private const val WALL = 1
private const val CLEANED = 2

private val dr = arrayOf(-1, 0, 1, 0) // 북, 동, 남, 서
private val dc = arrayOf(0, 1, 0, -1)

private fun turnLeft(d: Int) = (d + 3) % 4
private fun backward(d: Int) = (d + 2) % 4

private fun isRange(r: Int, c: Int, room: Array<IntArray>) =
    r in room.indices && c in room[0].indices

fun getCountOfDepartmentsCleanedByRobotVacuum(
    startR: Int,
    startC: Int,
    startD: Int,
    room: Array<IntArray>
): Int {
    var cleanedCount = 0

    var r = startR
    var c = startC
    var d = startD

    while (true) {
        if (isRange(r, c, room) && room[r][c] == EMPTY) {
            room[r][c] = CLEANED
            cleanedCount++
        }

        var isMoved = false
        for (i in 0 until 4) {
            d = turnLeft(d)
            val nr = r + dr[d]
            val nc = c + dc[d]
            if (isRange(nr, nc, room) && room[nr][nc] == EMPTY) {
                r = nr
                c = nc
                isMoved = true
                break
            }
        }
        if (isMoved) continue

        val backD = backward(d)
        val br = r + dr[backD]
        val bc = c + dc[backD]
        if (!isRange(br, bc, room) || room[br][bc] == WALL) {
            break
        } else {
            r = br
            c = bc
        }
    }

    return cleanedCount
}


fun main() {
    val currentR = 7
    val currentC = 4
    val currentD = 0
    val currentRoomMap = arrayOf(
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 1, 1, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 1, 1, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 1, 1, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 1, 1, 0, 1),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    val cleaned = getCountOfDepartmentsCleanedByRobotVacuum(
        currentR, currentC, currentD, currentRoomMap
    )
    println("로봇 청소기가 청소한 칸 수: $cleaned") // 기대값: 57

    //청소되기전 방 보기
    println("청소전 방 상태")
    for (row in currentRoomMap) {
        println(row.joinToString(" ") { if (it == 1) "#" else " " })
    }
    println()
    //청소된 방 보기
    println("청소후 방 상태")
    for (row in currentRoomMap) {
        println(row.joinToString(" ") {
            when (it) {
                1 -> "#"
                2 -> "."
                else -> " "
            }
        })
    }
}
