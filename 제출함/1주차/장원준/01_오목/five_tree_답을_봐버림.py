"""
1. 문제 접근 방식
  1) 처음에는 상 -> 하, 좌 -> 우, 좌하 -> 우상, 좌상 -> 우하 총 4가지의 경우를 모두 탐색하려 했음
  2) 그런데 뭔가 이닌가? 싶어서 우하 -> 좌상으로 탐색해 가면서 그래프 탐색으로 풀려고 했음
    -> 생각하다보니 이게 더 아니다 싶었음
  3) 그래서 답을 보고 풀었음

2. 시간 복잡도
  1) 바둑판의 넓이는 정해져 있고, 각 1개의 점마다 4방향의 탐색을 한다.
  2) 따라서 최악의 경우는 모든 바둑판에 검, 흰 바둑돌이 1번씩 번갈아가며 놓인 경우이다.
  3) 그 때의 시간 복잡도는 19 * 19 * 4 이므로 상수 시간 복잡도를 가진다
"""

board = [[0] * 19 for _ in range(19)]

# 바둑판 입력받기
for i in range(19):
  board[i] = list(map(int, input().split()))

def is_line_up(x, y, color):
  return 0 <= x < 19 and 0 <= y < 19 and board[x][y] == color

# 좌표를 입력 받아서 4방향으로 오목인지 판단하기
def check(x, y):
  color = board[x][y]

  # 4방향으로 오목 탐색
  for i in range(4):
    count = 1
    nx = x + dx[i]
    ny = y + dy[i]

    while 0 <= nx < 19 and 0 <= ny < 19 and board[nx][ny] == color:
      count += 1

      if count == 5:
        # 앞뒤로 6개의 돌이 나란히 위치하는지 체크
        if is_line_up(x - dx[i], y - dy[i], color) or is_line_up(nx + dx[i], ny + dy[i], color):
          break

        print(color)
        print(x + 1, y + 1)
        exit(0)

      nx += dx[i]
      ny += dy[i]

#     우,  하, 우상, 우하
dx = [+0, +1, -1, +1]
dy = [+1, +0, +1, +1]

for i in range(19):
  for j in range(19):
    if board[i][j] != 0:
      check(i, j)

print(0)