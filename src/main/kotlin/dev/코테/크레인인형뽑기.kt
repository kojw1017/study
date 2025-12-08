package org.example.dev.코테

fun main() {

}

private fun solution(board: Array<IntArray>, moves: IntArray): Int {
    val columns = buildColumnStacks(board)
    return runCraneGame(columns, moves)
}

/**
 * board[row][col] 형태의 2차원 배열을
 * 각 열마다 스택(Deque)로 변환
 */
private fun buildColumnStacks(board: Array<IntArray>): Array<ArrayDeque<Int>> {
    val n = board[0].size
    val columns = Array(n) { ArrayDeque<Int>() }

    // 위에서 아래로 내려가며 0이 아닌 값만 열의 앞쪽에 쌓기
    // -> removeLast()가 "맨 위 인형"이 되도록
    for (row in board) {
        row.forEachIndexed { col, value ->
            if (value != 0) {
                columns[col].addFirst(value)
            }
        }
    }

    return columns
}

/**
 * columns: 각 열의 인형 스택
 * moves: 크레인 열 인덱스(1-based)
 */
private fun runCraneGame(
    columns: Array<ArrayDeque<Int>>,
    moves: IntArray
): Int {
    val basket = ArrayDeque<Int>()
    var removedCount = 0

    for (move in moves) {
        val colIndex = move - 1
        val column = columns[colIndex]

        if (column.isEmpty()) continue

        val doll = column.removeLast()
        val top = basket.lastOrNull()

        if (doll == top) {
            basket.removeLast()
            removedCount += 2
        } else {
            basket.addLast(doll)
        }
    }

    return removedCount
}
