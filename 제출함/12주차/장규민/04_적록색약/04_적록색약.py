from collections import deque


n = int(input())

board = [list(input().rstrip()) for _ in range(n)]


def bfs(y, x, opt):
    global visit
    
    que = deque()
    dy, dx = (0,0,1,-1), (1,-1,0,0)

    que.append((y, x))
    color = board[y][x]
    visit[y][x] = True

    ret = 0

    while que:
        cur_y, cur_x = que.popleft()
        
        for i in range(4):
            ny, nx = cur_y + dy[i], cur_x + dx[i]

            if not (0 <= ny < n and 0 <= nx < n) or visit[ny][nx]:
                continue

            if opt and (color == 'B' or board[ny][nx] == 'B') and color != board[ny][nx]:
                continue

            if not opt and color != board[ny][nx]:
                continue

            visit[ny][nx] = True
            que.append((ny, nx))


for o in range(2):
    visit = [[False] * n for _ in range(n)]
    answer = 0
    
    for i in range(n):
        for j in range(n):
            if visit[i][j]:
                continue
            
            answer += 1
            bfs(i, j, o)
    print(answer, end=' ')
