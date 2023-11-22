import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.LinkedList

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (n, m, v) = br.readLine().split(" ").map { it.toInt() }
    val nodes = MutableList(n+1) { mutableListOf<Int>() }
    var visited = BooleanArray(n+1)

    repeat(m) {
        val (a, b) = br.readLine().split(" ").map { it.toInt() }
        nodes[a].add(b)
        nodes[b].add(a)
    }

    nodes.forEach { it.sort() }

    dfs(nodes, visited, v)
    visited = BooleanArray(n+1)
    println()
    bfs(nodes, visited, v)

    br.close()
}

fun dfs(nodes: MutableList<MutableList<Int>>, visited: BooleanArray, node: Int) {
    visited[node] = true
    print("$node ")

    for (i in nodes[node]) {
        if (!visited[i]) {
            dfs(nodes, visited, i)
        }
    }
}

fun bfs(nodes: MutableList<MutableList<Int>>, visited: BooleanArray, node: Int) {
    val q = LinkedList<Int>()
    q.offer(node)
    visited[node] = true

    while(q.isNotEmpty()) {
        val temp = q.poll()
        print("$temp ")
        for (i in nodes[temp]) {
            if (!visited[i]) {
                q.offer(i)
                visited[i] = true
            }
        }
    }
}