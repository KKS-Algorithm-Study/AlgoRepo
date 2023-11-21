"""
1. 문제 접근 방식
  1) 처음에는 그리디처럼 말뛰기를 먼저하고 원숭이처럼 뛰려 했음
  2) 그러나 말처럼 다 뛰고 난 뒤에 장애물을 만났을 때 실제로는 갈 수 있음에도 목적지에 못가는 경우를 발견
  3) 그래서 방문배열에 말처럼 몇번 뛰었는지의 개념을 넣어 2차원이 아닌 3차원으로 접근하려 함
  4) 근데 구현을 못해서 답보고 코드를 작성함 ㅠㅠ

2. 시간 복잡도
  1) H * W 행렬을 모두 1번씩 탐색을 한다.
  2) 1칸을 탐색 시 최대 12번을 탐색한다 (원숭이처럼 이동 4번, 말처럼 이동 8번)
  3) 따라서 시간 복잡도는 O(H * W)이다.

3. 참고 사항
  1) 비슷한 문제 - https://www.acmicpc.net/problem/2206
"""

from sys import stdin
from collections import deque

input = stdin.readline

k = int(input())
w, h = map(int, input().split())

map = [list(map(int, input().split())) for _ in range(h)]
# visited에는 (x, y) 좌표에 k번만큼 말처럼 뛰어왔을 때의 최소 이동 횟수가 기록된다.
# k는 0번 ~ k번까지 사용될 수 있으므로 범위는 k + 1이다.
visited = [[[0] * (k + 1) for _ in range(w)] for _ in range(h)]

# 말처럼 뛰는 인덱스
horse_dx = [-1, -2, -2, -1, +1, +2, +2, +1]
horse_dy = [-2, -1, +1, +2, -2, -1, +1, +2]

# 원숭이처럼 뛰는 인덱스
dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]


def can_go(x, y, z):
    return 0 <= x < h \
        and 0 <= y < w \
        and not map[x][y] \
        and not visited[x][y][z]


queue = deque([(0, 0, 0)])
visited[0][0][0] = 1

while queue:
    now_x, now_y, now_k = queue.popleft()

    if now_x == h - 1 and now_y == w - 1:
        print(visited[now_x][now_y][now_k] - 1)
        exit(0)

    # 원숭이처럼 뛰기
    for i in range(4):
        nx = now_x + dx[i]
        ny = now_y + dy[i]

        if can_go(nx, ny, now_k):
            queue.append((nx, ny, now_k))
            # 말처럼 뛴 횟수는 유지하며 1칸 이동 (visited만 갱신)
            visited[nx][ny][now_k] = visited[now_x][now_y][now_k] + 1

    if now_k < k:
        print("kkkkk")
        # 말처럼 먼저 뛸 수 있다면 말처럼 뛰자
        for i in range(8):
            nx = now_x + horse_dx[i]
            ny = now_y + horse_dy[i]

            if can_go(nx, ny, now_k + 1):
                queue.append((nx, ny, now_k + 1))
                # 말처럼 뛴 횟수를 증가시켜야 하므로 now_k + 1의 위치에 갱신
                visited[nx][ny][now_k + 1] = visited[now_x][now_y][now_k] + 1

print(-1)
