import sys

input = sys.stdin.readline

n = int(input())

house = [list(map(int, input().split())) for _ in range(n)]
INF = int(1e6)

answer = INF

for start in range(3):
    dp = [[INF] * 3 for _ in range(n + 1)]
    dp[1][start] = house[0][start]
    
    for i in range(2, n):
        dp[i][0] = min(dp[i - 1][1], dp[i - 1][2]) + house[i - 1][0]
        dp[i][1] = min(dp[i - 1][0], dp[i - 1][2]) + house[i - 1][1]
        dp[i][2] = min(dp[i - 1][1], dp[i - 1][0]) + house[i - 1][2]

    for i in range(3):
        if i == start:
            continue
        
        answer = min(answer, min(dp[n - 1][(i + 1) % 3], dp[n - 1][(i + 2) % 3]) + house[-1][i])
        

print(answer)
