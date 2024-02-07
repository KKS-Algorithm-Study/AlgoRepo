"""
1. 문제 접근 방식
  1) 시작점 -> 끝점, 끝점 -> 시작점까지의 최단 거리를 계산한다.

2. 시간 복잡도
  1) 그래프를 입력받고 각 노드의 자식을 정렬한다. -> (O(n * nlogn)
  2) bfs를 수행한다 -> O(n + m)
  3) 따라서 최종 시간 복잡도는 O(n**2 * logn)이다.

"""
def min_path(start, end):
  queue = deque([(start, [])])
  visited[start] = True
  min_path = []

  while queue:
    now, path = queue.popleft()

    if now == end:
      min_path = path
      break

    for next in graph[now]:
      if not visited[next]:
        new_path = path[:]
        new_path.append(next)
        queue.append((next, new_path))
        visited[next] = True

  return min_path


import sys
from collections import deque

input = sys.stdin.readline
n, m = map(int, input().split())

graph = {}

for i in range(n + 1):
  graph[i] = []

for _ in range(m):
  a, b = map(int, input().split())

  graph[a].append(b)
  graph[b].append(a)

for i in range(n + 1):
  graph[i].sort()

s, e = map(int, input().split())

# 시작점 -> 끝점까지 최단 거리 계산
visited = [False] * (n + 1)
start_min_path = min_path(s, e)

# 끝점 -> 시작점까지 최단 거리 계산
visited = [False] * (n + 1)
for node in start_min_path:
  visited[node] = True

end_min_path = min_path(e, s)

# 결과 출력
print(len(start_min_path) + len(end_min_path))
