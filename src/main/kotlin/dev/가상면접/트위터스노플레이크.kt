package org.example.dev.가상면접

import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

class SnowflakeIdGenerator(
    private val datacenterId: Long,
    private val workerId: Long,
    private val epoch: Long = 1288834974657L // 트위터 Snowflake 에포크 (2010-11-04T01:42:54.657Z)
) {

    init {
        require(datacenterId in 0..31) { "datacenterId must be between 0 and 31" }
        require(workerId in 0..31) { "workerId must be between 0 and 31" }
    }

    private val sequenceBits = 12
    private val workerIdBits = 5
    private val datacenterIdBits = 5
    private val maxSequence = (1 shl sequenceBits) - 1
    private val workerIdShift = sequenceBits
    private val datacenterIdShift = sequenceBits + workerIdBits
    private val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits

    private var lastTimestamp = -1L
    private val sequence = AtomicLong(0) // 동시성 문제를 위한 AtomicLong

    @Synchronized
    fun nextId(): Long {
        var timestamp = timeGen()

        // 현재 시간이 이전 타임스탬프보다 작으면 (시스템 시계가 뒤로 감김) 예외 발생
        if (timestamp < lastTimestamp) {
            throw RuntimeException(
                "Clock moved backwards. Refusing to generate id for ${lastTimestamp - timestamp} milliseconds"
            )
        }

        // 같은 밀리초 내에서 생성되는 경우, 시퀀스 번호 증가
        if (lastTimestamp == timestamp) {
            sequence.set((sequence.get() + 1) and maxSequence.toLong())
            // 시퀀스 번호가 최대값에 도달하면, 다음 밀리초까지 대기
            if (sequence.get() == 0L) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else { // 새로운 밀리초인 경우, 시퀀스 번호 초기화
            sequence.set(0)
        }

        lastTimestamp = timestamp

        return ((timestamp - epoch) shl timestampLeftShift) or
                (datacenterId shl datacenterIdShift) or
                (workerId shl workerIdShift) or
                sequence.get()
    }

    // 다음 밀리초까지 대기
    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen()
        }
        return timestamp
    }

    // 현재 시간 (밀리초)
    private fun timeGen(): Long {
        return Instant.now().toEpochMilli()
    }
}

fun main() {
    val generator = SnowflakeIdGenerator(datacenterId = 1, workerId = 1)

    // ID 10개 생성
    for (i in 0..9) {
        val id = generator.nextId()
        println("Generated ID: $id")
    }
}