"""
1. 문제 접근 방식
  1) 첫 번째 집을 색칠하는 3가지 경우를 나누어 계산 후 최소값을 비교한다.

2. 시간 복잡도
  1) for문을 2번 수행하지만, 안쪽 for문은 3으로 고정이므로 O(n)이다.
"""

n = int(input())
INF = int(1e9)
dp = [list(map(int, input().split())) for _ in range(n)]

first_row = [dp[0][0], dp[0][1], dp[0][2]]

dp_r = [row[:] for row in dp]
dp_g = [row[:] for row in dp]
dp_b = [row[:] for row in dp]

def solve(dp, first_idx):
    for i in range(1, n):
        for j in range(3):
            # 마지막 집일 경우에는 첫번 째 집과 다른 색으로 칠해야 한다.
            if i == n - 1 and j == first_idx:
                dp[i][j] = INF
            else:
                dp[i][j] = dp[i][j] + min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3])


# 첫 번째 집을 r로 칠했을 때
dp_r[0][0], dp_r[0][1], dp_r[0][2] = first_row[0], INF, INF
solve(dp_r, 0)
min_r = min(dp_r[n - 1])

# 첫 번째 집을 g로 칠했을 때
dp_g[0][0], dp_g[0][1], dp_g[0][2] = INF, first_row[1], INF
solve(dp_g, 1)
min_g = min(dp_g[n - 1])

# 첫 번째 집을 b로 칠했을 때
dp_b[0][0], dp_b[0][1], dp_b[0][2] = INF, INF, first_row[2]
solve(dp_b, 2)
min_b = min(dp_b[n - 1])

print(min(min_r, min_g, min_b))