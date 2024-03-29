package 코테1

class Solution {
    fun solution(wallpaper: Array<String>): IntArray {
        val tempList = mutableListOf<List<Int>>()
        wallpaper.forEachIndexed { i, s ->
            s.forEachIndexed { j, c ->
                if(c === '#') tempList.add(listOf(i, j))
            }
        }
        val minX = tempList.minOfOrNull { it[0] }!!
        val minY = tempList.minOfOrNull { it[1] }!!
        val maxX = tempList.maxOfOrNull { it[0] }!!
        val maxY = tempList.maxOfOrNull { it[1] }!!
        return intArrayOf(minX, minY, maxX+1, maxY+1)
    }
}

fun main(){

    val wallpaper = arrayOf("..........", ".....#....", "......##..", "...##.....", "....#.....")
    println(Solution().solution(wallpaper))
}