package 코테1

import java.util.PriorityQueue


fun main() {
    val product = listOf(30000, 2000, 1500000)
    val sale = listOf(20, 40)

    val sortProductList = product.sortedDescending()
    val sortSaleList = sale.sortedDescending()

    //최대 할인받은 가격
    var totalPrice = 0
    for(i in sortSaleList.indices){
        val discountPrice = sortProductList[i] * (100 - sortSaleList[i]) / 100
        totalPrice += discountPrice
    }

    // 할인 못받은 가격
    for(i in sortSaleList.size until sortProductList.size){
        totalPrice += sortProductList[i]
    }
    println(totalPrice)

    val deque = ArrayDeque<Boolean>()

    val string = ")"

    var result = true
    for (char in string.trim()) {
        when (char) {
            '(' -> deque.addLast(true)
            ')' -> {
                if(deque.isNotEmpty()){
                    deque.removeLast()
                }
            }
        }
    }

    if(deque.isEmpty()){
        result = true
    }else{
        result = false
    }

    println(result)


    //베스트 앨법 뽑기
    val category = arrayOf("classic", "pop", "classic", "classic", "pop")
    val plays = intArrayOf(500, 600, 150, 800, 2500)

    category.zip(plays.asList()).groupBy { it.first }.mapValues {
        it.value.sumOf { pair -> pair.second }
    }.toList().sortedByDescending { it.second }.map { it.first }.forEach { genre ->
        category.zip(plays.asList()).withIndex().filter { it.value.first == genre }
            .sortedWith(compareByDescending<IndexedValue<Pair<String, Int>>> { it.value.second }
                .thenBy { it.index })
            .forEach {
                println(it.index)
            }
    }

    val maxheap = PriorityQueue<Int>()
}
