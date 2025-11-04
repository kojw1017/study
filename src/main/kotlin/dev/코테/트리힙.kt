package 코테1

/**
 * BinaryHeap<T>
 * - 내부 저장: 배열(여기선 MutableList<T>)로 완전 이진 트리 표현
 * - comparator: 최소 힙/최대 힙을 선택할 수 있도록 외부에서 주입
 *
 * 인덱스 규칙(0-based):
 *  parent(i) = (i - 1) / 2
 *  left(i)   = 2*i + 1
 *  right(i)  = 2*i + 2
 */
class BinaryHeap<T>(
    private val comparator: Comparator<T>,
    elements: Collection<T> = emptyList()
) {
    private val a: MutableList<T> = elements.toMutableList()

    init {
        // O(n) build-heap: 마지막 내부 노드부터 내려가며 heapify
        for (i in parentIndex(a.lastIndex) downTo 0) {
            siftDown(i)
        }
    }

    val size: Int get() = a.size
    fun isEmpty(): Boolean = a.isEmpty()
    fun clear() = a.clear()

    fun peek(): T? = a.firstOrNull()

    /** push */
    fun add(x: T) {
        a.add(x)
        siftUp(a.lastIndex)
    }

    /** pop */
    fun poll(): T? {
        if (a.isEmpty()) return null
        val top = a.first()
        val last = a.removeAt(a.lastIndex)
        if (a.isNotEmpty()) {
            a[0] = last
            siftDown(0)
        }
        return top
    }

    /** 힙 내 임의 원소 제거(선택사항) */
    fun removeAt(index: Int): T? {
        if (index !in a.indices) return null
        val last = a.removeAt(a.lastIndex)
        return if (index == a.size) {
            // 방금 제거한 게 마지막 원소였던 경우
            last
        } else {
            val removed = a[index]
            a[index] = last
            // 복원: 위로/아래로 둘 다 가능성이 있어 둘 다 시도
            if (!siftDown(index)) {
                siftUp(index)
            }
            removed
        }
    }

    /** 디버깅/검증용: 현재 배열 복사본 */
    fun toList(): List<T> = a.toList()

    // --- Private helpers ---

    private fun parentIndex(i: Int): Int = (i - 1) / 2
    private fun leftIndex(i: Int): Int = 2 * i + 1
    private fun rightIndex(i: Int): Int = 2 * i + 2

    /** 위로 끌어올리기: O(log n) */
    private fun siftUp(start: Int) {
        var i = start
        while (i > 0) {
            val p = parentIndex(i)
            if (comparator.compare(a[i], a[p]) < 0) {
                swap(i, p)
                i = p
            } else break
        }
    }

    /**
     * 아래로 내려보내기: O(log n)
     * @return 움직였는지 여부
     */
    private fun siftDown(start: Int): Boolean {
        var i = start
        var moved = false
        while (true) {
            val l = leftIndex(i)
            val r = rightIndex(i)
            var smallest = i

            if (l < a.size && comparator.compare(a[l], a[smallest]) < 0) smallest = l
            if (r < a.size && comparator.compare(a[r], a[smallest]) < 0) smallest = r

            if (smallest != i) {
                swap(i, smallest)
                i = smallest
                moved = true
            } else break
        }
        return moved
    }

    private fun swap(i: Int, j: Int) {
        val t = a[i]
        a[i] = a[j]
        a[j] = t
    }
}

// ---- 사용 예시 ----
fun main() {
    // 1) 최소 힙: 작은 값이 먼저 나온다
    val minHeap = BinaryHeap(Comparator.naturalOrder<Int>())
    listOf(5, 1, 7, 3, 9, 2).forEach { minHeap.add(it) }
    println("Min-heap peek: ${minHeap.peek()}") // 1
    while (!minHeap.isEmpty()) {
        print("${minHeap.poll()} ")            // 1 2 3 5 7 9
    }
    println()

    // 2) 최대 힙: 큰 값이 먼저 나오도록 역순 comparator
    val maxHeap = BinaryHeap(Comparator.reverseOrder<Int>())
    listOf(5, 1, 7, 3, 9, 2).forEach { maxHeap.add(it) }
    println("Max-heap peek: ${maxHeap.peek()}") // 9
    while (!maxHeap.isEmpty()) {
        print("${maxHeap.poll()} ")            // 9 7 5 3 2 1
    }
    println()

    // 3) 초기 컬렉션으로 O(n) 빌드-힙 (성능상 add 반복보다 유리)
    val built = BinaryHeap(Comparator.naturalOrder<Int>(), listOf(8, 4, 6, 2, 10, 3))
    println("Built heap as array: ${built.toList()}")
    while (!built.isEmpty()) {
        print("${built.poll()} ")              // 2 3 4 6 8 10
    }
    println()

    // 4) 문자열도 가능 (길이 기준 최소 힙)
    val byLength = BinaryHeap(Comparator.comparingInt<String> { it.length })
    listOf("tree", "heap", "kotlin", "ai").forEach { byLength.add(it) }
    while (!byLength.isEmpty()) {
        print("${byLength.poll()} ")           // "ai" "heap" "tree" "kotlin"
    }
    println()
}
