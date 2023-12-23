"""
1. 문제 접근 방식
  1) 특정 집을 색칠할 때 이전에 색칠한 집을 제외한 나머지 2개의 집 중 1개를 선택해야 한다.

2. 시간 복잡도
  1) for문을 2번 수행하지만, 안쪽 for문은 3으로 고정이므로 O(n)이다.
"""

n = int(input())

dp = [list(map(int, input().split())) for _ in range(n)]

for i in range(1, n):
    for j in range(3):
        dp[i][j] = dp[i][j] + min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3])

print(min(dp[n - 1][0], dp[n - 1][1], dp[n - 1][2]))