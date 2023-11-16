# 현재 칸이 청소되지 않은 경우, 현재 칸 청소
# 4칸 중 청소되지 않은 빈칸이 없는 경우
#   1. 방향 유지한채로 후진 가능 -> 한 칸 후진하고 1번
#   2. 뒤쪽 칸이 벽이라 후진 못하면 끝
# 청소는 반시계 방향으로 회전 // 바라보는 방향 기준으로 한 칸 전진

import sys
input = sys.stdin.readline

N, M = map(int, input().split())
r, c, d = map(int,input().split())

# 0 북 / 1 동 / 2 남 / 3 서
dx = [0, 1, 0, -1]
dy = [-1, 0, 1, 0]

room = []
for _ in range(N):
    room.append(list(map(int,input().split())))

visited = [[0] * M for _ in range(N)]
visited[r][c] = 1
count = 1

while True : 
    flag = 0 # 아직 청소 X
    for _ in range(4):
        d = (d + 3) % 4 # 반시계 방향
        # 전진
        ny = r + dy[d] 
        nx = c + dx[d]
        # 후진
        by = r - dy[d] 
        bx = c - dx[d]

        if room[ny][nx] == flag:
            if visited[ny][nx] == 0:
                visited[ny][nx] = 1
                r = ny
                c = nx
                count += 1
                flag = 1 #청소 O
                break
    
    if flag == 0: #for문 안감
        # 후진했는데 벽이라면
        if room[by][bx] == 1: 
            print(count)
            break
        # 후진 가능하다면
        else :
            r, c = by, bx 