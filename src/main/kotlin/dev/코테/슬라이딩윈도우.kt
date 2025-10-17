import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

/**
 * BOJ 2003 - 수들의 합 2
 * 요구사항: 연속 부분배열의 합이 정확히 M인 경우의 수를 구하라.
 * 제약: 1 ≤ N ≤ 10,000, 1 ≤ M ≤ 300,000,000, 1 ≤ A[i] ≤ 30,000  (모두 자연수)
 *
 * 접근:
 *  - 자연수 배열 => 윈도우 합은 rt 증가 시 단조 증가, lt 증가 시 단조 감소.
 *  - 따라서 rt는 0..N-1로 한 번만 전진, lt도 0..N-1로 한 번만 전진 => O(N).
 */
object FastScanner {
    private val br = BufferedReader(InputStreamReader(System.`in`))
    private var st: StringTokenizer? = null

    fun next(): String {
        while (st == null || !st!!.hasMoreTokens()) {
            st = StringTokenizer(br.readLine())
        }
        return st!!.nextToken()
    }

    fun nextInt(): Int = next().toInt()
}

object Solver {

    data class Input(
        val n: Int,
        val m: Int,
        val arr: IntArray
    )

    /** 입력 파싱 + 검증 */
    fun parseAndValidate(): Input {
        val n = FastScanner.nextInt()
        val m = FastScanner.nextInt()
        require(n in 1..10_000) { "N out of range: $n" }
        require(m in 1..300_000_000) { "M out of range: $m" }

        val arr = IntArray(n) { FastScanner.nextInt() }
        // 요소 검증 (자연수)
        for (i in 0 until n) {
            require(arr[i] in 1..30_000) { "A[$i] out of range: ${arr[i]}" }
        }
        return Input(n, m, arr)
    }

    /**
     * 자연수 배열에서 '합 == target' 인 연속 부분배열의 개수.
     * 투 포인터 불변식:
     *  - 윈도우 [lt, rt]의 합 = sum
     *  - rt는 매 스텝 +1로 확장
     *  - sum >= target 이면 lt를 줄이며 필요한 만큼 축소
     *  - 축소 중 sum == target이면 카운팅
     */
    fun countSubarraySumEqualsTarget(arr: IntArray, target: Int): Long {
        var lt = 0
        var sum = 0L
        var count = 0L

        for (rt in arr.indices) {
            sum += arr[rt].toLong()

            // sum이 target 이상이면 왼쪽을 당겨 단조 감소
            while (sum >= target) {
                if (sum == target.toLong()) count++
                sum -= arr[lt].toLong()
                lt++
            }
        }
        return count
    }
}

fun main() {
    val input = Solver.parseAndValidate()
    val answer = Solver.countSubarraySumEqualsTarget(input.arr, input.m)
    println(answer)
}
