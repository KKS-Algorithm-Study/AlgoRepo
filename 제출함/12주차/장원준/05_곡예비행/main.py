"""
1. 문제 접근 방식
  1) 상승과 하강이 특정한 시점 이후로 바뀌므로 상승 dp, 하강 dp를 만들어서 계산

2. 시간 복잡도
  1) map을 총 3번만 반복하므로 O(n * m)이다.

** 근데 왜 INF를 -10001로 주면 틀릴까??

"""
import sys

input = sys.stdin.readline

INF = int(1e9)
n, m = map(int, input().split())
map = [[*map(int, input().split())] for _ in range(n)]

up_dp = [[-INF] * (m + 1) for _ in range(n + 1)]
down_dp = [[-INF] * (m + 1) for _ in range(n + 1)]

# 왼쪽 하단부터 오른쪽 상단으로 향하면서 상승에 대한 dp 만들기
up_dp[n][1] = 0
up_dp[n - 1][0] = 0

for i in range(1, m + 1):
  for j in range(n - 1, -1, -1):
    up_dp[j][i] = max(up_dp[j + 1][i], up_dp[j][i - 1]) + map[j][i - 1]

# 오른쪽 하단부터 왼쪽 상단으로 향하면서 상승에 대한 dp 만들기
down_dp[n][m - 1] = 0
down_dp[n - 1][m] = 0

for i in range(m - 1, -1, -1):
  for j in range(n - 1, -1, -1):
    down_dp[j][i] = max(down_dp[j + 1][i], down_dp[j][i + 1]) + map[j][i]

# 2개의 dp 배열의 같은 열, 같은 행의 값을 더했을 때의 최대값을 구하자.
# 최대값이 되는 지점이 상승, 하강의 터닝 포인트가 된다.
result = -INF

for i in range(n):
  for j in range(1, m + 1):
    # 상승 dp 인덱스 기준 순회
    result = max(result, up_dp[i][j] + down_dp[i][j - 1])

print(result)