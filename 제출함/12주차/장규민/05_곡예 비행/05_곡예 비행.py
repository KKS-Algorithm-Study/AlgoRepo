n, m = map(int, input().split())

board = [[0] * (m + 1)] + [[0] + list(map(int, input().split())) for _ in range(n)]

INF = -int(1e9)
up = [[INF] * (m + 1) for _ in range(n + 2)]
down = [[INF] * (m + 2) for _ in range(n + 2)]

for i in range(n, 0, -1):
    for j in range(1, m + 1):
        if up[i + 1][j] == INF and up[i][j - 1] == INF:
            up[i][j] = board[i][j]
            continue
        
        up[i][j] = max(up[i + 1][j], up[i][j - 1]) + board[i][j]

for i in range(n, 0, -1):
    for j in range(m, 0, -1):
        if down[i + 1][j] == INF and down[i][j + 1] == INF:
            down[i][j] = board[i][j]
            continue
        
        down[i][j] = max(down[i + 1][j], down[i][j + 1]) + board[i][j]

answer = INF
for i in range(1, n + 1):
    for j in range(1, m + 1):
        answer = max(answer, up[i][j] + down[i][j])
print(answer)
