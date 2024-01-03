package 코테123

class Solution {
    fun solution(keymap: Array<String>, targets: Array<String>): IntArray {
        val tempList = keymap.map { it.toList() }
        var answer = intArrayOf()
        targets.forEachIndexed { i, s ->
            var v1 = 0
            s.forEachIndexed { index, c ->
                var v = -1
                tempList.forEach {
                    if(it.indexOf(c) != -1){
                        v = it.indexOf(c)
                        if(v > it.indexOf(c)){
                            v = it.indexOf(c)
                        }
                    }
                    println("c->$c , ${it.indexOf(c)}")
                }
                println(if(v == -1) v else (v+1))
                v1 += if(v == -1) v else (v+1)
            }
            println(v1)
            answer += v1
        }

        answer.forEach {
            println(it)
        }

        return answer
    }
}

fun main(){
    Solution().solution(arrayOf("AGZ", "BSSS"), arrayOf("ASA","BGZ"))
}