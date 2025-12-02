import java.util.StringTokenizer

private data class Pos(val row: Int, val col: Int)

fun main() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())

    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val houses = mutableListOf<Pos>()
    val chickens = mutableListOf<Pos>()

    for (i in 0 until n) {
        st = StringTokenizer(br.readLine())
        for (j in 0 until n) {
            val value = st.nextToken().toInt()
            when (value) {
                1 -> houses.add(Pos(i, j))
                2 -> chickens.add(Pos(i, j))
            }
        }
    }

    val selectedChickens = IntArray(m)
    var answer = Int.MAX_VALUE

    fun recursive(depth: Int, start: Int) {
        if (depth == m) {
            println("depth: $depth, start: $start")
            println("Selected chickens: ${selectedChickens.joinToString()}")
            var totalDistance = 0
            for (house in houses) {
                var minDistance = Int.MAX_VALUE
                for(i in selectedChickens){
                    val chicken = chickens[i]
                    val dist = kotlin.math.abs(house.row - chicken.row) + kotlin.math.abs(house.col - chicken.col)
                    minDistance = kotlin.math.min(minDistance, dist)
                }
                totalDistance += minDistance
                println("House at (${house.row}, ${house.col}) minDistance: $minDistance, totalDistance so far: $totalDistance")
            }
            answer = kotlin.math.min(answer, totalDistance)
            println("Current answer: $answer")
            return
        }

        for (i in start until chickens.size) {
            println("depth: $depth, selected: $i")
            selectedChickens[depth] = i
            recursive(depth + 1, i + 1)
        }
    }

    recursive(0, 0)
    println(answer)
}
