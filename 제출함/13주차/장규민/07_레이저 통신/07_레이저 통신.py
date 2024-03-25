from collections import deque

w, h = map(int, input().split())
board = [list(input().rstrip()) for _ in range(h)]
start = []

for i in range(h):
    for j in range(w):
        if board[i][j] == 'C':
            start.append((i, j))

INF = int(1e5)
visit = [[[INF for _ in range(4)] for j in range(w)] for i in range(h)]


def bfs(s, e):
    global visit, board

    dy, dx = (0,0,1,-1), (1,-1,0,0)
    
    que = deque()
    
    
    for i in range(4):
        visit[s[0]][s[1]][i] = 0
        que.append((s[0], s[1], i))
        
    while que:
        y, x, direction = que.popleft()

        for i in range(4):
            ny, nx = y + dy[i], x + dx[i]

            if not (0 <= ny < h and 0 <= nx < w) or board[ny][nx] == '*':
                continue

            plus = 0
            if i != direction:
                plus = 1

            if visit[ny][nx][i] <= visit[y][x][direction] + plus:
                continue
            
            visit[ny][nx][i] = min(visit[ny][nx][i], visit[y][x][direction] + plus)

            if e[0] == ny and e[1] == nx:
                continue
            
            que.append((ny, nx, i))


bfs(start[0], start[1])

y, x = start[1][0], start[1][1]
print(min(visit[y][x]))
