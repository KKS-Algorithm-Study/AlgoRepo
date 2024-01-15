from collections import deque

n = int(input())

ocean = [list(map(int, input().split())) for _ in range(n)]

baby_shark = ()
count = 0

for i in range(n):
    for j in range(n):
        if ocean[i][j] == 9:
            baby_shark = (i, j, 0)
            continue

        if ocean[i][j]:
            count += 1


def bfs(y, x, size):
    global ocean, count, n

    dy, dx = (0,0,1,-1), (1,-1,0,0)

    visit = [[False] * n for _ in range(n)]

    que = deque([(y, x, 0)])
    visit[y][x] = True

    pos = ()
    dist = n * n

    while que:
        cur_y, cur_x, cnt = que.popleft()

        for i in range(4):
            ny, nx = cur_y + dy[i], cur_x + dx[i]

            if not (0 <= ny < n and 0 <= nx < n) or ocean[ny][nx] > size or visit[ny][nx]:
                continue

            visit[ny][nx] = True
            
            if ocean[ny][nx] == size or ocean[ny][nx] == 0:
                que.append((ny, nx, cnt + 1))
                continue

            if cnt + 1 > dist:
                continue

            if not pos or dist > cnt + 1:
                dist = cnt + 1
                pos = (ny, nx, cnt + 1)
                continue

            if pos[0] < ny:
                continue
        
            if pos[0] == ny and pos[1] <= nx:
                continue

            pos = (ny, nx, cnt + 1)

    return pos


def solution():
    global baby_shark, count
    
    answer = 0
    size = 2
    eaten_count = 0
    
    while count:
        y, x, cnt = baby_shark
        ocean[y][x] = 0
        
        baby_shark = bfs(y, x, size)

        if not baby_shark:
            break

        eaten_count += 1
        count -= 1
        answer += baby_shark[2]

        if eaten_count == size:
            size += 1
            eaten_count = 0
        
    print(answer)   


solution()
