package ㄱ

fun solution(lottos: IntArray, win_nums: IntArray): MutableList<Int> {
    return mutableListOf(
        (lottos.size.plus(1)) - lottos.filter { win_nums.contains(it) || it == 0 }.size ,
        (lottos.size.plus(1)) - lottos.filter { win_nums.contains(it)}.size
    ).map { if(it>6) it-1 else it }.toMutableList()
}

fun main(){
    solution(intArrayOf(44, 1, 0, 0, 31, 25), intArrayOf(31, 10, 45, 1, 6, 19))
}