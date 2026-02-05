package org.example.dev.가상면접

interface RateLimiter {
    fun allowRequest(): Boolean
}
class TokenBucketRateLimiter(
    private val capacity: Long,              // 버킷 최대 토큰 수
    private val refillTokens: Long,          // 한 번에 채울 토큰 수
    private val refillIntervalMillis: Long,  // 채우는 주기
    private val clock: () -> Long = { System.currentTimeMillis() }
) : RateLimiter {

    @Volatile
    private var availableTokens: Long = capacity

    @Volatile
    private var lastRefillTimestamp: Long = clock()

    @Synchronized
    override fun allowRequest(): Boolean {
        refillIfNeeded()
        return if (availableTokens > 0) {
            availableTokens--
            true
        } else {
            false
        }
    }

    private fun refillIfNeeded() {
        val now = clock() // 토큰 리필이 필요한지 확인
        if (now <= lastRefillTimestamp) return // 시간이 아직 안 지났으면 리턴

        val elapsed = now - lastRefillTimestamp // 경과 시간 계산
        val intervals = elapsed / refillIntervalMillis // 리필 주기 수 계산
        if (intervals <= 0) return // 리필 주기가 아직 안 지났으면 리턴

        val tokensToAdd = intervals * refillTokens // 추가할 토큰 수 계산
        availableTokens = (availableTokens + tokensToAdd).coerceAtMost(capacity) // 토큰 추가 및 최대 용량 제한
        lastRefillTimestamp += intervals * refillIntervalMillis // 마지막 리필 시간 갱신
    }
}

fun main() {
    val rateLimiter = TokenBucketRateLimiter(
        capacity = 5, // 최대 5개의 토큰
        refillTokens = 1, // 1초마다 1개의 토큰 추가
        refillIntervalMillis = 1000 // 1초
    )

    // 테스트: 10개의 요청을 빠르게 시도
    for (i in 1..20) {
        if (rateLimiter.allowRequest()) {
            println("Request $i: Allowed")
        } else {
            println("Request $i: Denied")
        }
        Thread.sleep(200) // 200ms 간격으로 요청 시도
    }
}
