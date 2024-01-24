import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val t = br.readLine().toInt()

    repeat(t) {
        val n = br.readLine().toInt()
        val sticker = Array(2) {
            br.readLine().split(" ").map { it.toInt() }.toIntArray()
        }

        val dp = Array(2) { IntArray(n) }

        dp[0][0] = sticker[0][0]
        dp[1][0] = sticker[1][0]

        if (n > 1) {
            dp[0][1] = sticker[1][0] + sticker[0][1]
            dp[1][1] = sticker[0][0] + sticker[1][1]
        }

        for (i in 2..<n) {
            dp[0][i] = dp[1][i - 1].coerceAtLeast(dp[1][i - 2]) + sticker[0][i]
            dp[1][i] = dp[0][i - 1].coerceAtLeast(dp[0][i - 2]) + sticker[1][i]
        }

        println(dp[0][n - 1].coerceAtLeast(dp[1][n - 1]))
    }

    br.close()
}