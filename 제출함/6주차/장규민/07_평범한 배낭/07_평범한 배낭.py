n, k = map(int, input().split())

things = [[]] + [list(map(int, input().split())) for _ in range(n)]

dp = [[0] * (k + 1) for _ in range(n + 1)]

for i in range(1, n + 1):
    weight, value = things[i]

    for w in range(1, k + 1):
        if weight > w:
            dp[i][w] = dp[i - 1][w]
            continue

        dp[i][w] = max(dp[i - 1][w], dp[i - 1][w - weight] + value)


print(dp[-1][-1])
