n = int(input())

weight = list(map(int, input().split()))
max_value = sum(weight) + 1

m = int(input())

marbles = list(map(int, input().split()))

INF = int(1e5)
dp = [[INF] * max_value for _ in range(n + 1)]

for i in range(n + 1):
    dp[i][0] = 0

for i in range(1, n + 1):
    w1 = weight[i - 1]

    for w2 in range(1, max_value):
        if w1 > w2:
            dp[i][w2] = dp[i - 1][w2]
            continue

        dp[i][w2] = min(dp[i - 1][w2], dp[i - 1][w2 - w1] + 1)

answer = []

for marble in marbles:
    temp = 'N'
    
    for w in range(marble, max_value):
        if dp[-1][w] >= INF or dp[-1][w - marble] >= INF:
            continue
        
        temp = 'Y'
        break

    answer.append(temp)
    
print(' '.join(answer))

