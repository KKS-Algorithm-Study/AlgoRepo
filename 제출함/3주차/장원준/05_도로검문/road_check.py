"""
1. 문제 접근 방식
  1) 먼저 도로를 제거하지 않은 상태에서 최소 비용으로 이동할 수 있는 경로를 구한다.
  2) 그 경로의 도로를 1개씩 제거하며 다시 최소 비용을 계산한다.
  3) 계산한 최소 비용들의 최소값을 구한다.

2. 시간 복잡도
  1) 우선순위 큐를 사용하는 다익스트라 알고리즘은 O(ElogV) 시간복잡도를 가진다.
  2) 따라서 총 노드의 갯수 만큼 다익스트라 알고리즘을 반복하므로 O(N * NlogM)의 시간복잡도를 가진다.
"""

import sys
import heapq

# 입력 받기
input = sys.stdin.readline

n, m = map(int, input().split())
INF = int(1e9)
graph = dict()
roads = []
min_roads = [(0, INF)] * (n + 1)
next = [(0, 0)] * (n + 1)

for i in range(1, n + 1):
  graph[i] = []

for _ in range(m):
  a, b, t = map(int, input().split())

  graph[a].append((b, t))
  graph[b].append((a, t))
  roads.append((a, b, t))

  # 각 노드에 연결된 도로 중 가장 비용이 작은 도로를 min_roads에 저장
  if min_roads[a][1] > t:
    min_roads[a] = (b, t)

  if min_roads[b][1] > t:
    min_roads[b] = (a, t)

def dijkstra(start, end):
  q = []
  distance = [INF] * (n + 1)

  heapq.heappush(q, (0, start))
  distance[start] = 0

  while q:
    dist, now = heapq.heappop(q)

    if distance[now] < dist:
      continue

    for elem in graph[now]:
      cost = dist + elem[1]

      if cost < distance[elem[0]]:
        # 다익스트라를 통해 1 -> N까지 최소비용으로 가능 경로를 next에 저장
        if not next[now][0]:
          next[now] = (elem[0], elem[1])
        distance[elem[0]] = cost
        heapq.heappush(q, (cost, elem[0]))

  return distance[end]

# 기본 값 계산
origin_dist = dijkstra(1, n)

# 도로의 갯수만큼 for 문을 반복하면서 각 도로를 제거하며 최소 거리 계산
result = -INF
for i in range(n + 1):

  if not next[i][0]:
    continue
  a, b, t = i, next[i][0], next[i][1]

  graph[a].remove((b, t))
  graph[b].remove((a, t))

  deplayed_dist = dijkstra(1, n)

  if deplayed_dist == INF:
    result = INF
    break

  result = max(result, deplayed_dist - origin_dist)

  graph[a].append((b, t))
  graph[b].append((a, t))

if result == INF:
  print(-1)
else:
  print(result)