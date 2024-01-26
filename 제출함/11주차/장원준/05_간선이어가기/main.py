"""
1. 문제 접근 방식
  1) s -> t로 가면서 최소의 비용을 구하면 된다.
  2) 다익스트라 알고리즘으로 풀자.

2. 시간 복잡도
  1) 다익스트라 알고리즘의 시간복잡도는 O(MlogN)이다. (n: 정점의 갯수, m: 간선의 갯수)

"""
from heapq import heappop, heappush
import sys

def dijkstra(start):
  distances = [INF] * (n + 1)

  distances[start] = 0
  q = [(0, start)]


  while q:
    dist, now = heappop(q)

    if dist < distances[now]:
      continue

    for next, cost in graph[now]:
      if dist + cost < distances[next]:
        distances[next] = dist + cost
        heappush(q, (dist + cost, next))

  return distances

input = sys.stdin.readline

INF = int(1e9)
n, m = map(int, input().split())
graph = {}

for i in range(1, n + 1):
  graph[i] = []

for _ in range(m):
  a, b, c = map(int, input().split())
  graph[a].append((b, c))
  graph[b].append((a, c))

s, t = map(int, input().split())

distances = dijkstra(s)

print(distances[t])