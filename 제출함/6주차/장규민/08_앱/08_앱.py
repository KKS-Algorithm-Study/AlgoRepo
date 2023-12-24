n, m = map(int, input().split())

memories = list(map(int, input().split()))

costs = list(map(int, input().split()))
max_value = sum(costs) + 1

apps = list(zip(memories, costs))

dp = [[0] * max_value for _ in range(n + 1)]

for i in range(1, n + 1):
    memo, cost = apps[i - 1]
    
    for c in range(max_value):
        if cost > c:
            dp[i][c] = dp[i - 1][c]
            continue
        
        dp[i][c] = max(dp[i - 1][c - cost] + memo, dp[i - 1][c])


for i in range(max_value):
    if dp[-1][i] >= m:
        print(i)
        break