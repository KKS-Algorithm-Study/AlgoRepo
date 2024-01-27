board = [list(map(int, input().split(' '))) for _ in range(9)]

blank = []
for i in range(9):
    for j in range(9):
        if board[i][j] == 0:
            blank.append((i,j))

def check(row, col, board, num):
    for i in board[row]:
        if i == num:
            return False
    cols = [i[col] for i in board]
    for i in cols:
        if i == num:
            return False
    area = (row//3 * 3, col//3 * 3)
    for i in range(area[0], area[0]+3):
        for j in range(area[1], area[1]+3):
            if board[i][j] == num:
                return False
    return True

def dfs(depth, n, board):
    if depth == n:
        for i in range(9):
            print(*board[i])
        exit(0)

    row, col = blank[depth]
    
    for num in range(1,10):
        if check(row, col, board, num):
            board[row][col] = num
            dfs(depth+1, n, board)
            board[row][col] = 0

dfs(0, len(blank), board)