"""
1. 문제 접근 방식
  1) 3 <= N, M <= 50 & 1 <= K <= 6인 것 보고 일단 사이즈가 작다고 생각함
  2) 그래서 단순 구현으로 풀려고 시도함
  3) 행렬을 회전하는 무언가의 공식 같은게 있는지 알고 규칙을 찾으려 노력했으나 없었고, 그것 땜에 오래 걸림
  4) 파이썬의 배열 깊은 복사 코드를 숙지 못하여 오래 걸림
  5) 순열 구할 때 swap을 가지고 구하는게 익숙치 않아 오래 걸림
  6) 처음에는 rotate 부분이 자꾸 틀려서 답을 찾아봤더니, 내가 생각한 아이디어랑 똑같아서 rotate 부분을 고침

2. 시간 복잡도
  1) 쿼리의 갯수는 최대 6! = 720번이며 매 회전마다 배열을 전체 탐색하여 최소값을 계산해야 한다.
  2) 1번 최소값을 계산할 때는 O(N * M) 시간이 소요된다.
  3) 1번 rotate를 할 때는 기존 배열에 접근하므로 O(1)인가..??
  4) 따라서 720 * O(N * M) * O(1) = O(N * M) 인가용?
"""
n, m, k = map(int, input().split())
matrix = [list(map(int, input().split())) for _ in range(n)]
rotate_infos = [list(map(int, input().split())) for _ in range(k)]

# 배열의 최소값을 구하는 함수
def matrix_value(matrix):
  result = int(1e9)

  for row in matrix:
    result = min(result, sum(row))

  return result

# 배열을 돌리는 함수
def rotate(matrix, rcs):
  r, c, s = rcs

  r -= 1
  c -= 1

  # 리스트 슬라이싱을 사용한 깊은 복사 (deepcopy는 느리다고 함)
  copied_matrix = [row[:] for row in matrix]

  while r - s < r + s:
    # 윗변 (좌 -> 우)
    # 시작 (matrix[r - s][c - s]) -> 끝 (matrix[r - s][c + s])
    for i in range(2 * s):
      copied_matrix[r - s][c - s + i + 1] = matrix[r - s][c - s + i]

    # 우변 (상 -> 하)
    # 시작 (matrix[r - s][c + s]) -> 끝 (matrix[r + s][c + s])
    for i in range(2 * s):
      copied_matrix[r - s + i + 1][c + s] = matrix[r - s + i][c + s]

    # 아랫변 (우 -> 좌)
    # 시작 (matrix[r + s][c + s]) -> 끝 (matrix[r + s][c - s])
    for i in range(2 * s):
      copied_matrix[r + s][c + s - i - 1] = matrix[r + s][c + s - i]

    # 좌변 (하 -> 상)
    # 시작 (matrix[r + s][c - s]) -> 끝 (matrix[r - s][c - s])
    for i in range(2 * s):
      copied_matrix[r + s - i - 1][c - s] = matrix[r + s - i][c - s]

    # 정사각형의 범위 줄여주기
    s -= 1

  return copied_matrix

# swap을 이용한 순열
# 배열의 r번째 인덱스까지가 순열로 뽑힌 요소들이다.
def permutation(n, r, depth):
  global min_value

  if depth == r:
    m = matrix
    for rcs in rotate_infos:
      m = rotate(m, rcs)
    min_value = min(min_value, matrix_value(m))
  else:
    for i in range(depth, n):
      rotate_infos[depth], rotate_infos[i] = rotate_infos[i], rotate_infos[depth]
      permutation(n, r, depth + 1)
      rotate_infos[depth], rotate_infos[i] = rotate_infos[i], rotate_infos[depth]

min_value = int(1e9)

permutation(k, k, 0)

print(min_value)