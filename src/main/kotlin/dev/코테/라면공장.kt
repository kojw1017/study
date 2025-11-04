package 코테12

import java.util.PriorityQueue

fun getMinimumCountOfOverseasSupply(
    stock: Int,
    dates: IntArray,
    supplies: IntArray,
    k: Int
): Int {
    var day = stock                  // 현재 재고로 버틸 수 있는 마지막 날
    var i = 0                        // dates/supplies 인덱스
    var answer = 0
    val maxHeap = PriorityQueue<Int>(compareByDescending { it }) // 최대 힙

    println("초기 상태 → stock=$stock, k=$k, dates=${dates.toList()}, supplies=${supplies.toList()}")
    println("==========================================")

    // 이미 충분히 버틸 수 있으면 0
    if (day >= k) return 0

    while (day < k) {
        // 현재 day까지 도달 가능한 공급을 모두 힙에 넣기
        while (i < dates.size && dates[i] <= day) {
            println("➡️  ${dates[i]}일차 공급 (${supplies[i]}톤) 도달 가능 → 힙에 추가")
            maxHeap.add(supplies[i])
            i++
        }

        println("📦 현재 힙 상태 = ${maxHeap.toList()} (가장 큰 공급량이 위)")

        if (maxHeap.isEmpty()) {
            println("❌ 더 이상 받을 수 있는 공급 없음 (day=$day)")
            return -1
        }

        // 가장 큰 공급을 꺼내 받기
        val chosen = maxHeap.poll()
        day += chosen
        answer++

        println("✅ ${answer}번째 공급: ${chosen}톤 받음 → day=$day 까지 버틸 수 있음")
        println("------------------------------------------")
    }

    println("🎉 목표 도달! (day=$day, k=$k, 총 ${answer}회 공급)")
    return answer
}

fun main() {
    val ramenStock = 4
    val supplyDates = intArrayOf(4, 10, 15)
    val supplySupplies = intArrayOf(20, 5, 10)
    val k = 30

    println(getMinimumCountOfOverseasSupply(ramenStock, supplyDates, supplySupplies, k))

    // 원문 테스트 케이스들
    println(
        "정답 = 2 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(4, intArrayOf(4, 10, 15), intArrayOf(20, 5, 10), 30)
    )
    println(
        "정답 = 4 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(4, intArrayOf(4, 10, 15, 20), intArrayOf(20, 5, 10, 5), 40)
    )
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(2, intArrayOf(1, 10), intArrayOf(10, 100), 11)
    )

    // 경계값 테스트 케이스들
    // 1. stock = k (이미 충분한 경우)
    println(
        "정답 = 0 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(10, intArrayOf(5), intArrayOf(20), 10)
    )

    // 2. stock = 0 (재고 완전 바닥)
    println(
        "정답 = 2 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(0, intArrayOf(0, 10, 15), intArrayOf(20, 10, 15), 35)
    )

    // 3. 딱 한 번만 공급받으면 되는 경우
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(5, intArrayOf(5), intArrayOf(30), 30)
    )

    // 4. 공급 후 stock이 정확히 k가 되는 경우
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(10, intArrayOf(10), intArrayOf(20), 30)
    )

    // 5. 첫날부터 공급 가능한 경우
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(0, intArrayOf(0), intArrayOf(100), 50)
    )

    // 6. k = 1 (최소 기간)
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(0, intArrayOf(0), intArrayOf(10), 1)
    )

    // 7. 여러 번 공급받아야 하고 딱 맞아떨어지는 경우
    println(
        "정답 = 3 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(0, intArrayOf(0, 5, 10), intArrayOf(5, 5, 5), 15)
    )

    // 8. 공급 가능 날짜가 여러 개지만 하나만 선택해야 하는 경우
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(5, intArrayOf(5, 6, 7), intArrayOf(100, 10, 10), 50)
    )

    // 9. 마지막 날에 공급받는 경우
    println(
        "정답 = 2 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(10, intArrayOf(10, 29), intArrayOf(20, 100), 30)
    )

    // 10. stock이 k보다 1 작은 경우
    println(
        "정답 = 1 / 현재 풀이 값 = " +
                getMinimumCountOfOverseasSupply(29, intArrayOf(29), intArrayOf(100), 30)
    )
}
