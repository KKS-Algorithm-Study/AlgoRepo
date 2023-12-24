import sys

input = sys.stdin.readline

T = int(input())

for _ in range(T):
    n = int(input())

    stickers = [[0] + list(map(int, input().split())) for i in range(2)]
    
    dp = [[0] * (n + 1) for i in range(2)]
    dp[0][1], dp[1][1] = stickers[0][1], stickers[1][1]
    
    for i in range(2, n + 1):
          dp[0][i] = max(dp[0][i - 2], dp[1][i - 1], dp[1][i - 2]) + stickers[0][i]
          dp[1][i] = max(dp[0][i - 1], dp[1][i - 2], dp[0][i - 2]) + stickers[1][i]

    print(max(dp[0][-1], dp[1][-1]))
