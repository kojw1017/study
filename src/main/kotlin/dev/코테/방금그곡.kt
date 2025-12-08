package org.example.dev.코테


fun main() {
    val m = "ABCDEFG"
    val musicInfos = arrayOf(
        "12:00,12:14,HELLO,CDEFGAB",
        "13:00,13:05,WORLD,ABCDEF"
    )
    println(solution(m, musicInfos))
}

// 샾(#) 음을 한 글자로 정규화
private fun String.normalizeMelody(): String =
    this
        .replace("A#", "a")
        .replace("B#", "b")
        .replace("C#", "c")
        .replace("D#", "d")
        .replace("E#", "e")
        .replace("F#", "f")
        .replace("G#", "g")

private data class MelodyInfo(
    val playTime: Int,
    val title: String,
    val fullMelody: String,
    val order: Int      // 입력 순서 (동점일 때 우선순위)
) {
    companion object {
        // "HH:MM" -> 총 분으로 변환
        private fun getPlayTime(start: String, end: String): Int {
            val (sh, sm) = start.split(":").map { it.toInt() }
            val (eh, em) = end.split(":").map { it.toInt() }
            return (eh * 60 + em) - (sh * 60 + sm)
        }

        // 재생 시간만큼 멜로디를 반복해서 전체 재생 멜로디 생성
        private fun getFullMelody(melody: String, playTime: Int): String {
            val full = StringBuilder(playTime)
            val len = melody.length
            for (i in 0 until playTime) {
                full.append(melody[i % len])
            }
            return full.toString()
        }

        fun from(musicInfo: String, index: Int): MelodyInfo {
            val (start, end, title, melody) = musicInfo.split(",")
            val playTime = getPlayTime(start, end)
            val normalizedMelody = melody.normalizeMelody()
            val fullMelody = getFullMelody(normalizedMelody, playTime)
            return MelodyInfo(playTime, title, fullMelody, index)
        }
    }
}

fun solution(m: String, musicInfos: Array<String>): String {
    val target = m.normalizeMelody()

    val melodyInfos = musicInfos
        .mapIndexed { index, info -> MelodyInfo.from(info, index) }

    val candidates = melodyInfos
        .filter { it.fullMelody.contains(target) }

    if (candidates.isEmpty()) return "(None)"

    // 1순위: playTime 큰 곡, 2순위: order 작은 곡 (먼저 나온 곡)
    val best = candidates
        .sortedWith(
            compareByDescending<MelodyInfo> { it.playTime }
                .thenBy { it.order }
        )
        .first()

    return best.title
}