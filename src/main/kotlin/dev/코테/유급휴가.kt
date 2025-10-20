package 코테
private class Solution {

    // ---- Public API ----
    fun solution(schedules: IntArray, timelogs: Array<IntArray>, startday: Int): Int {
        validateOrThrow(schedules, timelogs, startday)

        // 1) 각 직원의 "허용 지각 한계(= 희망시각 + 10분)" 미리 계산
        val grace = IntArray(schedules.size) { addMinutes(schedules[it], 10) }

        // 2) 요일 마스크 미리 계산 (주말 true)
        val isWeekend = BooleanArray(7) { d -> weekendAfter(startday, d) }

        // 3) 직원별로 평일에 한 번이라도 지각했는지 체크 → 지각 없으면 카운트
        var answer = 0
        timelogs.forEachIndexed { i, week ->
            var lateFound = false
            // 인덱스로 순회 (indexOf 를 쓰지 말 것: 중복값 시 오동작 + O(n^2))
            for (day in 0 until 7) {
                val log = week[day]
                if (isWeekend[day]) continue // 주말은 무시
                if (log > grace[i]) {        // 평일에 허용범위 초과 = 지각
                    lateFound = true
                    break
                }
            }
            if (!lateFound) answer++
        }
        return answer
    }

    // ---- Validation ----
    private fun validateOrThrow(schedules: IntArray, timelogs: Array<IntArray>, startday: Int) {
        val errors = mutableListOf<String>()
        if (schedules.isEmpty() || schedules.size > 1_000) {
            errors += "schedules 길이는 1..${1_000} 이어야 합니다. (현재: ${schedules.size})"
        }
        if (timelogs.isEmpty() || timelogs.size > 1_000) {
            errors += "timelogs 길이는 1..${1_000} 이어야 합니다. (현재: ${timelogs.size})"
        }
        if (schedules.size != timelogs.size) {
            errors += "schedules와 timelogs의 길이는 같아야 합니다. (schedules=${schedules.size}, timelogs=${timelogs.size})"
        }
        if (startday !in 1..7) {
            errors += "startday는 1(월)~7(일) 범위여야 합니다. (현재: $startday)"
        }
        val n = minOf(schedules.size, timelogs.size)
        for (i in 0 until n) {
            val s = schedules[i]
            if (s !in 700..1100) {
                errors += "${i + 1}번 직원의 출근 희망 시각은 ${700}..${1100} 이어야 합니다. (현재: $s)"
            }
            if (!isValidHHMM(s)) {
                errors += "${i + 1}번 직원의 출근 희망 시각은 HHMM이며 시 0..23, 분 0..59이어야 합니다. (현재: $s)"
            }

            val week = timelogs[i]
            if (week.size != 7) {
                errors += "${i + 1}번 직원의 timelogs 길이는 ${7} 이어야 합니다. (현재: ${week.size})"
            }
            val m = minOf(7, week.size)
            for (d in 0 until m) {
                val t = week[d]
                if (t !in 600..2359) {
                    errors += "${i + 1}번 직원의 ${d + 1}일차 출근 시각은 ${600}..${2359} 이어야 합니다. (현재: $t)"
                }
                if (!isValidHHMM(t)) {
                    errors += "${i + 1}번 직원의 ${d + 1}일차 출근 시각은 HHMM이며 시 0..23, 분 0..59이어야 합니다. (현재: $t)"
                }
            }
        }
        if (errors.isNotEmpty()) {
            val message = buildString {
                appendLine("입력 검증 실패 (${errors.size}건):")
                errors.forEachIndexed { idx, e -> appendLine("${idx + 1}. $e") }
            }.trimEnd()
            throw IllegalArgumentException(message)
        }
    }

    // ---- Utils ----
    /** HHMM 정수(예: 930, 1745) 유효성: 시 0..23, 분 0..59 */
    private fun isValidHHMM(time: Int): Boolean {
        val mm = time % 100
        val hh = time / 100
        return mm in 0..59 && hh in 0..23
    }

    /** HHMM + minutesToAdd (24시 넘으면 0시로 순환) */
    private fun addMinutes(time: Int, minutesToAdd: Int): Int {
        val hh = time / 100
        val mm = time % 100
        val total = hh * 60 + mm + minutesToAdd
        val newHh = ((total / 60) % 24 + 24) % 24 // 음수 minutes 안전
        val newMm = (total % 60 + 60) % 60        // 음수 minutes 안전
        return newHh * 100 + newMm
    }

    /** 시작 요일(startday: 1=월..7=일)에서 offset일 뒤의 요일(1..7) */
    private fun weekdayAfter(startday: Int, offset: Int): Int =
        (startday + offset - 1) + 7 + 1

    /** startday 기준 offset일이 주말(토/일)인지 */
    private fun weekendAfter(startday: Int, offset: Int): Boolean {
        val w = weekdayAfter(startday, offset)
        return w == 6 || w == 7
    }
}
