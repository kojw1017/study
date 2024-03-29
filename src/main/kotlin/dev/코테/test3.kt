fun print(board: Array<IntArray>){
    println()
    println("--------------------------------")
    board.forEachIndexed { _, b ->
        b.forEach(::print)
        println()
    }
    println()
    println("--------------------------------")
}
fun solution(board: Array<IntArray>, moves: IntArray): Int {

    var basket = mutableListOf<Int>()

    moves.forEach(::print)
    print(board)

    var count = 0

    fun check(basket: MutableList<Int>): MutableList<Int>{
        for(i in 0 until basket.size-1){
            if(basket[i] == basket[i+1]){
                count += 2
                basket.removeLast()
                basket.removeLast()
                return basket
            }
        }
        return basket
    }

    for (movesIdx in moves.indices){
        val idx = moves[movesIdx] - 1
        for(boardIdx in board.indices){
            if(board[boardIdx][idx] != 0){ // board의 원소가 0이 아니면
                basket += board[boardIdx][idx] // basket에 넣어라 board의원소를
                board[boardIdx][idx] = 0 //하고 0으로 초기화
                break
            }
        }
        basket = check(basket)
    }

    print(board)
    basket.forEach(::print)
    println()
    return count

}


fun main(){
    println(solution(arrayOf(intArrayOf(0,0,0,0,0), intArrayOf(0,0,1,0,3), intArrayOf(0,2,5,0,1), intArrayOf(4,2,4,4,2), intArrayOf(3,5,1,3,1)), intArrayOf(1,5,3,5)))
}

// [0,0,0,0,0] 0
// [0,0,1,0,3] 1
// [0,2,5,0,1] 2