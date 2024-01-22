import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m, k) = br.readLine().split(" ").map { it.toInt() }
    val price = br.readLine().split(" ").map { it.toInt() }
    val parent = IntArray(n) { it }

    fun find(x: Int): Int {
        if (parent[x] == x) return x
        parent[x] = find(parent[x])
        return parent[x]
    }

    fun merge(x: Int, y: Int): Unit {
        val nx = find(x)
        val ny = find(y)
        if (nx == ny) return
        if (price[nx] < price[ny]) {
            parent[ny] = nx
        } else {
            parent[nx] = ny
        }
    }

    repeat(m) {
        val (v, w) = br.readLine().split(" ").map { it.toInt()-1 }
        merge(v, w)
    }

    var answer = 0
    val visited = BooleanArray(parent.size)
    parent.forEach {
        val a = find(it)
        if (!visited[a]) answer += price[a]
        visited[a] = true
    }

    if (answer > k) print("Oh no") else print(answer)
    br.close()
}