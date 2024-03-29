package 코테123
class Solution {
    fun solution(keymap: Array<String>, targets: Array<String>): IntArray {
        val keyPressCount = targets.map { target ->
            var totalKeyPresses = 0
            for (char in target) {
                val keyPressesForChar = keymap
                    .map { it.indexOf(char) + 1 }
                    .filter { it > 0 }
                    .minOrNull() ?: return@map -1
                totalKeyPresses += keyPressesForChar
            }
            totalKeyPresses
        }
        return keyPressCount.toIntArray()
    }
}

fun main() {
    val solution = Solution()
    println("Result: ${solution.solution(arrayOf("AGZ", "BSSS"), arrayOf("ASA", "BGZ")).contentToString()}")
}