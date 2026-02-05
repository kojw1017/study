package org.example.dev.놀이터
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val mode = args.firstOrNull() ?: "bounded" // alloc | leak | bounded

    // STW 체감(200ms 이상만 표시)
    val last = AtomicLong(System.nanoTime())
    thread(name = "heartbeat", isDaemon = true) {
        while (true) {
            val now = System.nanoTime()
            val gapMs = (now - last.getAndSet(now)) / 1_000_000
            if (gapMs > 200) println("[HB] STW? gap=${gapMs}ms at ${Instant.now()}")
            Thread.sleep(50)
        }
    }

    // 메모리 사용량 1초마다
    thread(name = "mem", isDaemon = true) {
        while (true) {
            val rt = Runtime.getRuntime()
            val usedMb = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024)
            println("[MEM] used=${usedMb}MB at ${Instant.now()}")
            Thread.sleep(1000)
        }
    }

    when (mode) {
        "alloc" -> allocOnlyShortLived()
        "leak" -> leakUnbounded()
        "bounded" -> boundedCache(maxEntries = 120) // 여기 숫자만 바꾸면 됨
        else -> println("Usage: alloc | leak | bounded")
    }
}

/** 단명 객체만 생성(청소 잘 되는 케이스) */
fun allocOnlyShortLived() {
    println("=== allocOnlyShortLived ===")
    var i = 0
    while (true) {
        val trash = ByteArray(512 * 1024)
        trash[0] = 1
        i++
        if (i % 200 == 0) println("[APP] i=$i")
        Thread.sleep(5)
    }
}

/** 무제한 캐시(=누수처럼 동작, OOM 쉽게 남) */
fun leakUnbounded() {
    println("=== leakUnbounded ===")
    val cache = HashMap<Int, ByteArray>()
    var key = 0
    while (true) {
        cache[key++] = ByteArray(512 * 1024)
        if (key % 50 == 0) println("[APP] cacheSize=${cache.size}")
        Thread.sleep(20)
    }
}

/** ✅ 해결 버전: LRU + 최대 엔트리 수 제한 */
fun boundedCache(maxEntries: Int) {
    println("=== boundedCache (LRU, maxEntries=$maxEntries) ===")
    val cache = LruCache<Int, ByteArray>(maxEntries)
    var key = 0

    while (true) {
        cache[key++] = ByteArray(512 * 1024)

        if (key % 50 == 0) {
            println("[APP] cacheSize=${cache.size} evictions=${cache.evictions}")
        }
        Thread.sleep(20)
    }
}

/** LRU 캐시: 오래 안 쓴 항목부터 제거 + 최대 개수 제한 */
class LruCache<K, V>(private val maxEntries: Int) : LinkedHashMap<K, V>(maxEntries + 1, 0.75f, true) {
    var evictions: Long = 0

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        val shouldEvict = size > maxEntries
        if (shouldEvict) evictions++
        return shouldEvict
    }
}
