package ㅋ
class ParkWalking {
    fun solution(park: Array<String>, routes: Array<String>): IntArray {
        var currY = 0
        var currX = 0
        val moveY = mapOf('N' to -1, 'S' to 1, 'W' to 0, 'E' to 0)
        val moveX = mapOf('N' to 0, 'S' to 0, 'W' to -1, 'E' to 1)

        for (i in park.indices) {
            for (j in park[i].indices) {
                if (park[i][j] == 'S') {
                    currY = i
                    currX = j
                }
            }
        }

        for (route in routes) {
            val direction = route[0]
            val step = route.substring(2).toInt()
            var maxStep = step

            for (i in 1..step) {
                val newY = currY + moveY[direction]!! * i
                val newX = currX + moveX[direction]!! * i

                if (newY < 0 || newX < 0 ||newY >= park.size || newX >= park[0].length || park[newY][newX] == 'X') {
                    maxStep = 0
                    break
                }
            }

            currY += moveY[direction]!! * maxStep
            currX += moveX[direction]!! * maxStep
        }

        return intArrayOf(currY, currX)
    }
}
fun main(){
    val v1 = arrayOf("SOO", "OOO", "OOO")
    val v2 = arrayOf("E 2", "S 2", "W 1")
//    Solution().solution(v1, v2)
}