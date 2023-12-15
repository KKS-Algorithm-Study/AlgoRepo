"""
1. 문제 접근 방식
  1) 그래프를 입력받고, 섬에 번호를 붙여준다.
  2) 가로, 세로 방향으로 탐색하면서 다리가 될 수 있는 후보 (이하 간선)를 구한다.
  3) 크루스칼 알고리즘을 통해 가장 작은 비용을 구한다.

2. 시간 복잡도
  1) 섬에 번호를 부여하기 -> n * m * (n * m) (인접 행렬을 사용한 BFS)
  2) 섬의 다리가 될 수 있는 후보 구하기 -> n * m * max(n, m)
  3) 섬의 다리 구하기 (크루스칼 알고리즘) -> (다리의 갯수)log(섬의 갯수) -> (n + m) * log(섬의갯수)
  4) 따라서 1, 2, 3번을 더하고 작은 값을 제외하면 O(n**2 * m**2)의 시간 복잡도를 가진다.
"""

import sys
from collections import deque

sys.setrecursionlimit(int(1e9))
input = sys.stdin.readline


# 섬에 번호를 부여하기
def indexing_island(island):
  dx = [-1, +1, +0, +0]
  dy = [+0, +0, -1, +1]

  copied_island = [row[:] for row in island]
  visited = [[False] * m for _ in range(n)]
  index = 0

  for i in range(n):
    for j in range(m):
      if island[i][j] and not visited[i][j]:
        index += 1
        queue = deque([(i, j)])
        visited[i][j] = True
        copied_island[i][j] = index

        while queue:
          now_x, now_y = queue.popleft()

          for k in range(4):
            nx, ny = now_x + dx[k], now_y + dy[k]

            if 0 <= nx < n and 0 <= ny < m and island[nx][ny] and not visited[nx][ny]:
              queue.append((nx, ny))
              visited[nx][ny] = True
              copied_island[nx][ny] = index

  return copied_island, index


# 인덱스가 붙은 섬을 탐색하면서, 다리를 간선으로 표현
# a, b, c => a에서 b로 갈 수 있는 다리의 비용은 c이다.
def bridges(island):
  graph = []
  # 가로 다리 계산하기
  for i in range(n):
    for j in range(1, m):
      prev, next = island[i][j - 1], island[i][j]

      # 이전 위치가 섬이고, 다음이 바다이면
      # 이전 위치가 다리의 시작점이 된다.
      if prev > 0 and next == 0:
        idx = j

        while idx < m and island[i][idx] == 0:
          idx += 1

        if idx < m:
          length = idx - (j - 1) - 1
          if length > 1:
            graph.append((length, prev, island[i][idx]))
      else:
        continue

  # 세로 다리 계산하기
  for i in range(m):
    for j in range(1, n):
      prev, next = island[j - 1][i], island[j][i]

      # 이전 위치가 섬이고, 다음이 바다이면
      # 이전 위치가 다리의 시작점이 된다.
      if prev > 0 and next == 0:
        idx = j

        while idx < n and island[idx][i] == 0:
          idx += 1

        if idx < n:
          length = idx - (j - 1) - 1
          if length > 1:
            graph.append((length, prev, island[idx][i]))
      else:
        continue

  return graph


def find(groups, a):
  if groups[a] == a:
    return a

  groups[a] = find(groups, groups[a])

  return groups[a]


def union(groups, a, b):
  a = find(groups, a)
  b = find(groups, b)

  if a > b:
    a, b = b, a

  groups[b] = a

  return groups


n, m = map(int, input().split())
island = [list(map(int, input().split())) for _ in range(n)]
graph = []
result = 0

# 섬에 번호 붙이기
indexed_island, num_island = indexing_island(island)

# 섬의 다리가 될 수 있는 후보 구하기
bridges = sorted(bridges(indexed_island))

# 섬의 다리를 기반으로 MST 수행
groups = list(range(num_island + 1))

for c, a, b in bridges:
  a = find(groups, a)
  b = find(groups, b)

  if a != b:
    union(groups, a, b)
    result += c


# 모든 섬이 이어져있는지 체크
prev = find(groups, groups[1])
flag = True

for i in range(1, len(groups)):
  next = find(groups, groups[i])

  if prev != next:
    flag = False
    break

  prev = next

if flag:
  print(result)
else:
  print(-1)