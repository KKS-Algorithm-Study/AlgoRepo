"""
1. 문제 접근 방식
  1) 선수학습이 이루어져야하므로 위상 정렬 알고리즘을 사용한다.

2. 시간 복잡도
  1) 각 노드와 간선을 1번씩 방문해야하므로 O(N + M)의 시간 복잡도를 가진다.
"""
import sys
from collections import deque

input = sys.stdin.readline
n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]
indegree = [0] * (n + 1) # 진입차수를 관리할 배열
terms = [1] * (n + 1)

for _ in range(m):
  prev, next = map(int, input().split())
  graph[prev].append(next)

  # 나한테 들어오는 진입차수를 계산한다.
  indegree[next] += 1

queue = deque()

# 첫 번째로 진입차수가 0인 노드를 큐에 넣어준다.
for i in range(1, n + 1):
  if indegree[i] == 0:
    queue.append((i, 1))

while queue:
  now, term = queue.popleft()

  for next in graph[now]:
    indegree[next] -= 1

    if indegree[next] == 0:
      terms[next] = term + 1
      queue.append((next, term + 1))

for i in range(1, n + 1):
  print(terms[i], end=' ')