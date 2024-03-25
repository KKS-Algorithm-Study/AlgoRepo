import sys

input = sys.stdin.readline

n = int(input())

numbers = [0] + list(map(int, input().split()))

dp = [[0] * (n + 1) for _ in range(n + 1)]

for i in range(n + 1):
    dp[i][i] = 1

for i in range(n):
    if numbers[i] != numbers[i + 1]:
        continue
    
    dp[i][i + 1] = 1

for i in range(n - 2, 0, -1):
    for j in range(1, n+1):
        if not (numbers[i] == numbers[j] and dp[i + 1][j - 1]):
            continue

        dp[i][j] = 1

for _ in range(int(input())):
    s, e = map(int, input().split())    

    print(dp[s][e])
