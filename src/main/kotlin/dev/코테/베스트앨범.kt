package 코테1

private class Solution {
    fun validateOrThrow(genres: Array<String>, plays: IntArray) {
        require(genres.size in 1..10_000 && plays.size in 1..10000 && genres.size == plays.size) {
            "genres와 plays의 길이는 1..10,000 이어야 하며, 두 배열의 길이는 같아야 합니다. (genres=${genres.size}, plays=${plays.size})"
        }
        require(genres.distinct().size < 100) {
            "장르의 종류는 100개 미만이어야 합니다. (현재: ${genres.distinct().size})"
        }
    }

    fun solution(genres: Array<String>, plays: IntArray): IntArray {
        validateOrThrow(genres, plays)

        var answer = intArrayOf()

        val genresMap = mutableMapOf<String, Int>()
        val songsMap = mutableMapOf<String, MutableList<Pair<Int, Int>>>()

        for (i in genres.indices) {
            genresMap[genres[i]] = genresMap.getOrDefault(genres[i], 0) + plays[i]
            if (songsMap.containsKey(genres[i])) {
                songsMap[genres[i]]!!.add(Pair(i, plays[i]))
            } else {
                songsMap[genres[i]] = mutableListOf(Pair(i, plays[i]))
            }
        }
        val sortedGenres = genresMap.toList().sortedByDescending { it.second }.map { it.first }
        for (genre in sortedGenres) {
            val songs = songsMap[genre]!!
            val sortedSongs = songs.sortedWith(compareByDescending<Pair<Int, Int>> { it.second }.thenBy { it.first })
            val topSongs = sortedSongs.take(2)
            for (song in topSongs) {
                answer += song.first
            }
        }

        return answer
    }
}

fun main() {
    val genres = arrayOf("classic", "pop", "classic", "classic", "pop")
    val plays = intArrayOf(500, 600, 150, 800, 2500)
    val a = Solution().solution(genres, plays)
    a.forEach(::println)
}
