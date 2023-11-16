n, m, k = map(int, input().split(' '))

array = [list(map(int, input().split(' '))) for _ in range(n)]

turn = [list(map(int, input().split(' '))) for _ in range(k)]

visit = [False] * k


answer = 5000
def find_max_row(_array):
    global answer
    for i in range(len(_array)):
        answer = min(answer, sum(_array[i]))
    

def turn_array(_turn, _array, _s):
    global turn, n, m

    r, c, s = _turn
    r, c = r - 1, c - 1
    sy, sx, ey, ex = r - s + _s, c - s + _s, r + s - _s, c + s - _s
    
    
    temp = _array[sy][sx]

    for y in range(sy, ey):
        _array[y][sx] = _array[y+1][sx]

    for x in range(sx, ex):
        _array[ey][x] = _array[ey][x+1]

    for y in range(ey, sy, -1):
        _array[y][ex] = _array[y-1][ex]

    for x in range(ex, sx, -1):
        _array[sy][x] = _array[sy][x-1]

    _array[sy][sx+1] = temp

    return _array

            
def repeat_turn_array(_turn, _array):
    global n, m
    s = _turn[2]
    ret = [[_array[i][j] for j in range(m)] for i in range(n)]
    for i in range(s):
        turn_array(_turn, ret, i)
    return ret
    

def choice_order(depth, ret):
    global visit, k
    
    if depth == k:
        find_max_row(ret)
        return

    for i in range(k):
        if visit[i]:
            continue
        visit[i] = True
        choice_order(depth+1, repeat_turn_array(turn[i], ret))
        visit[i] = False
            
    
choice_order(0, array)
print(answer)

