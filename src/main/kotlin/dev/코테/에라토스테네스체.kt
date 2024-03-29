package ㅋㅋ
class Solution {
    enum class Op(val v1:Int, val v2:Int){
        E(1, 1),
        W(1, -1),
        S(0, 1),
        N(0, -1);
    }
    private fun getStart(park: Array<Array<String>>):MutableList<Int>{
        val currIdx = mutableListOf<Int>()
        park.forEachIndexed { i, strings ->
            strings.forEachIndexed { j, s ->
                print(s)
                if(s == "S"){
                    currIdx.add(i)
                    currIdx.add(j)
                }
            }
            println()
        }
        return currIdx
    }
    fun solution(park: Array<String>, routes: Array<String>): IntArray {
        val answer = intArrayOf()
        val parkArray = park.map { it.map { char -> char.toString() }.toTypedArray() }.toTypedArray()
        val currIdx = getStart(parkArray)
        routes.forEach {
            val road = it.split(" ")
            val op = Op.valueOf(road[0])
            val a = when(op){
                Op.E,
                Op.S->{
                    currIdx[op.v1] + road[1].toInt()
                }
                Op.W,
                Op.N->{
                    currIdx[op.v1] - road[1].toInt()
                }
            }
            if(a in (0..parkArray.size-1)){
                if(parkArray[op.v1][a] != "X"){
                    parkArray[currIdx[0]][currIdx[1]] = "O"
                    parkArray[op.v1][a] = "S"
                    currIdx[0] = op.v1
                    currIdx[1] = a
                }
            }
        }

        return getStart(parkArray).toIntArray()
    }
}
fun main(){
    val v1 = arrayOf("OSO", "OOO", "OOO")
    val v2 = arrayOf("E 2", "S 2", "W 1")
    Solution().solution(v1, v2).forEach {
        print(it)
    }
}