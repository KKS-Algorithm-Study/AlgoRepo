"""
1. 문제 접근 방식
  1) 행의 인덱스는 물건을, 열의 인덱스는 가방 무게를 의미하는 2차원 배열을 만든다.
  2) 물건을 가방에 넣어 보면서 물건을 넣었을 때와 넣지 않았을 때의 최소값을 비교한다.

2. 시간 복잡도
  1) for문을 2번 수행하므로 O(n * k)이다.
"""
import sys

input = sys.stdin.readline
n, k = map(int, input().split())

dp = [[0] * (k + 1) for _ in range(n + 1)]

for item_idx in range(1, n + 1):
  # 물건을 1개 입력받음
  w, v = map(int, input().split())

  # 물건을 가방에 넣어 보며 무게 계산
  for bag_w in range(1, k + 1):
    if bag_w >= w:
      dp[item_idx][bag_w] = max(v + dp[item_idx - 1][bag_w - w], dp[item_idx - 1][bag_w])
    else:
      dp[item_idx][bag_w] = dp[item_idx - 1][bag_w]

print(dp[n][k])