"""
첫 제출 : 풀긴 풀었지만 코드가 너무 더러워서 리팩토링이 필요할 것 같음,,,

문제 접근 방식 :
    1. 방향마다 루프를 돌아야되는 순서를 정한다.
        L => Row - Col / 0, N
        R => Row - Col / N, 0
        T => Col - Row / 0, N
        B => Col - Row / N, 0
    
    2. loop는 cur가 벽을 만날 때(board의 끝까지)까지 돈다
    3. cur가 숫자일 때 다음 숫자까지 탐색한다.
    4-1. 
        if 다음에 만난 수가 동일한 숫자 라면:
            cur에 *2를 해주고 다음에 만난 숫자를 0으로 만들고 cur를 1증가시킨다
    4-2.
        else:
            cur = k
    5. cur에서 부터 반댓방향으로 탐색을 진행하고 가장 앞쪽 0을 찾아 해당 위치에 값을 위치시키고 2부터 반복.

시간 복잡도:
    모든 방향으로 최대 5번까지 움직일 수 있기 때문에 4^5
    1번 움직일 때마다 N*N 을 탐색하므로 N^2
    움직이는 과정에서 제일 앞으로 옮기는 작업 N(N+1)/2 * N

    (N^2 + N(N+1)/2 * N) * 4^5 = 4,710,400
"""

N = int(input())

board = [list(map(int, input().split())) for _ in range(N)]

answer = -1

# L R T B인데 탐색방향은 반대임
dr = [0,0,1,-1]
dc = [1,-1,0,0]

def merge(board, d):
    newBoard = [element[:] for element in board]
    
    if d == 0:                                                                               
        for i in range(N): # Left
            cur = 0
            end = N
            
            while cur != end:
                if(newBoard[i][cur] > 0):
                    tmp = cur
                    curVal = newBoard[i][cur]
                    for _ in range(N-cur-1):
                        cur += 1
                        if newBoard[i][cur] > 0:
                            if newBoard[i][cur] == curVal:
                                newBoard[i][cur] = 0
                                curVal *= 2
                            else:
                                cur -= 1
                            break

                    for k in range(0, tmp):
                        if newBoard[i][k] == 0:
                            newBoard[i][tmp] = 0
                            newBoard[i][k] = curVal
                            break
                    else:
                        newBoard[i][tmp] = curVal
                cur +=1
    
    elif d == 1:
        for i in range(N): # Left
            cur = N-1
            end = -1
            
            while cur != end:
                if(newBoard[i][cur] > 0):
                    tmp = cur
                    curVal = newBoard[i][cur]
                    for _ in range(cur):
                        cur -= 1
                        if newBoard[i][cur] > 0:
                            if newBoard[i][cur] == curVal:
                                newBoard[i][cur] = 0
                                curVal *= 2
                            else:
                                cur += 1
                            break

                    for k in range(N-1, tmp-1,-1):
                        if newBoard[i][k] == 0:
                            newBoard[i][tmp] = 0
                            newBoard[i][k] = curVal
                            break
                    else:
                        newBoard[i][tmp] = curVal
                cur -=1

    elif d == 2:
        for i in range(N): # TOP
            cur = 0
            end = N
            
            while cur != end:
                if(newBoard[cur][i] > 0):
                    tmp = cur
                    curVal = newBoard[cur][i]
                    for _ in range(N-cur-1):
                        cur += 1
                        if newBoard[cur][i] > 0:
                            if newBoard[cur][i] == curVal:
                                newBoard[cur][i] = 0
                                curVal *= 2
                            else:
                                cur -= 1
                            break

                    for k in range(0, tmp):
                        if newBoard[k][i] == 0:
                            newBoard[tmp][i] = 0
                            newBoard[k][i] = curVal
                            break
                    else:
                        newBoard[tmp][i] = curVal
                cur +=1

    elif d == 3:
        for i in range(N): # Bottom
            cur = N-1
            end = -1
            
            while cur != end:
                if(newBoard[cur][i] > 0):
                    tmp = cur
                    curVal = newBoard[cur][i]
                    for _ in range(cur):
                        cur -= 1
                        if newBoard[cur][i] > 0:
                            if newBoard[cur][i] == curVal:
                                newBoard[cur][i] = 0
                                curVal *= 2
                            else:
                                cur += 1
                            break

                    for k in range(N-1, tmp-1,-1):
                        if newBoard[k][i] == 0:
                            newBoard[tmp][i] = 0
                            newBoard[k][i] = curVal
                            break
                    else:
                        newBoard[tmp][i] = curVal
                cur -=1
        
    return newBoard
    

def dfs(depth, board):
    global answer
    if depth == 5:
        for row in board:
            answer = max(answer, max(row))
        return
    
    for i in range(4):
        newBoard = merge(board, i)
        dfs(depth + 1, newBoard)
    

dfs(0, board)
print(answer)