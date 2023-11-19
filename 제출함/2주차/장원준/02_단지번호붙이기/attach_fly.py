"""
1. 문제 접근 방식
  1) 조건에 맞는 인접한 영역이 1개의 단지로 묶이므로 다음과 같이 풀이
  2) 입력 받은 행렬과 같은 방문 배열 선언
  3) 방문하지 않은 위치라면 bfs 탐색 시작
  4) 인접한 요소를 방문할 때 카운트를 1씩 증가하고, 탐색이 완료되면 리스트에 추가

2. 시간 복잡도
  1) N * N 행렬을 모두 1번씩 탐색을 한다.
  2) 만약 방문하지 않은 위치라면 탐색을 시작하는데 그 탐색은 최대 N번까지 할 수 있다.
  2) 따라서 시간 복잡도는 O(N ** 3)이다.
"""
import sys
from collections import deque

n = int(sys.stdin.readline().rstrip())
map = [list(map(int, sys.stdin.readline().rstrip())) for _ in range(n)]
visited = [[False] * n for _ in range(n)]

# 상, 하, 좌, 우
dx = [-1, +1, +0, +0]
dy = [+0, +0, -1, +1]

sections = []

for i in range(n):
    for j in range(n):
        # 집이 있는 곳이고 방문하지 않은 곳이면 단지 계산을 한다.
        if map[i][j] and not visited[i][j]:
            queue = deque([(i, j)])
            visited[i][j] = True
            num_house = 1

            while queue:
                now_x, now_y = queue.popleft()

                # 현재 점을 기준으로 사방을 확인
                for k in range(4):
                    nx = now_x + dx[k]
                    ny = now_y + dy[k]

                    if 0 <= nx < n and 0 <= ny < n and map[nx][ny] == 1 and not visited[nx][ny]:
                        queue.append((nx, ny))
                        visited[nx][ny] = True
                        num_house += 1

            sections.append(num_house)

print(len(sections))

sections.sort()

for cnt in sections:
    print(cnt)