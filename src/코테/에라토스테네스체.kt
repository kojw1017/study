package ㅋㅋ
class Solution {
    fun solution(park: Array<String>, routes: Array<String>): IntArray {
        var answer: IntArray = intArrayOf()
        val currIdx = mutableListOf<Int>()
        val parkArray = park.map { it.map { char -> char.toString() }.toTypedArray() }.toTypedArray()
        parkArray.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                if(s == "S"){
                    currIdx.add(i)
                    currIdx.add(j)
                }
            }
        }
        routes.forEach {
            val road = it.split(" ")
            val op = when(road[0]){
                "E","W" -> 1
                "S","N" -> 0
                else -> 2
            }
            currIdx[op] = currIdx[op]+road[1].toInt()
        }

        return answer
    }
}
fun main(){
    val v1 = arrayOf("OSO", "OOO", "OOO")
    val v2 = arrayOf("E 2", "S 2", "W 1")
    Solution().solution(v1, v2)
}