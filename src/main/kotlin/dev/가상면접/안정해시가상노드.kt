import java.util.SortedMap
import java.util.TreeMap
import java.security.MessageDigest
import java.math.BigInteger

// 해시 함수 (SHA-256 사용)
fun sha256(input: String): BigInteger {
    val md = MessageDigest.getInstance("SHA-256")
    val bytes = md.digest(input.toByteArray(Charsets.UTF_8))
    return BigInteger(1, bytes)
}

class ConsistentHash<T>(
    private val numberOfReplicas: Int, // 가상 노드 수
    private val nodes: MutableList<T> = mutableListOf() // 서버 목록
) {

    private val circle: SortedMap<BigInteger, T> = TreeMap() // 해시 링

    init {
        nodes.forEach { add(it) }
    }

    // 서버 추가
    fun add(node: T) {
        for (i in 0 until numberOfReplicas) {
            val key = sha256("$node-$i")
            circle[key] = node
        }
    }

    // 서버 삭제
    fun remove(node: T) {
        for (i in 0 until numberOfReplicas) {
            val key = sha256("$node-$i")
            circle.remove(key)
        }
    }

    // 키에 해당하는 서버 찾기
    fun get(key: String): T? {
        if (circle.isEmpty()) {
            return null
        }
        val hash = sha256(key)
        val tailMap = circle.tailMap(hash)
        val nodeKey = if (tailMap.isEmpty()) {
            circle.firstKey()
        } else {
            tailMap.firstKey()
        }
        return circle[nodeKey]
    }

    fun getAllNodes() : List<T> {
        return nodes.toList()
    }
}

fun main() {
    // 서버 목록
    val servers = listOf("server1", "server2", "server3")

    // 가상 노드 수를 3으로 설정
    val consistentHash = ConsistentHash<String>(3, servers.toMutableList())

    // 데이터 분배 테스트
    val dataKeys = listOf("key1", "key2", "key3", "key4", "key5")
    dataKeys.forEach { key ->
        val server = consistentHash.get(key)
        println("Key '$key' is assigned to server: $server")
    }

    // 서버 삭제 테스트
    println("\nRemoving server2...")
    consistentHash.remove("server2")

    dataKeys.forEach { key ->
        val server = consistentHash.get(key)
        println("Key '$key' is now assigned to server: $server")
    }

    // 서버 추가 테스트
    println("\nAdding server4...")
    consistentHash.add("server4")

    dataKeys.forEach { key ->
        val server = consistentHash.get(key)
        println("Key '$key' is now assigned to server: $server")
    }
}