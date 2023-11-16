from collections import deque

n, m, t = map(int, input().split(' '))
board = [list(map(int, input().split(' '))) for _ in range(n)]
dy, dx = [0,0,1,-1], [1,-1,0,0]

"""
*공기 청정기의 위치에서 일직석으로 가다 벽을 만나면 바람 회전*
1. 미세먼지 퍼질양 구하기: (빠질 먼지 수, 좌표) 를 큐에 담기
2. 미세먼지 확산: 1번의 큐를 이용하여 빼기, 더하기 순서대로 연산
"""

air_cleaner = []

for i in range(n):
    for j in range(m):
        if board[i][j] != -1:
            continue
        air_cleaner.append((i, j))

def is_in(y, x):
    global n, m
    return 0 <= y < n and 0 <= x < m


def check_and_count_around(y, x):
    global board, dy, dx

    ret = 0
    
    for i in range(4):
        ny, nx = y + dy[i], x + dx[i]
        if not is_in(ny, nx) or board[ny][nx] == -1:
            continue
        
        ret += 1
        
    return ret


def calculate_dirt():
    global que, n, m, board

    for i in range(n):
        for j in range(m):
            if board[i][j] == -1 or not board[i][j]:
                continue
            cnt = check_and_count_around(i, j)
            # 좌표, 빼야할 값, 추가할 값
            add_value = board[i][j] // 5
            que.append((i, j, add_value * cnt, add_value))
            

def diffusion_dirt():
    global board, dy, dx, que

    while que:
        y, x, subtract, add = que.popleft()

        board[y][x] -= subtract
        
        for i in range(4):
            ny, nx = y + dy[i], x + dx[i]
            if not is_in(ny, nx) or board[ny][nx] == -1:
                continue
            
            board[ny][nx] += add


def clean_dirt_1(pos):
    global n, m, board

    start_y, start_x = pos

    for y in range(start_y - 1, 0, -1):
        board[y][start_x] = board[y-1][start_x]

    for x in range(0, m - 1):
        board[0][x] = board[0][x+1]

    for y in range(0, start_y + 1):
        board[y][m - 1] = board[y+1][m-1]

    for x in range(m - 1, 1, -1):
        board[start_y][x] = board[start_y][x-1]
    board[start_y][1] = 0
        
    
def clean_dirt_2(pos):
    global n, m, board

    start_y, start_x = pos

    for y in range(start_y + 1, n - 1):
        board[y][start_x] = board[y+1][start_x]
        
    for x in range(0, m - 1):
        board[n - 1][x] = board[n - 1][x+1]

    for y in range(n - 1, start_y, -1):
        board[y][m - 1] = board[y-1][m-1]

    for x in range(m - 1, 0, -1):
        board[start_y][x] = board[start_y][x-1]
    board[start_y][1] = 0
    

que = deque()
for _ in range(t):
    calculate_dirt()
    diffusion_dirt()
    # 여기까지는 완성
    clean_dirt_1(air_cleaner[0])
    clean_dirt_2(air_cleaner[1])


answer = 2

for i in range(n):
    answer += sum(board[i])

print(answer)    
