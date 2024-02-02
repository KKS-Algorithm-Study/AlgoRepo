"""
1. 문제 접근 방식
  1) 일반 board와 적록색양용 board를 만들어 bfs로 풀이한다.

2. 시간 복잡도
  1) board의 모든 요소를 탐색하므로 O(N**2)이다.

"""
def bfs(board):
  dx = [+1, -1, +0, +0]
  dy = [+0, +0, +1, -1]
  visited = [[False] * n for _ in range(n)]
  result = 0

  for i in range(n):
    for j in range(n):
      if not visited[i][j]:
        queue = deque([(i, j)])
        visited[i][j] = True
        tmp = 1
        while queue:
          now_x, now_y = queue.popleft()

          for k in range(4):
            nx, ny = now_x + dx[k], now_y + dy[k]

            if 0 <= nx < n and 0 <= ny < n and not visited[nx][ny] and board[nx][ny] == board[i][j]:
              visited[nx][ny] = True
              queue.append((nx, ny))
              tmp += 1

        result += 1

  return result


import sys
from collections import deque

input = sys.stdin.readline

n = int(input().rstrip())
board = [list(input().rstrip()) for _ in range(n)]
red_green_board = [row[:] for row in board]

for i in range(n):
  for j in range(n):
    if red_green_board[i][j] == 'R':
      red_green_board[i][j] = 'G'

print(bfs(board), bfs(red_green_board))