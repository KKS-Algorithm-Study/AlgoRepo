"""
1. 문제 접근 방식
  1) 규칙을 찾아 dp로 풀이 가능하다.

2. 시간 복잡도
  1) o(1)이다.

"""
n = int(input())
dp = [0, 1, 1] + [0] * 88

for i in range(3, 91):
  dp[i] = dp[i - 2] + dp[i - 1]

print(dp[n])