"""
1. 문제 접근 방식
  1) 2차원의 누적합으로 풀기
    a) 현재 += 왼쪽 + 윗쪽 - 대각위 + 현재 위치값
  2) 2차원의 누적합은 다음과 같이 계산한다.
    a) (r1, c1) ~ (r2, c2) 의 값을 구할 때
    b) prefix_sum[r2][c2] \
           - prefix_sum[r2][c1 - 1] \
           - prefix_sum[r1 - 1][c2] \
           + prefix_sum[r1 - 1][c1- 1]

2. 시간 복잡도
  1) 누적합을 구할 때 배열의 요소만큼만 순회하면 되므로 O(n * m)이다.

3. 참고 링크
 https://nahwasa.com/entry/%EB%88%84%EC%A0%81-%ED%95%A9prefix-sum-2%EC%B0%A8%EC%9B%90-%EB%88%84%EC%A0%81%ED%95%A9prefix-sum-of-matrix-with-java
"""

import sys

input = sys.stdin.readline

n, m = map(int, input().split())
land = [list(map(int, input().split())) for _ in range(n)]

num_test_cases = int(input().rstrip())
test_cases = [list(map(int, input().split())) for _ in range(num_test_cases)]

prefix_sum = [[0] * (m + 1) for _ in range(n + 1)]

# 누적합 계산
for i in range(1, n + 1):
  for j in range(1, m + 1):
    # 행에 대해 먼저 누적합 계산
    # 마지막에 현재 바라보고 있는 값 추가
    prefix_sum[i][j] += prefix_sum[i - 1][j] + prefix_sum[i][j - 1] - prefix_sum[i - 1][j - 1] + land[i - 1][j - 1]

for r1, c1, r2, c2 in test_cases:
  result = prefix_sum[r2][c2] \
           - prefix_sum[r2][c1 - 1] \
           - prefix_sum[r1 - 1][c2] \
           + prefix_sum[r1 - 1][c1 - 1]

  print(result)
