n = int(input())

arr = list(map(int, input().split(' ')))
dp = [1] * n

for i in range(n):
    maxVal = 0
    for j in range(i-1,-1,-1):
        if arr[j] < arr[i]:
            maxVal = max(maxVal, dp[j])
    dp[i] = maxVal + 1

print(max(dp))