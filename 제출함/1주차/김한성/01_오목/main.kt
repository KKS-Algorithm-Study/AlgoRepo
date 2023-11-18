import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val board = Array(19) { IntArray(19) }
    val dx = intArrayOf(1, 1, 0, -1)
    val dy = intArrayOf(0, 1, 1, 1)

    for (i in board.indices) {
        val str = br.readLine().split(" ").map { it.toInt() }
        for (j in board.indices) {
            board[i][j] = str[j]
        }
    }

    for (j in board.indices) {
        for (i in board[j].indices) {
            if (board[i][j] != 0) {
                for (k in 0..3) {
                    var nx = i
                    var ny = j
                    var count = 1

                    while (true) {
                        nx += dx[k]
                        ny += dy[k]

                        if (nx in 0 until 19 && ny in 0 until 19) {
                            if (board[i][j] == board[nx][ny]) count++
                            else break
                        } else break
                    }

                    nx = i
                    ny = j

                    while (true) {
                        nx -= dx[k]
                        ny -= dy[k]

                        if (nx in 0 until 19 && ny in 0 until 19) {
                            if (board[i][j] == board[nx][ny]) count++
                            else break
                        } else break
                    }

                    if (count == 5) {
                        print("${board[i][j]}\n${i + 1} ${j + 1}")
                        return
                    }
                }
            }
        }
    }

    print(0)
}