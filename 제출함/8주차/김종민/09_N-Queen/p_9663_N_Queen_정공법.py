global N, cnt
N = int(input())
cnt = 0

def isValid(board, row, col):
    global N
    rows = board[row]
    
    for r in rows:
        if r == 1:
            return False
    
    for i in range(1, col+1):
        nc = col-i
        nr1 = row - i
        nr2 = row + i

        if 0 <= nr1 and board[nr1][nc] == 1:
            return False        
        if nr2 < N and board[nr2][nc] ==1:
            return False
        
    return True

def dfs(board, depth):
    global N, cnt
    if depth == N:
        cnt += 1
        return

    for i in range(N):
        if isValid(board, i, depth):
            board[i][depth] = 1
            dfs(board, depth+1)
            board[i][depth] = 0

board = [([0] * N) for _ in range(N)]

dfs(board, 0)
print(cnt)