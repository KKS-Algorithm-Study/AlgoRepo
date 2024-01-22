import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (n, m) = br.readLine().split(" ").map { it.toInt() }
    val arr = IntArray(n+1) { it }

    fun find(x: Int): Int {
        if (arr[x] == x) return x
        arr[x] = find(arr[x])
        return arr[x]
    }

    fun merge(x: Int, y: Int): Unit {
        val nx = find(x)
        val ny = find(y)
        if (nx == ny) return
        arr[ny] = nx
    }

    repeat(m) {
        val (calc, a, b) = br.readLine().split(" ").map { it.toInt() }
        if (calc == 0) {
            merge(a, b)
        } else {
            if (find(a) == find(b)) println("YES") else println("NO")
        }
    }

    br.close()
}