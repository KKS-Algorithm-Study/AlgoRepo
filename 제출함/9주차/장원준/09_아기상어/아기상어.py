"""
1. 문제 접근 방식
  1) 상어의 위치를 바꿔가면서, 매번 조건에 맞는 물고기를 찾는다.

2. 시간 복잡도
  1) 최대 world의 칸 갯수만큼 반복한다 -> O(n**2)
  2) 1개의 칸에서 bfs 수행 -> O(n**2 * 4)
  3) 따라서 최종 시간 복잡도는 O(n**4)이다. (n은 최대 20이라 시간 내에 풀이 가능)

"""

from collections import deque

def eatable_fishes(world, shark_point):
  copied_world = [[0] * n for _ in range(n)]
  visited = [[False] * n for _ in range(n)]
  fish_points = []

  x, y = shark_point
  shark_size = world[x][y]
  queue = deque([shark_point])
  visited[x][y] = True

  while queue:
    now_x, now_y = queue.popleft()

    for i in range(4):
      nx, ny = now_x + dx[i], now_y + dy[i]

      if 0 <= nx < n and 0 <= ny < n and not visited[nx][ny]:
        visited[nx][ny] = True
        # 물고기가 있는데, 자기 자신보다 큰 물고기일 때
        if shark_size < world[nx][ny]:
          continue

        queue.append((nx, ny))
        copied_world[nx][ny] = copied_world[now_x][now_y] + 1

        if world[nx][ny] > 0 and world[nx][ny] < shark_size:
          fish_points.append((nx, ny, copied_world[nx][ny]))

  fish_points.sort(key=lambda x: (x[2], x[0], x[1]))

  return fish_points


dx = [+0, +0, +1, -1]
dy = [-1, +1, +0, +0]

n = int(input())
world = [[0] * n for _ in range(n)]
shark_point = (0, 0)
result_second = 0
eat_count = 0

for i in range(n):
  row = [*map(int, input().split())]
  for j in range(n):
    world[i][j] = row[j]

    if row[j] == 9:
      shark_point = (i, j)
      world[i][j] = 2


while True:
  fish_points = eatable_fishes(world, shark_point)

  if not fish_points:
    print(result_second)
    break

  # 가장 우선순위가 높은 물고기를 잡아먹는다.
  shark_x, shark_y = shark_point
  fish_x, fish_y, dist = fish_points[0]

  # 물고기 먹은 수 증가
  eat_count += 1

  # 상어의 몸집 증가
  if world[shark_x][shark_y] == eat_count:
    world[shark_x][shark_y] += 1
    eat_count = 0

  world[fish_x][fish_y] = world[shark_x][shark_y]
  # 이전에 상어가 있던 자리 정리
  world[shark_x][shark_y] = 0
  # 상어가 다음 고기를 잡아먹으러 가는 시간 증가
  result_second += dist
  # 상어의 위치 변경
  shark_point = (fish_x, fish_y)
