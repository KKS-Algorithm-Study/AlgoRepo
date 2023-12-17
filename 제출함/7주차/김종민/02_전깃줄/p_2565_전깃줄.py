n = int(input())

arr = []
for _ in range(n):
    arr.append(tuple(map(int, input().split(' '))))

arr.sort()
dp = [1] * n

for i in range(n):
    mav = 0
    for j in range(i):
        if arr[i][1] > arr[j][1]:
            mav = max(mav, dp[j])
        dp[i] = mav+1

print(n-max(dp))