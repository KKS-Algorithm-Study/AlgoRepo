"""
1. 문제 접근 방식
  1) 백트래킹을 이용한다.
    - 백트래킹으로 탐색할 대상들을 한군데 모은다. (dict or list 등)
    - dfs로 탐색 > 모든 대상들을 탐색하면 return

2. 시간 복잡도
  1) n**2개의 칸을 탐색 -> O(N**2)
  2) 1개의 칸을 탐색할 때마다 n개를 탐색
  3) 최종 시간 복잡도는 O(N**3)이다.
"""

n = 9

board = [list(map(int, input().split())) for _ in range(n)]

def exists_row(row, target):
  for i in range(n):
    if board[row][i] == target:
      return False

  return True

def exists_col(col, target):
  for i in range(n):
    if board[i][col] == target:
      return False

  return True

def exists_square(x, y, target):
  nx = x // 3 * 3
  ny = y // 3 * 3

  for i in range(3):
    for j in range(3):
      if board[nx + i][ny + j] == target:
        return False

  return True


def fill_board(blanks, depth):
  # 빈칸을 모두 채웠으면 끝
  if depth == len(blanks):
    for row in board:
      print(*row)
    exit(0)

  # 숫자를 1개씩 채워보기
  for i in range(1, 10):
    x, y = blanks[depth][0], blanks[depth][1]

    # 프루닝~
    if exists_row(x, i) and exists_col(y, i) and exists_square(x, y, i):
      board[x][y] = i
      fill_board(blanks, depth + 1)
      board[x][y] = 0

blanks = []

"""
백트래킹으로 프루닝 할 수 있는 대상들을 모으자.
"""

for x in range(n):
  for y in range(n):
    if board[x][y] == 0:
      # 비어있는 위치를 담아줌
      blanks.append((x, y))

fill_board(blanks, 0)