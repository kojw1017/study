private const val WALL = 1
private const val EMPTY = 0
private const val CLEANED = 2

fun main() {
    val br = System.`in`.bufferedReader()

    // 1. 입력
    val (n, m) = br.readLine().split(" ").map { it.toInt() }
    val (r0, c0, d0) = br.readLine().split(" ").map { it.toInt() }

    val room = Array(n) {
        br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    // 2. 시뮬레이션 실행
    val result = getCleanedCount(r0, c0, d0, room)

    // 3. 정답 출력
    println(result)
}

fun getCleanedCount(startR: Int, startC: Int, startD: Int, room: Array<IntArray>): Int {
    var r = startR
    var c = startC
    var d = startD

    val dr = arrayOf(-1, 0, 1, 0) // 북, 동, 남, 서
    val dc = arrayOf(0, 1, 0, -1)

    fun turnLeft(direction: Int) = (direction + 3) % 4
    fun backward(direction: Int) = (direction + 2) % 4
    fun inBounds(r: Int, c: Int) = r in room.indices && c in room[0].indices

    var cleaned = 0

    while (true) {
        // 1) 현재 위치 청소
        if (room[r][c] == EMPTY) {
            room[r][c] = CLEANED
            cleaned++
        }

        // 2) 왼쪽부터 4방향 탐색
        var moved = false
        for (i in 0 until 4) {
            d = turnLeft(d)
            val nr = r + dr[d]
            val nc = c + dc[d]
            if (inBounds(nr, nc) && room[nr][nc] == EMPTY) {
                r = nr
                c = nc
                moved = true
                break
            }
        }
        if (moved) continue

        // 3) 네 방향 모두 불가 → 후진 시도
        val backDir = backward(d)
        val br = r + dr[backDir]
        val bc = c + dc[backDir]
        if (!inBounds(br, bc) || room[br][bc] == WALL) break

        r = br
        c = bc
    }
    return cleaned
}
