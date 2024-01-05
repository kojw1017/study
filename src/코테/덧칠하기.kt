package 코테2

class Solution {
    fun solution(n: Int, m: Int, section: IntArray):Int{
        var answer = 0
        var nextSection = 0
        section.forEach {
            println(it)
            if (it >= nextSection) {
                answer++
                nextSection = it + m
            }
            println(nextSection)
        }
        return answer
    }
}

fun main(){
    Solution().solution(8, 4, intArrayOf(1, 6))
}