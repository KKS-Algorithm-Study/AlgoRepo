import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, k) = br.readLine().split(" ").map { it.toInt() }
    val dp = IntArray(k+1) { 1000000 }
    var values = intArrayOf()

    dp[0] = 0

    repeat(n) {
        values += br.readLine().toInt()
    }

    for (i in 1..k) {
        for (v in values) {
            if (i - v >= 0) {
                dp[i] = minOf(dp[i], dp[i-v]+1)
            }
        }
    }

    print(if (dp[k]==1000000) -1 else dp[k])

    br.close()
}