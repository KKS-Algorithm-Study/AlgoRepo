"""
1. 문제 접근 방식 (답 보고 품)
  1) bfs로 풀이
  2) 방문 배열에는 그 지점을 방문했을 때의 체력을 기록함
    ** 이미 방문했어도 다시 방문했을 때의 체력이 높으면 그 체력으로 바꿔줌

2. 시간 복잡도
  1) S와 E를 찾기 위해 map을 순회한다. -> O(n * n)
  2) bfs를 수행한다. -> O(4 * n * n)
  3) 따라서 최종 시간 복잡도는 O(n * n)이다.

** 아직 visited 배열을 T/F가 아닌 숫자로 하는 것에 약한 듯.
** T/F 방문 배열이면 방문을 하거나 안하거나를 처리한다.
** 그러나 특정 조건을 기록하는 방문 배열이면 다시 방문을 할 수는 있으며
    조건 분기에 따라 큐에 요소를 추가할 수 있다.
    따라서 방문 배열을 계속 갱신을 해줘야 하는 문제라면 T/F가 아닌 특정 값을 저장하는 것을 생각하자.
"""
import sys
from collections import deque

input = sys.stdin.readline

INF = int(1e9)
dx = [+1, -1, +0, +0]
dy = [+0, +0, +1, -1]

n, h, d = map(int, input().split())
map = [list(input().rstrip()) for _ in range(n)]

umbrellas = []
start, end = (0, 0), (0, 0)
answer = -1

for i in range(n):
  for j in range(n):
    if map[i][j] == 'S':
      start = (i, j)
    elif map[i][j] == 'E':
      end = (i, j)

# 특정 지점을 방문했을 때의 체력을 기록
visited = [[0] * n for _ in range(n)]
start_x, start_y = start
visited[start_x][start_y] = h

queue = deque([(start_x, start_y, h, 0, 0)])

while queue:
  now_x, now_y, now_h, now_d, dist = queue.popleft()

  if (now_x, now_y) == end:
    answer = dist
    break

  for i in range(4):
    nx, ny, nh, nd = now_x + dx[i], now_y + dy[i], now_h, now_d

    if 0 <= nx < n and 0 <= ny < n:
      if map[nx][ny] != 'E':
        if map[nx][ny] == 'U':
          # 우산을 만나면 교체
          nd = d

        # 우산이 있는 곳도 비가 내림
        if nd > 0:
          nd = nd - 1
        else:
          nh = nh - 1

      # 체력을 다 쓰면 더 이상 진행 x
      if nh == 0:
        continue

      if visited[nx][ny] < nh:
        visited[nx][ny] = nh
        queue.append((nx, ny, nh, nd, dist + 1))

print(answer)