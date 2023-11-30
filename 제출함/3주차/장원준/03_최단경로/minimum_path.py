"""
1. 문제 접근 방식
  1) "주어진 시작점에서" 다른 모든 정점으로의 최단 경로를 구하는 문제
    -> 다익스트라 알고리즘 이용

2. 시간 복잡도
  1) 우선순위 큐를 사용하는 다익스트라 알고리즘은 O(ElogV) 시간복잡도를 가진다.
"""
import sys
import heapq

input = sys.stdin.readline
INF = int(1e9)

num_vertex, num_edge = map(int, input().split())
start = int(input())
distance = [INF] * (num_vertex + 1)
graph = dict()

for i in range(1, num_vertex + 1):
  graph[i] = []

for _ in range(num_edge):
  u, v, w = map(int, input().split())
  graph[u].append((v, w))

def dijkstra(start):
  q = []

  # 힙에 (가중치, 정점)의 값을 가지는 튜플을 넣는다.
  heapq.heappush(q, (0, start))
  distance[start] = 0

  while q:
    dist, now = heapq.heappop(q)

    # 이미 최단거리 계산이 되었다면 continue
    if distance[now] < dist:
      continue

    # 새로운 cost를 계산
    for elem in graph[now]:
      cost = dist + elem[1]

      if cost < distance[elem[0]]:
        distance[elem[0]] = cost
        heapq.heappush(q, (cost, elem[0]))

dijkstra(start)

for i in range(1, num_vertex + 1):
  if distance[i] == INF:
    print('INF')
  else:
    print(distance[i])