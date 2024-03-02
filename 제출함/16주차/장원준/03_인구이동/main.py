"""
1. 문제 접근 방식
  1) bfs를 이용한 구현 문제

2. 시간 복잡도
  1) 인구 이동하는 횟수는 최대 2000번이다. -> O(1)
  2) 1번의 인구 이동에서 n**2 번의 반복을 한다. -> O(n**2)
  3) 1번의 반복에서 bfs를 수행한다. -> O(n**2)
  4) 따라서 최종 시간 복잡도는 O(n**4)이다.
"""

def move():
  global world

  world = [row[:] for row in world]
  visited = [[False] * n for _ in range(n)]
  unions = []

  # 연합을 구한다.
  for i in range(n):
    for j in range(n):
      if not visited[i][j]:
        # union 구하기
        total = world[i][j]
        union = [(i, j)]
        queue = deque([(i, j)])
        visited[i][j] = True

        while queue:
          now_x, now_y = queue.popleft()

          for k in range(4):
            nx, ny = now_x + dx[k], now_y + dy[k]

            if 0 <= nx < n and 0 <= ny < n and not visited[nx][ny]:
              # 두 나라의 인구 차이를 구함
              if l <= abs(world[now_x][now_y] - world[nx][ny]) <= r:
                queue.append((nx, ny))
                union.append((nx, ny))
                visited[nx][ny] = True
                total += world[nx][ny]

        unions.append((total, union))

  # 만약 연합의 갯수가 나라의 갯수와 같다면 더 이상 이동 불가능하다.
  if len(unions) == n ** 2:
    return False

  # 연합에 따라 인구 이동을 한다.
  for total, union in unions:
    population = total // len(union)
    # 인구수 나누어 copied_world에 반영
    for x, y in union:
      world[x][y] = population

  return True


import sys
from collections import deque

dx = [+1, -1, +0, +0]
dy = [+0, +0, +1, -1]

input = sys.stdin.readline
n, l, r = map(int, input().split())
world = [[*map(int, input().split())] for _ in range(n)]
answer = 0

while True:
  if not move():
    break

  answer += 1

print(answer)