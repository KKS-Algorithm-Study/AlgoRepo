import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, k) = br.readLine().split(" ").map { it.toInt() }
    val dp = IntArray(k+1)

    dp[0] = 1

    repeat(n) {
        val v = br.readLine().toInt()
        for (i in v..k) {
            dp[i] += dp[i-v]
        }
    }

    print(dp[k])

    br.close()
}