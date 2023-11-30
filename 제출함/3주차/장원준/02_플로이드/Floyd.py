"""
1. 문제 접근 방식
  1) "모든 도시의 쌍"에 대해 비용의 최솟값을 구하는 문제
    -> 플로이드 워셜 알고리즘 이용

2. 시간 복잡도
  1) 플로이드 워셜 알고리즘은 3중 반복문을 사용하므로 시간 복잡도는 O(N^3)이다.
"""
import sys
input = sys.stdin.readline

n = int(input())
m = int(input())
INF = int(1e9)
graph = [[INF] * n for _ in range(n)]

# A <-> B를 연결하는 노선은 1개가 아닐 수 있으므로 그 중 최솟값을 저장함
for i in range(m):
  fr, to, cost = map(int, input().split())
  graph[fr - 1][to - 1] = min(graph[fr - 1][to - 1], cost)

# 자기 자신으로 가는 비용은 0으로 초기화
for i in range(n):
  for j in range(n):
    if i == j:
      graph[i][j] = 0

for k in range(n):
  for a in range(n):
    for b in range(n):
      graph[a][b] = min(graph[a][b], graph[a][k] + graph[k][b])

for i in range(n):
  for j in range(n):
    if graph[i][j] == INF:
      graph[i][j] = 0
    print(graph[i][j], end=' ')
  print()