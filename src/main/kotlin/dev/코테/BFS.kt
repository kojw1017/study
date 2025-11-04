package 코테12

fun dfsRecursive(node: Int, graph: Map<Int, List<Int>>, visited: MutableSet<Int>) {
    // 1️⃣ 방문 표시
    if (node in visited) return
    visited.add(node)

    // 2️⃣ 현재 노드 출력 (혹은 원하는 작업 수행)
    println("Visit $node")

    // 3️⃣ 자식 노드(이웃 노드) 탐색
    for (next in graph[node] ?: emptyList()) {
        dfsRecursive(next, graph, visited)
    }
}

fun dfsIterative(start: Int, graph: Map<Int, List<Int>>) {
    val visited = mutableSetOf<Int>()
    val stack = ArrayDeque<Int>() // 스택 역할
    stack.add(start)

    while (stack.isNotEmpty()) {
        val node = stack.removeLast() // pop
        if (node in visited) continue

        visited.add(node)
        println("Visit $node")

        // 이웃을 역순으로 넣어야 "작은 노드부터" 탐색 가능
        val neighbors = graph[node] ?: emptyList()
        for (next in neighbors.reversed()) {
            if (next !in visited) {
                stack.add(next)
            }
        }
    }
}


fun main() {
    val graph = mapOf(
        1 to listOf(2, 3),
        2 to listOf(4, 5),
        3 to listOf(),
        4 to listOf(),
        5 to listOf()
    )
    val visited = mutableSetOf<Int>()
    dfsRecursive(1, graph, visited)
}

