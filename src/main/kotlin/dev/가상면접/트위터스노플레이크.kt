package org.example.dev.가상면접
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Twitter-style Snowflake ID generator (64-bit signed long)
 *
 * Layout (most common):
 *  - 1 bit:  sign (always 0)
 *  - 41 bit: timestamp (ms since custom epoch)
 *  - 10 bit: workerId (0..1023)
 *  - 12 bit: sequence (0..4095)
 */
class Snowflake(
    private val workerId: Long,
    private val epochMs: Long = 1288834974657L // Twitter epoch commonly used
) {
    companion object {
        private const val WORKER_ID_BITS = 10
        private const val SEQUENCE_BITS = 12

        private const val MAX_WORKER_ID = (1L shl WORKER_ID_BITS) - 1 // 1023
        private const val MAX_SEQUENCE = (1L shl SEQUENCE_BITS) - 1   // 4095

        private const val WORKER_ID_SHIFT = SEQUENCE_BITS
        private const val TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS
    }

    private var lastTimestamp = -1L
    private var sequence = 0L

    init {
        require(workerId in 0..MAX_WORKER_ID) {
            "workerId must be between 0 and $MAX_WORKER_ID (given: $workerId)"
        }
    }

    /**
     * Thread-safe ID generation.
     */
    @Synchronized
    fun nextId(): Long {
        var now = currentTimeMs()

        // clock moved backwards: safest is to fail fast (or wait). 여기선 fail fast.
        if (now < lastTimestamp) {
            val diff = lastTimestamp - now
            throw IllegalStateException("Clock moved backwards by ${diff}ms. Refusing to generate id.")
        }

        if (now == lastTimestamp) {
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                // sequence overflow in the same millisecond: wait next ms
                now = waitNextMillis(now)
            }
        } else {
            sequence = 0L
        }

        lastTimestamp = now
        val timestampPart = now - epochMs

        return (timestampPart shl TIMESTAMP_SHIFT) or
                (workerId shl WORKER_ID_SHIFT) or
                sequence
    }

    fun decode(id: Long): Decoded {
        val sequence = id and MAX_SEQUENCE
        val worker = (id shr WORKER_ID_SHIFT) and MAX_WORKER_ID
        val timestampPart = id shr TIMESTAMP_SHIFT
        val createdAtMs = timestampPart + epochMs
        return Decoded(
            id = id,
            createdAtMs = createdAtMs,
            workerId = worker,
            sequence = sequence
        )
    }

    data class Decoded(
        val id: Long,
        val createdAtMs: Long,
        val workerId: Long,
        val sequence: Long
    )

    private fun currentTimeMs(): Long = System.currentTimeMillis()

    private fun waitNextMillis(current: Long): Long {
        var now = current
        while (now == lastTimestamp) {
            now = currentTimeMs()
        }
        return now
    }
}

/**
 * 실습용 main
 */
fun main() {
    val gen = Snowflake(workerId = 42L)

    // 1) ID 5개 생성
    val ids = List(5) { gen.nextId() }
    println("Generated IDs:")
    ids.forEach { println(it) }

    // 2) 디코딩해서 의미 확인
    println("\nDecoded:")
    val fmt = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
    ids.forEach { id ->
        val d = gen.decode(id)
        val timeStr = fmt.format(Instant.ofEpochMilli(d.createdAtMs))
        println("id=$id  time=$timeStr  worker=${d.workerId}  seq=${d.sequence}")
    }

    // 3) 같은 밀리초에 많이 만들면 seq가 올라가는지 확인 (간단 burst)
    println("\nBurst test (20 ids):")
    repeat(20) {
        val id = gen.nextId()
        val d = gen.decode(id)
        println("worker=${d.workerId} seq=${d.sequence} id=$id")
    }

    // 4) JSON 직렬화 팁: JS 안전정수(53비트) 문제 때문에 문자열 권장
    println("\nTip: When sending via JSON to JS, prefer id as String (e.g., \"id_str\").")
}
