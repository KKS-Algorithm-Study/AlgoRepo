from collections import deque

n, m = map(int, input().split())
board = [list(input().rstrip()) for _ in range(n)]

water = deque()
hedgehog = deque()

for i in range(n):
    for j in range(m):
        if board[i][j] == '*':
            water.append((i, j, 0))
            continue

        if board[i][j] == 'S':
            hedgehog.append((i, j, 0))


def move(que, is_water, cur):
    global board

    dy, dx = (0,0,1,-1), (1,-1,0,0)

    for _ in range(len(que)):
        y, x, cnt = que.popleft()

        for i in range(4):
            ny, nx = y + dy[i], x + dx[i]


            if not (0 <= ny < n and 0 <= nx < m) or board[ny][nx] == 'X':
                continue

            if not is_water and board[ny][nx] == 'D':
                return cnt + 1

            if is_water and (board[ny][nx] == 'D' or board[ny][nx] == '*'):
                continue

            if not is_water and board[ny][nx] != '.':
                continue

            que.append((ny, nx, cnt + 1))
            board[ny][nx] = cur

    return -1


def bfs():
    global board, water, hedgehog


    while hedgehog:
        move(water, True, '*')
        cnt = move(hedgehog, False, 'S')

        if cnt != -1:
            return cnt
        
    return -1


answer = bfs()
print(answer if answer != -1 else 'KAKTUS')
