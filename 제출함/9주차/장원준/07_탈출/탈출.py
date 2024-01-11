"""
1. 문제 접근 방식
  1) 고슴도치가 먼저 탈줄할 경로를 찾고 물을 퍼뜨린다.
    a) 2차원 배열에 튜플을 저장하는데 앞의 값은 현재의 상태 (물, 돌, 고슴도치 등), 뒤의 값은 고슴도치가 탈출할 때의 경로
  2) 고슴도치가 탈출할 수 있는지 체크 후, 물을 퍼뜨린다.

2. 시간 복잡도
  1) 고슴도치는 최대 (n * m)번 움직일 수 있다.
  2) 고슴도치가 1번 움직일 때마다 world를 1번씩 탐색하므로 O(n * m)이다.
  3) 따라서 최종 시간 복잡도는 O(n**2 * m**2)이다.
"""

def spread_water(world):
  copied_world = [row[:] for row in world]

  for i in range(len(world)):
    for j in range(len(world[0])):
      if world[i][j][0] == '*':
        for k in range(4):
          nx, ny = i + dx[k], j + dy[k]
          if 0 <= nx < len(world) and 0 <= ny < len(world[0])\
              and not world[nx][ny][0] == 'D' and not world[nx][ny][0] == 'X':
            copied_world[nx][ny] = ('*', 1)

  return copied_world


def escape(world):
  start_points = []

  for i in range(n):
    for j in range(m):
      if world[i][j][0] == 'S':
        start_points.append((i, j))

  # 고슴도치가 잠겨버리면 탈출 못함
  if not start_points:
    return False

  for i, j in start_points:
    for k in range(4):
      nx, ny = i + dx[k], j + dy[k]
      if 0 <= nx < len(world) and 0 <= ny < len(world[0]) \
          and not world[nx][ny][0] == '*' and not world[nx][ny][0] == 'X' and not world[nx][ny][0] == 'S':

        # 고슴도치가 탈출함!
        if world[nx][ny][0] == 'D':
          print(world[i][j][1])
          exit(0)

        world[nx][ny] = ('S', world[i][j][1] + world[nx][ny][1])

  return True


dx = [-1, +1, +0, +0]
dy = [+0, +0, -1, +1]

n, m = map(int, input().split())
world = [[('', 0)] * m for _ in range(n)]

for i in range(n):
  tmp = list(input())
  for j in range(m):
    world[i][j] = (tmp[j], 1)

while escape(world):
  world = spread_water(world)

print('KAKTUS')


