package 코테1233

class Solution {
    // String을 "mm:ss" 형식에서 초 단위로 변환하는 확장 함수
private fun String.toSeconds(): Int = split(":").let { (min, sec) -> min.toInt() * 60 + sec.toInt() }
// 시간 형식이 "mm:ss"인지 확인하는 함수
private fun isValidTimeFormat(time: String): Boolean = Regex("^[0-5][0-9]:[0-5][0-9]$").matches(time)
// 시간 형식이 올바른지 검증하는 함수
private fun validateTimeFormat(vararg times: String) {
    times.forEach { require(it.length == 5 && isValidTimeFormat(it)) { "시간 형식이 잘못되었습니다." } }
}
// 비디오 재생 시간과 오프닝 종료 시간이 비디오 길이 내에 있는지 검증
private fun validateTimeRange(time: String, opEnd: String, videoLen: String) {
    val videoLenSec = videoLen.toSeconds()
    require(time.toSeconds() <= videoLenSec && opEnd.toSeconds() <= videoLenSec) {
        "비디오의 현재 위치 혹은 오프닝이 끝나는 시각이 동영상의 범위 밖인 경우는 주어지지 않습니다."
    }
}
// 오프닝 시작 시간이 오프닝 종료 시간보다 이전인지 검증
private fun validateOpeningTime(opStart: String, opEnd: String) {
    require(opStart.toSeconds() <= opEnd.toSeconds()) { "오프닝이 시작하는 시각은 항상 오프닝이 끝나는 시각보다 전입니다." }
}
// commands 배열에 'prev'와 'next'만 포함되었는지 검증
private fun validateCommands(commands: Array<String>) {
    require(commands.all { it == "prev" || it == "next" }) { "commands 배열에는 'prev'와 'next'만 포함됩니다." }
}
    // 최종 솔루션 함수
    fun solution(videoLen: String, pos: String, opStart: String, opEnd: String, commands: Array<String>): String {
        // 입력 형식과 조건을 검증
        validateTimeFormat(videoLen, pos, opStart, opEnd)
        validateTimeRange(pos, opEnd, videoLen)
        validateOpeningTime(opStart, opEnd)
        validateCommands(commands)
        // 시간을 초 단위로 변환
        val videoLenSec = videoLen.toSeconds()
        val opStartSec = opStart.toSeconds()
        val opEndSec = opEnd.toSeconds()
        val posSec = pos.toSeconds()
        // 오프닝 구간에 있는지 확인하여 초기 재생 위치 설정
        var currentSec = if(posSec in opStartSec..opEndSec) opEndSec else posSec
        // 명령어 처리
        commands.forEach {
            when (it) {
                // 10초 전으로 이동
                "prev" -> currentSec = (currentSec - 10).coerceAtLeast(0)
                // 10초 후로 이동
                "next" -> currentSec = (currentSec + 10).coerceAtMost(videoLenSec)
            }
            // 오프닝 구간을 벗어나는지 확인
            if (currentSec in opStartSec..opEndSec) currentSec = opEndSec
        }
        // 결과를 "mm:ss" 형식으로 반환
        return "%02d:%02d".format(currentSec / 60, currentSec % 60)
    }
}
fun main() {
    val videoLen = "07:22"
    val pos = "04:05"
    val opStart = "00:15"
    val opEnd = "04:17"
    val commands = arrayOf("prev", "next")
    val result = Solution().solution(videoLen, pos, opStart, opEnd, commands)
    println(result)
}
