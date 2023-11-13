"""
1. 문제 접근 방식
  1) 3 <= N, M <= 50인 것 보고 일단 사이즈가 작다고 생각함
  2) 그래서 로봇 청소기가 작동하는 방식대로 순서대로 구현함

2. 시간 복잡도
  1) 로봇청소기는 시계 반대방향으로 회전하면서 점차 영역을 넓혀감 (마치 달팽이 모양)
  2) 그래서 최악의 경우는 가장 끝 영역에 벽이 있는 곳을 제외한 모든 곳을 청소해야 하는 경우이다.
  3) 그리고 가장 중앙에서 청소를 시작하는 경우에 O(N * M) 시간 복잡도를 가진다.
"""
n, m = map(int, input().split())
x, y, dir = map(int, input().split())

# 북(0), 동(1), 남(2), 서(3)
dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]

# map의 0 : 청소되지 않은 칸, 1 : 벽, 2 : 청소된 칸
map = [list(map(int, input().split())) for _ in range(n)]

def exists_need_clean(x, y):
  for i in range(4):
    nx = x + dx[i]
    ny = y + dy[i]

    if 0 <= nx < n and 0 <= ny < m and map[nx][ny] == 0:
      return True

  return False

def need_clean(x, y, dir):
  nx = x + dx[dir]
  ny = y + dy[dir]

  return 0 <= nx < n and 0 <= ny < m and map[nx][ny] == 0

num_cleaned_room = 0
cur_x, cur_y, cur_dir = x, y, dir

while True:
  # 현재 칸이 청소되어있지 않으면 청소한다.
  if map[cur_x][cur_y] == 0:
    map[cur_x][cur_y] = 2
    num_cleaned_room += 1

  if exists_need_clean(cur_x, cur_y):
    # 현재 칸의 주변 4칸 중 청소 되지 않은 빈칸이 있는 경우
    rotated_index = (cur_dir - 1) % 4
    cur_dir = rotated_index
    nx = cur_x + dx[rotated_index]
    ny = cur_y + dy[rotated_index]

    if 0 <= nx < n and 0 <= ny < m and map[nx][ny] == 0:
      cur_x += dx[rotated_index]
      cur_y += dy[rotated_index]

  else:
    # 현재 칸의 주변 4칸 중 청소 되지 않은 빈칸이 없는 경우
    back_index = (cur_dir + 2) % 4

    nx = cur_x + dx[back_index]
    ny = cur_y + dy[back_index]

    if 0 <= nx < n and 0 <= ny < m:
      if map[nx][ny] == 1:
        break
      else:
        cur_x += dx[back_index]
        cur_y += dy[back_index]

print(num_cleaned_room)