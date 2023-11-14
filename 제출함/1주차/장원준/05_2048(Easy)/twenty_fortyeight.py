"""
1. 문제 접근 방식
  1) 1 <= N <= 20, 상하좌우 4방향 중 5번 선택인 것 보고 일단 사이즈가 작다고 생각함
  2) 4방향을 중복있게 5번 선택하면 총 1024번 탐색하기 때문
  3) 그래서 단순 구현으로 풀려고 시도함

2. 풀이 과정
  1) 각 돌들은 2의 거듭제곱인 수를 가짐 -> 돌들을 지수로 표현하여 메모리 절약함
  2) 움직이는 경우의 수를 상하좌우로 나누어 중복있게 5개를 선택할 수 있는 permutaion 함수 작성
  3) 입력을 받음
  4) 방향에 따라 board가 움직인 결과를 계산하는 함수 작성
  5) 2번에서 계산한 경우의 수에 따라 board를 움직이고 최대값을 계산

3. 시간 복잡도
  1) board가 움직이는 경우의 수는 총 1024 * 5 = 5120번
  2) 1번 움직일 때마다 n^2의 시간이 소요됨
  3) 따라서 1024 * 5 * O(n^2) = O(n^2)
"""

# 1. 각 돌들이 가질 수 있는 값을 dic 자료형으로 나타냄
stone_values = {0: 0, 2: 1, 4: 2, 8: 3, 16: 4, 32: 5, 64: 6, 128: 7, 256: 8, 512: 9, 1024: 10}

# 2. 움직이는 경우의 수를 배열로 선언 (0: 상, 1: 하, 2: 좌, 3: 우)
move = [0, 1, 2, 3]
move_cases = []

def permutation(index, list):
  global move_cases

  if len(list) == 5:
    move_cases.append(list[:])
    return

  for i in range(len(move)):
    list.append(move[i])
    permutation(i, list)
    list.pop()

# 3. 움직이는 경우의 수를 구함
permutation(0, [])


# 4. 입력 받기 (각 보드판에는 값, 합침 여부를 가진 튜플이 저장된다)
n = int(input())
board = []

for _ in range(n):
  tmp = list(map(int, input().split()))
  stones = []

  for val in tmp:
    stones.append((stone_values.get(val), False))

  board.append(stones)

# 보드를 특정 방향을 향하여 움직인 결과를 계산하는 함수를 작성
# 각 행, 열의 값을 리스트로 받으면서 중복 제거하고, 다시 비어있는 board에 결과를 매핑함
def move_board(board, dir):
  new_board = [[(0, False)] * n for _ in range(n)]

  if dir == 0:
    # 상
    for i in range(n):
      # i번째 열의 요소를 리스트로 만들기
      tmp = []
      for j in range(n):
        current = board[j][i]
        if current[0] > 0:
          # tmp가 비어있지 않고, 이전의 요소와 값이 같으며, 이전의 요소가 false일 때
          # 이전의 요소를 2배하고, True로 바꾼다.

          if not tmp:
            tmp.append(current)
          else:
            prev = tmp.pop()
            if prev[0] == current[0] and prev[1] == False:
              tmp.append((prev[0] + 1, True))
            else:
              tmp.append(prev)
              tmp.append(current)

      for j in range(len(tmp)):
        new_board[j][i] = (tmp[j][0], False)

  elif dir == 1:
    # 하
    for i in range(n):
      # i번째 열의 요소를 리스트로 만들기
      tmp = []

      for j in range(n - 1, -1, -1):
        current = board[j][i]
        if current[0] > 0:
          # tmp가 비어있지 않고, 이전의 요소와 값이 같으며, 이전의 요소가 false일 때
          # 이전의 요소를 2배하고, True로 바꾼다.

          if not tmp:
            tmp.append(current)
          else:
            prev = tmp.pop()
            if prev[0] == current[0] and prev[1] == False:
              tmp.append((prev[0] + 1, True))
            else:
              tmp.append(prev)
              tmp.append(current)

      for j in range(len(tmp)):
        new_board[n - 1 - j][i] = (tmp[j][0], False)
  elif dir == 2:
    # 좌
    for i in range(n):
      # i번째 행의 요소를 리스트로 만들기
      tmp = []

      for j in range(n):
        current = board[i][j]
        if current[0] > 0:
          # tmp가 비어있지 않고, 이전의 요소와 값이 같으며, 이전의 요소가 false일 때
          # 이전의 요소를 2배하고, True로 바꾼다.

          if not tmp:
            tmp.append(current)
          else:
            prev = tmp.pop()
            if prev[0] == current[0] and prev[1] == False:
              tmp.append((prev[0] + 1, True))
            else:
              tmp.append(prev)
              tmp.append(current)

      for j in range(len(tmp)):
        new_board[i][j] = (tmp[j][0], False)
  else:
    # 우
    for i in range(n):
      # i번째 행의 요소를 리스트로 만들기
      tmp = []

      for j in range(n - 1, -1, -1):
        current = board[i][j]
        if current[0] > 0:
          # tmp가 비어있지 않고, 이전의 요소와 값이 같으며, 이전의 요소가 false일 때
          # 이전의 요소를 2배하고, True로 바꾼다.

          if not tmp:
            tmp.append(current)
          else:
            prev = tmp.pop()
            if prev[0] == current[0] and prev[1] == False:
              tmp.append((prev[0] + 1, True))
            else:
              tmp.append(prev)
              tmp.append(current)

      for j in range(len(tmp)):
        new_board[i][n - 1 - j] = (tmp[j][0], False)
  return new_board

# board에서 최대값을 찾는 함수 선언
def find_max(board):
  result = int(1e9) * -1

  for row in board:
    for elem in row:
      result = max(result, elem[0])

  return result


max_value = int(1e9) * -1

# 경우의 수에 따라 board를 1번씩 움직이고, 최대값을 계산한다.
for case in move_cases:
  copied_board = [row[:] for row in board]

  for move_val in case:
    copied_board = move_board(copied_board, move_val)
    max_value = max(max_value, find_max(copied_board))

print(2**max_value)

