"""
왼쪽에서 오른쪽, 위에서 아래쪽 방향으로 확인
↙, ↘, →, ↓
"""
board = [list(map(int, input().split(' '))) for _ in range(19)]

def is_in(y, x):
    return 0 <= y < 19 and 0 <= x < 19

def check_stone(y, x, color, mul_y, mul_x):
    global board
    ret = 0
    for i in range(19):
        ny, nx = y + mul_y * i, x + mul_x * i
        if not is_in(ny, nx) or (is_in(ny, nx) and color != board[ny][nx]):
            break
        ret += 1
        
    for i in range(1, 19):
        ny, nx = y - mul_y * i, x - mul_x * i
        if not is_in(ny, nx) or (is_in(ny, nx) and color != board[ny][nx]):
            break
        ret += 1
        
    return ret == 5

def solution():
    global board
    dy, dx = [1, 1, 0, 1], [-1, 1, 1, 0]
    for y in range(19):
        for x in range(19):
            color = board[y][x]
            for i in range(4):
                if color and check_stone(y, x, board[y][x], dy[i], dx[i]):
                    print(color)
                    if i == 0:
                        print(y + 1 + dy[i] * 4, x + 1 + dx[i] * 4)
                    else:
                        print(y + 1, x + 1)
                    return
    print(0)
                
solution()
