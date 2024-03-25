from collections import deque

n, m = map(int, input().split())

board = [list(input().rstrip()) for _ in range(n)]
visit = [[False] * m for _ in range(n)]

def bfs(x):
    global board, visit, n, m
    
    que = deque()

    que.append((0, x))
    visit[0][x] = True
    
    dy, dx = (0, 0, -1, 1), (1, -1, 0, 0)

    while que:
        cur_y, cur_x = que.popleft()
        if cur_y == n - 1:
            return True

        for i in range(4):
            ny, nx = cur_y + dy[i], cur_x + dx[i]

            if not (0 <= ny < n and 0 <= nx < m) or visit[ny][nx] or board[ny][nx] == '1':
                continue

            visit[ny][nx] = True
            que.append((ny, nx))

    return False


answer = 'NO'
for x in range(m):
    if visit[0][x]:
        continue

    if bfs(x):
        answer = 'YES'
        break

print(answer)
