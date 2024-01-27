"""
1. 문제 접근 방식
  1) 곰팡이를 퍼뜨리다보면 특정 칸에 곰팡이가 있었을 때 하루 주기로 폈다 없어졌다 하는 규칙을 찾을 수 있다.
  2) 그러나 곰팡이가 퍼질 수 없었다면 그 위치의 곰팡이는 사라진다.
  3) 따라서 visited 배열을 3차원 배열로 선언하여 풀이한다.

2. 시간 복잡도
  1) ..??

"""
import sys
from collections import deque

dx = [+1, +2, +1, +2, -1, -2, -1, -2]
dy = [+2, +1, -2, -1, -2, -1, +2, +1]

input = sys.stdin.readline
n, m, k, t = map(int, input().split())

room = [[0] * n for _ in range(n)]
visited = [[[False] * 2 for _ in range(n)] for _ in range(n)]
queue = deque()

# 곰팡이의 좌표
for _ in range(m):
  x, y = map(int, input().split())
  queue.append((x - 1, y - 1, 0))
  visited[x - 1][y - 1][0] = True

while queue:
  x, y, day = queue.popleft()

  # t일이 지나고 체크해야하므로 t일이 지났다면 continue
  if day >= t:
    continue

  is_spread = False

  for i in range(8):
    nx, ny = x + dx[i], y + dy[i]

    if 0 <= nx < n and 0 <= ny < n:
      # 홀수번째 날이니, 짝수번째 날이니?
      next = (day + 1) % 2

      if visited[nx][ny][next] == True:
        continue

      visited[nx][ny][next] = True
      queue.append((nx, ny, day + 1))
      is_spread = True

  # x, y를 꺼냈는데 퍼진 곳이 없으면 그 다음에 x,y로 못돌아옴
  if not is_spread:
    visited[x][y][day % 2] = False

# 청소 시스템이 검사 시작
result = 'NO'

for _ in range(k):
  x, y = map(int, input().split())

  if visited[x - 1][y - 1][t % 2]:
    result = 'YES'
    break

print(result)