n = int(input())

board = [list(map(int, input().split(' '))) for _ in range(n)]
answer = 2


def move_down(_board):
    global n

    ret = [[_board[y][x] for x in range(n)] for y in range(n)]
    visit = [[False] * n for _ in range(n)]

    for x in range(n):
        for y in range(n - 2, -1, -1):
            if ret[y][x] == 0:
                continue
            idx = y + 1
            
            while idx < n - 1 and not ret[idx][x]:
                idx += 1

            if idx == n - 1 and not ret[idx][x]:
                ret[y][x], ret[idx][x] = ret[idx][x], ret[y][x]
            elif not visit[idx][x] and ret[idx][x] == ret[y][x]:
                ret[idx][x] += ret[y][x]
                visit[idx][x] = True
                ret[y][x] = 0
            else:
                ret[y][x], ret[idx-1][x] = ret[idx-1][x], ret[y][x]

    return ret


def move_up(_board):
    global n
    
    ret = [[_board[y][x] for x in range(n)] for y in range(n)]
    visit = [[False] * n for _ in range(n)]         
    
    for x in range(n):
        for y in range(1, n):
            if ret[y][x] == 0:
                continue
            
            idx = y - 1
            
            while idx > 0 and not ret[idx][x]:
                idx -= 1

            if idx == 0 and not ret[idx][x]:
                ret[y][x], ret[idx][x] = ret[idx][x], ret[y][x]
            elif not visit[idx][x] and ret[idx][x] == ret[y][x]:
                ret[idx][x] += ret[y][x]
                visit[idx][x] = True
                ret[y][x] = 0
            else:
                ret[y][x], ret[idx+1][x] = ret[idx+1][x], ret[y][x]

    return ret
    
            
def move_left(_board):
    global n
    
    ret = [[_board[y][x] for x in range(n)] for y in range(n)]
    visit = [[False] * n for _ in range(n)]
    
    for y in range(n):
        for x in range(1, n):
            if ret[y][x] == 0:
                continue
            
            idx = x - 1
            
            while idx > 0 and not ret[y][idx]:
                idx -= 1

            if idx == 0 and not ret[y][idx]:
                ret[y][x], ret[y][idx] = ret[y][idx], ret[y][x]                
            elif not visit[y][idx] and ret[y][idx] == ret[y][x]:
                ret[y][idx] += ret[y][x]
                visit[y][idx] = True
                ret[y][x] = 0
            else:
                ret[y][x], ret[y][idx+1] = ret[y][idx+1], ret[y][x]

    return ret
            

def move_right(_board):
    global n

    ret = [[_board[y][x] for x in range(n)] for y in range(n)]    
    visit = [[False] * n for _ in range(n)]        
    
    for y in range(n):
        for x in range(n - 2, -1, -1):
            if ret[y][x] == 0:
                continue
            
            idx = x + 1
            
            while idx < n - 1 and not ret[y][idx]:
                idx += 1

            if idx == n - 1 and not ret[y][idx]:
                ret[y][x], ret[y][idx] = ret[y][idx], ret[y][x]
            elif not visit[y][idx] and ret[y][idx] == ret[y][x]:
                ret[y][idx] += ret[y][x]
                visit[y][idx] = True
                ret[y][x] = 0
            else:
                ret[y][x], ret[y][idx-1] = ret[y][idx-1], ret[y][x]

    return ret
    

def solution(depth, _board):
    global answer
    
    if depth == 5:
        for _b in _board:
            answer = max(answer, max(_b))
        return
    
    solution(depth + 1, move_up(_board))
    solution(depth + 1, move_down(_board))
    solution(depth + 1, move_left(_board))
    solution(depth + 1, move_right(_board))

    

for data in board:
    answer = max(answer, max(data))
    
solution(0, board)

print(answer)
