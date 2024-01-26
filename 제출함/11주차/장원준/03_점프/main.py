"""
1. 문제 접근 방식
  1) DP로 풀기!
  2) 각 인덱스에서는 그 인덱스까지 올 수 있는 경우의 수를 체크한다.

2. 시간 복잡도
  1) 가로, 세로길이가 n인 게임판을 1번만 순회하므로 O(N**2)이다.

"""
import sys

input = sys.stdin.readline

n = int(input().rstrip())
board = [[*map(int, input().split())] for _ in range(n)]
dp = [[0] * n for _ in range(n)]
dp[0][0] = 1

for i in range(n):
  for j in range(n):
    if i == n - 1 and j == n - 1:
     break

    jump_val = board[i][j]

    if 0 <= i + jump_val < n:
      dp[i + jump_val][j] += dp[i][j]

    if 0 <= j + jump_val < n:
      dp[i][j + jump_val] += dp[i][j]

print(dp[n - 1][n - 1])