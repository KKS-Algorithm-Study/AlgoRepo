n, m, k = map(int, input().split())

board = [list(map(int, input().split())) for _ in range(n)]

visit = [[False] * m for _ in range(n)]

answer = -40001


def check_visit(y, x):
    global visit
    
    dy, dx = (0,0,1,-1), (1,-1,0,0)

    for i in range(4):
        ny, nx = y + dy[i], x + dx[i]
        
        if not (0 <= ny < n and 0 <= nx < m):
            continue
        
        if visit[ny][nx]:
            return True
        
    return False
            


def backtracking(depth, start_y, start_x, total):
    global answer, visit
    
    if depth == k:
        answer = max(answer, total)
        return

    for y in range(start_y, n):
        for x in range(m):
            if visit[y][x] or check_visit(y, x):
                continue

            visit[y][x] = True
            backtracking(depth + 1, y, x, total + board[y][x])
            visit[y][x] = False
            

backtracking(0, 0, 0, 0)
print(answer)
