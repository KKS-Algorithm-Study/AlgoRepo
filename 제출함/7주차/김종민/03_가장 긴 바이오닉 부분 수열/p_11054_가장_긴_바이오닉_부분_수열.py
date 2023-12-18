def search(arr, n):
    dp = [0] * n
    for i in range(n):
        mav = 0
        for j in range(i):
            if arr[i] > arr[j]:
                mav = max(mav, dp[j])
        dp[i] = mav+1
    return dp

n = int(input())
arr = list(map(int, input().split(' ')))

dp1 = search(arr, n)
dp2 = search(arr[::-1], n)[::-1]

mav = 0
for i in range(n):
    mav = max(mav, dp1[i] + dp2[i])

print(mav-1)