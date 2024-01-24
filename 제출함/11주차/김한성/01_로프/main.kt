import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    val rope = mutableListOf<Int>()
    var max = 0

    repeat(n) {
        rope.add(br.readLine().toInt())
    }

    rope.sortDescending()

    for (i in rope.indices) {
        max = maxOf(max, rope[i] * (i+1))
    }

    print(max)
}