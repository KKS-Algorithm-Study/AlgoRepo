"""
1. 문제 접근 방식
  1) 윗줄, 아랫줄을 뗄 떼의 경우의 수를 계산한다.
  2) 현재 스티커를 뗐을 때 이전에 어떤 스티커를 떼면서 올 수 있는지의 경우의 수를 계산한다.

2. 시간 복잡도
  1) for문을 2번 수행하기 때문에 O(T * n)이다.
"""

import sys
input = sys.stdin.readline

n = int(input())

for _ in range(n):
  size = int(input())
  arr = [[0] + list(map(int, input().split())) for _ in range(2)]
  dp = [[0] * (size + 1) for _ in range(2)]

  dp[0][1], dp[1][1] = arr[0][1], arr[1][1]

  for i in range(2, size + 1):
    dp[0][i] = max(dp[1][i - 1], dp[1][i - 2])       + arr[0][i]
    dp[1][i] = max(dp[0][i - 1], dp[0][i - 2])       + arr[1][i]

  print(max(dp[0][size], dp[1][size]))