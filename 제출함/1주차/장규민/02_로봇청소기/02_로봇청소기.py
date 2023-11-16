n, m = map(int, input().split(' '))
r, c, d = map(int, input().split())
board = [list(map(int, input().split(' '))) for _ in range(n)]

dy, dx = [-1, 0, 1, 0], [0, 1, 0, -1]


def check_dirty(y, x):
    global board, dy, dx
    
    for i in range(4):
        ny, nx = y + dy[i], x + dx[i]
        if board[ny][nx]:
            continue
        return True
    
    return False


answer = 0
while board[r][c] != 1:
    
    if board[r][c] == 0:
        board[r][c] = 2
        answer += 1
        
    if check_dirty(r, c):
        #반시계 방향으로 90도 회전
        for i in range(1, 5):
            direction = (d - i)
            if direction < 0:
                direction = 4 + direction
                
            ny, nx = r + dy[direction], c + dx[direction]
            if board[ny][nx] != 0:
                continue
            r, c, d = ny, nx, direction
            break
    else:
        r, c = r + (-dy[d]), c + (-dx[d])
    
print(answer)

