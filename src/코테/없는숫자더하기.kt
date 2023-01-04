package ㄴ
fun solution(participant: MutableList<String>, completion: MutableList<String>): String {
    var map = participant.groupingBy { it }.eachCount()
    participant.filterNot {
        println(map)
        if(completion.contains(it)) map[it]?.minus(1)
        completion.contains(it)
    }.map {
        it
    }

    map.forEach {
        println(it.key)
        println(it.value)
    }

    return ""
}

fun main(){
    println(solution(mutableListOf("leo", "leo","kiki", "eden"), mutableListOf("eden", "kiki", "leo") ))
}