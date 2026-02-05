package org.example.dev.놀이터

// 데이터의 버전을 관리하는 클래스
data class RowVersion(
    val id: Long,
    val value: String,
    val version: Int // 데이터의 버전 (시간의 흐름)
)

class MVCCDatabase {
    // 실제 데이터 저장소 (하나의 ID에 여러 버전을 리스트로 가짐)
    // 예: 1번 데이터 -> [버전1, 버전2, 버전3]
    private val storage = mutableMapOf<Long, MutableList<RowVersion>>()

    init {
        // 초기 데이터: ID 1, 값 "Original", 버전 1
        storage[1L] = mutableListOf(RowVersion(1, "Original", 1))
    }

    // 읽기 (Read): 트랜잭션이 시작된 시점(readVersion)보다 작거나 같은 최신 버전을 가져옴
    fun read(id: Long, transactionVersion: Int): String {
        val versions = storage[id] ?: return "Not Found"

        // 내 트랜잭션 버전보다 낮은 것 중 가장 최신 데이터를 찾음 (Snapshot Read)
        val visibleData = versions
            .filter { it.version <= transactionVersion }
            .maxByOrNull { it.version }

        return visibleData?.value ?: "No Visible Data"
    }

    // 쓰기 (Write): 기존 데이터를 덮어쓰지 않고, 새로운 버전을 추가함
    fun update(id: Long, newValue: String, newVersion: Int) {
        val versions = storage[id] ?: return
        // 기존 데이터를 지우지 않음! 새 버전을 리스트에 추가 (Append)
        versions.add(RowVersion(id, newValue, newVersion))
        println("Update: [ID: $id] -> '$newValue' (Version $newVersion) created.")
    }
}

fun main() {
    val db = MVCCDatabase()

    // 상황 1: 사용자 A가 트랜잭션 시작 (버전 10 시점)
    val userATrxVersion = 10
    println("User A reads: ${db.read(1L, userATrxVersion)}")
    // 출력: Original (버전 1)

    // 상황 2: 사용자 B가 들어와서 데이터를 수정함 (버전 11 생성)
    // *중요*: A가 읽고 있는데도 B는 대기하지 않고 업데이트 성공!
    db.update(1L, "Updated Value", 11)

    // 상황 3: 사용자 A가 다시 조회함 (여전히 자신의 트랜잭션 버전 10 기준)
    println("User A reads again: ${db.read(1L, userATrxVersion)}")
    // 출력: Original (여전히 버전 1이 보임! 격리성 유지)

    // 상황 4: 새로운 사용자 C가 들어옴 (버전 12 시점)
    val userCTrxVersion = 12
    println("User C reads: ${db.read(1L, userCTrxVersion)}")
    // 출력: Updated Value (버전 11이 보임)
}