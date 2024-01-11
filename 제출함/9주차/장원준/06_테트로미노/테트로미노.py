"""
1. 문제 접근 방식
  1) 주어진 테트로미노 5개에서 회전, 대칭을 모두 계산하면 총 19가지의 테트로미노가 된다.
  2) 따라서 총 19개의 테트로미노를 구하는 좌표를 계산 후, 종이에 1개씩 붙여보면서 최대값을 구한다.

2. 시간 복잡도
  1) 종이 위의 모든 점을 탐색해야 한다 -> O(n * m)
  2) 1개의 점을 기준으로 테트로미노를 체크할 때 O(19 * 3)번이 소요된다.
  3) 따라서 O(n * m * 19 * 3)이며 최종 시간 복잡도는 O(n * m)이다.
"""
import sys

input = sys.stdin.readline

def check(board, tetrominos, i, j):
  max_value = 0

  for tetromino in tetrominos:
    tmp_sum = board[i][j]
    can_put = True

    for x, y in tetromino:
      nx, ny = x + i, y + j

      if 0 <= nx < len(board) and 0 <= ny < len(board[0]):
        tmp_sum += board[nx][ny]
      else:
        can_put = False
        break

    if not can_put:
      tmp_sum = 0

    max_value = max(max_value, tmp_sum)

  return max_value


n, m = map(int, input().split())
board = [list(map(int, input().split())) for _ in range(n)]
result = 0

tetrominos = [
  [(+0, +1), (+0, +2), (+0, +3)],
  [(+1, +0), (+2, +0), (+3, +0)],
  [(+0, +1), (+1, +0), (+1, +1)],
  [(+1, +0), (+2, +0), (+2, +1)],
  [(+1, +0), (+2, +0), (+2, -1)],
  [(+0, +1), (+0, +2), (+1, +0)],
  [(+0, +1), (+1, +1), (+2, +1)],
  [(+1, +0), (+1, -1), (+1, -2)],
  [(+1, +0), (+1, +1), (+1, +2)],
  [(+0, +1), (+1, +0), (+2, +0)],
  [(+0, +1), (+0, +2), (+1, +2)],
  [(+1, +0), (+1, +1), (+2, +1)],
  [(+0, -1), (+1, -1), (+1, -2)],
  [(+1, +0), (+1, -1), (+2, -1)],
  [(+0, +1), (+1, +1), (+1, +2)],
  [(+0, +1), (+0, +2), (+1, +1)],
  [(+1, +0), (+2, +0), (+1, -1)],
  [(+1, -1), (+1, +0), (+1, +1)],
  [(+1, +0), (+2, +0), (+1, +1)],
]


for i in range(n):
  for j in range(m):
    # 특정 점에 모든 테트로미노를 넣었을 때의 최대 값을 구한다.
    result = max(result, check(board,tetrominos, i, j))

print(result)
