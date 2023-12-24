n, m = map(int, input().split())

coins = [int(input()) for _ in range(n)]

dp = [10001] * (m + 1)
dp[0] = 0

for coin in coins:
    for c in range(coin, m + 1):
        dp[c] = min(dp[c], dp[c - coin] + 1)

print(dp[m] if dp[m] != 10001 else '-1')
