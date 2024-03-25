n = int(input())

board = [list(map(int, input().split())) for _ in range(n)]

dp = [[0] * n for _ in range(n)]
dp[0][0] = 1

for i in range(n):
    for j in range(n):
        
        if board[i][j] == 0:
            break
        
        ny, nx = i + board[i][j], j + board[i][j]

        if ny < n:
            dp[ny][j] += dp[i][j]

        if nx < n:
            dp[i][nx] += dp[i][j]


print(dp[-1][-1])
