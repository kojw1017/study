package 코테

class Solution {
    fun solution(arr: IntArray): IntArray {
        if(arr.size == 1) return intArrayOf(-1)
        var min = arr[0]
        arr.forEachIndexed { index, i -> if(min > i) min = i }
        var answer = intArrayOf()
        arr.forEach { if(min != it) answer += it }
        return answer
    }
}

fun main(){
    val intArr = intArrayOf(1, 2, 3, 4)
    val a = Solution().solution(intArr)
    a.forEach(::println)
}