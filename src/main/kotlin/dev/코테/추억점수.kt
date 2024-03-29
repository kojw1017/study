package ㅇ
class Solution {
    fun solution(name: Array<String>, yearning: IntArray, photo: Array<Array<String>>): IntArray {
        var answer = mutableListOf<Int>()
        val nameMap = name.zip(yearning.toTypedArray()).toMap()
        photo.forEachIndexed { index, strings ->
            var yearningCount = 0
            strings.forEach { j -> yearningCount += nameMap[j]?:0 }
            answer.add(yearningCount)
        }
        return answer.toIntArray()
    }
}

class Solution2 {
    fun solution(names: Array<String>, yearnings: IntArray, photoes: Array<Array<String>>): IntArray {
        val map = names.zip(yearnings.toTypedArray()).toMap()
        return photoes.map { photo -> photo.sumOf { map[it] ?: 0 } }.toIntArray()
    }
}
fun main(){
    val names = arrayOf("may", "kein", "kain", "radi")
    val yearning = intArrayOf(5, 10, 1, 3)
    val photos = arrayOf(arrayOf("may", "kain", "kain"), arrayOf("may", "radi", "kain"))
    Solution().solution(names, yearning, photos).forEach {
        println(it)
    }
}