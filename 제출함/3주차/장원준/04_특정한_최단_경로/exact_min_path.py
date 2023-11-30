"""
1. 문제 접근 방식
  1) "반드시 지나야 하는 두 개의 점"이 있으므로 다익스트라 알고리즘 사용

2. 시간 복잡도
  1) 우선순위 큐를 사용하는 다익스트라 알고리즘은 O(ElogV) 시간복잡도를 가진다.
"""

import sys
import heapq
input = sys.stdin.readline

n, e = map(int, input().split())
INF = int(1e9)
graph = dict()

for i in range(1, n + 1):
  graph[i] = []

for _ in range(e):
  a, b, c = map(int, input().split())

  graph[a].append((b, c))
  graph[b].append((a, c))

v1, v2 = map(int, input().split())

def dijkstra(start, end):
  distance = [INF] * (n + 1)
  q = []

  heapq.heappush(q, (0, start))
  distance[start] = 0

  while q:
    dist, now = heapq.heappop(q)

    if distance[now] < dist:
      continue

    for elem in graph[now]:
      cost = dist + elem[1]

      if cost < distance[elem[0]]:
        distance[elem[0]] = cost
        heapq.heappush(q, (cost, elem[0]))

  return distance[end]

# 2개의 정점만 지나면 되므로, 2가지 경우의 수만 계산
case1 = dijkstra(1, v1) + dijkstra(v1, v2) + dijkstra(v2, n)
case2 = dijkstra(1, v2) + dijkstra(v2, v1) + dijkstra(v1, n)

if min(case1, case2) >= INF:
  print(-1)
else:
  print(min(case1, case2))