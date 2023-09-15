package ㅋㅋ
class Solution {
    fun solution(park: Array<String>, routes: Array<String>): IntArray {
        var answer: IntArray = intArrayOf()

        val parkArray = park.map { it.map { char -> char.toString() }.toTypedArray() }.toTypedArray()
        parkArray.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                if(s == "S") println("$i $j")
            }
        }

        return answer
    }
}
fun main(){
    val v1 = arrayOf("SOO", "OOO", "OOO")
    val v2 = arrayOf("E 2", "S 2", "W 1")
    Solution().solution(v1, v2)
}