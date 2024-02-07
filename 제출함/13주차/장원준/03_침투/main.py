"""
1. 문제 접근 방식
  1) 0번째 행을 시작점으로 bfs를 수행한다.
  2) 탐색이 완료된 후, 맨 아래 행에 전류가 흘렀는지 확인한다.

2. 시간 복잡도
  1) 맨 위의 행을 시작점으로 bfs를 시작한다 -> O(m)
  2) 1개의 시작점에서 bfs를 수행한다 -> O(n * m)
  3) 따라서 최종 시간 복잡도는 O(n * m**2)이다.

"""
def check (start):
  queue = deque([(0, start)])
  board[0][start] = '2'

  while True:
    new_queue = deque()

    for now_x, now_y in queue:
      for i in range(4):
        nx, ny = now_x + dx[i], now_y + dy[i]

        if 0 <= nx < n and 0 <= ny < m and board[nx][ny] == '0':
          new_queue.append((nx, ny))
          board[nx][ny] = '2'

    if not new_queue:
      break

    queue = new_queue


import sys
from collections import deque

dx = [+1, -1, +0, +0]
dy = [+0, +0, +1, -1]

input = sys.stdin.readline

n, m = map(int, input().split())

board = [list(input().rstrip()) for _ in range(n)]
is_success = 'NO'

for i in range(m):
  if board[0][i] == '0':
    check(i)

if board[n - 1].count('2') > 0:
  is_success = 'YES'

print(is_success)