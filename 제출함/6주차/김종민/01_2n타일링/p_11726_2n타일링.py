"""
문제 접근 방식:
    DP는 큰 문제를 작은 문제로 분할해서 풀이하는 것
    기존에 계산 된 값은 캐싱하여 사용하는 '메모이제이션' 사용
    타일 문제는 f(n) = f(n-1) + f(n-2)의 점화식으로 풀림
"""

dp = [0 for _ in range(1001)]
dp[1] = 1
dp[2] = 2

n = int(input())

for i in range(3,n+1):
    dp[i] = dp[i-1] + dp[i-2]

print(dp[n] % 10007)