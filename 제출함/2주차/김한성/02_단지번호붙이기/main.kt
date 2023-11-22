import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val n = br.readLine().toInt()

    // 인풋 받을 지도
    val map = Array(n) { IntArray(n) }

    val visited = Array(n) { BooleanArray(n) }

    // 우하좌상 방향
    val dx = arrayOf(0, 1, 0, -1)
    val dy = arrayOf(1, 0, -1, 0)

    // 아파트 단지
    val blocks = mutableListOf<Int>()

    repeat(n) { x ->
        val temp = br.readLine()
        repeat(n) { y ->
            map[x][y] = temp[y] - '0'
        }
    }

    fun dfs(x: Int, y: Int) {
        visited[x][y] = true
        blocks[blocks.size-1]++
        for (i in dx.indices) {
            val nx = x + dx[i]
            val ny = y + dy[i]

            if (nx !in 0..<n || ny !in 0..<n) continue
            if (visited[nx][ny]) continue

            if (map[nx][ny] == 1) {
                dfs(nx, ny)
            }
        }
    }

    for (i in 0..<n) {
        for (j in 0..<n) {
            if (visited[i][j]) continue
            if (map[i][j] == 1) {
                blocks.add(0)
                dfs(i, j)
            }
        }
    }

    blocks.sort()
    println(blocks.size)
    blocks.forEach {
        println(it)
    }

    br.close()
}