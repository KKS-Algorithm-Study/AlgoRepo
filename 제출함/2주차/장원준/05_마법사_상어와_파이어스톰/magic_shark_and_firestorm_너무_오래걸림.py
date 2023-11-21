"""
1. 문제 접근 방식
  1) 크게 ㅌ개의 문제로 나눔
    a) 부분 배열을 시계방향 90도로 회전한다. -> 인덱스를 적절하게 사용하여 회전한다...
    b) 얼음을 감소시킨다. -> 미세먼지 확산 문제와 비슷하게 배열을 전체 복사 후 감소시킨다.
    c) 전체 얼음 수를 계산한다. -> 이중 for문을 사용한 2차원 배열의 sum 계산
    d) 가장 넓은 영역의 갯수를 계산한다. -> bfs를 사용하여 계산

  2) 그러나 말처럼 다 뛰고 난 뒤에 장애물을 만났을 때 실제로는 갈 수 있음에도 목적지에 못가는 경우를 발견
  3) 그래서 방문배열에 말처럼 몇번 뛰었는지의 개념을 넣어 2차원이 아닌 3차원으로 접근하려 함
  4) 근데 구현을 못해서 답보고 코드를 작성함 ㅠㅠ

2. 시간 복잡도
  1) 총 Q번 동안 배열 회전과 얼음 감소 로직을 수행한다.
  2) 배열 회전 -> O(n^4)
  3) 얼음 감소 -> O(4 * 2**2n)
  4) 모든 얼음의 합 계산 -> O(2**2n)
  5) 최대 영역 찾기 -> O(2**2n)
  6) 따라서 O(Q * n^4 * 4 * 2**2n + 2**2n + 2**2n) = O(Q * n**4)
"""

from collections import deque
from sys import stdin

input = stdin.readline


# 2차원 행렬의 좌표를 1차원 배열의 인덱스로 매핑
def to_array_index(x, y, length):
    return x * length + y


# 1차원 배열의 인덱스를 2차원 행렬의 좌표로 매핑
def to_matrix_index(index, length):
    return index // length, index % length


# 2차원 행렬의 좌표를 시계방향으로 회전한 좌표로 매핑
def convert_matrix_index(x, y, length):
    # length 길이를 가지는 배열 안에서, 시계 방향으로 90 회전하는 공식은 다음과 같다.
    #   (x, y) -> (y, lentgh - 1 - x) 인덱스로 매핑됨.
    # 다만 주의해야 할 점은 부분 배열일 때는 out of index 에러가 날 수 있으므로
    # 반드시 전체 혹은 부분 배열의 왼쪽 모서리를 (0, 0)으로 취급할 수 있도록
    # 인덱스를 조정 후 변환해야 한다.
    return y, length - 1 - x


# 부분 배열로 돌리는 함수
def rotate(board, level, L):
    copied_board = board[:]
    length = 2 ** level

    # 전체 행에 대해 반복
    for i in range(0, L - 1, length):
        # 전체 열에 대해 반복
        for j in range(0, L - 1, length):
            # (i, j) -> 각 부분 행렬의 왼쪽 모서리의 좌표가 됨
            for x in range(i, i + length):
                for y in range(j, j + length):
                    # (x, y) -> 부분 행렬의 좌표가 됨
                    # 부분 행렬의 2차원 좌표를 회전했을 떄의 좌표로 변환
                    # 주의할 점은 "부분" 행렬이므로 length의 길이로 그냥 계산하면 index outbound에러 뜸
                    # ex. 부분 배열의 좌표는 (3, 3)인데 length는 2일 때
                    # 따라서 (i, j)만큼 줄여주고 회전 후 다시 (i, j)만큼 더해주자
                    nx, ny = convert_matrix_index(x - i, y - j, length)
                    nx_array_index = to_array_index(nx + i, ny + j, L)
                    # 변환한 1차원 배열에 회전한 요소를 매핑
                    copied_board[nx_array_index] = board[to_array_index(x, y, L)]
    return copied_board


def fire_storm(board):
    copied_board = board[:]

    for i in range(len(board)):
        if visited[i] or copied_board[i] == 0:
            continue

        around_ice_count = 0

        for j in range(4):
            index = i + direction[j]
            if 0 <= index < len(board) and board[index] > 0:
                around_ice_count += 1

        if around_ice_count < 3:
            copied_board[i] -= 1

    return copied_board


n, q = map(int, input().split())

n = 2 ** n
L = n + 1

# 입력 받을 2차원 행렬을 1차원 배열로 풀기 위한 작업 진행
direction = [1, -1, L, -L]  # 1차원 배열에서 2차원 행렬의 상하좌우를 체크할 때 쓰이는 방향 배열
board = [0] * n * L
visited = [False] * n * L
widest_count = 0

# 2차원 배열에 대한 값을 입력 받으며 1차원 배열에 매핑
for i in range(0, n * L, L):
    board[i:i + n] = map(int, input().split())
    visited[i + n] = True  # 1차원 배열의 벽으로 취급할 위치는 True로 초기화

# 입력받은 level마다 파이어스톰 시전
for level in list(map(int, input().split())):
    board = rotate(board, level, L)
    board = fire_storm(board)

# 가장 넓은 영역의 갯수를 bfs로 계산
for i in range(len(board)):
    if visited[i] or board[i] == 0:
        continue

    queue = deque([i])
    visited[i] = True
    count = 0

    while queue:
        now = queue.popleft()
        count += 1

        for j in range(4):
            nx = now + direction[j]

            if 0 <= nx < len(board) and not visited[nx] and board[nx] > 0:
                visited[nx] = True
                queue.append(nx)

    widest_count = max(widest_count, count)

print(str(sum(board)) + '\n' + str(widest_count))
