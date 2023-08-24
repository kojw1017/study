package ㄴ
class Solution2 {
    fun solution(players: Array<String>, callings: Array<String>): Array<String> {
        var answer = players
        callings.forEach {
            val pIndex = players.indexOf(it)
            val prevIndex = pIndex - 1
            players[pIndex] = players[prevIndex]
            players[prevIndex] = it
        }
        return answer
    }
}

class Solution {
    fun solution(players: Array<String>, callings: Array<String>): Array<String> {
        val positionMap = HashMap<String, Int>()
        players.forEachIndexed { index, player -> positionMap[player] = index }
        callings.forEach { calling ->
            val currentPosition = positionMap[calling]!!
            val prevPosition = currentPosition - 1

            // switching the players
            players[currentPosition] = players[prevPosition]
            players[prevPosition] = calling

            // update the positions in the map
            positionMap[calling] = prevPosition
            positionMap[players[currentPosition]] = currentPosition
        }
        return players
    }
}
fun main(){
    val players = arrayOf("mumu", "soe", "poe", "kai", "mine")
    val callings = arrayOf("kai", "kai", "mine", "mine")
    val result = Solution().solution(players, callings)
    result.forEach {
        println(it)
    }
}