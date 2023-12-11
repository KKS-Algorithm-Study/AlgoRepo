from collections import deque
from heapq import heappush, heappop

n, m = map(int, input().split())

board = [list(map(int, input().split())) for _ in range(n)]
graph = []


def bfs(y, x, _visit, _number):
    global n, m, board
    
    que = deque()
    que.append((y, x))
    _visit[y][x] = True
    board[y][x] = _number
    
    dy, dx = (0,0,1,-1), (1,-1,0,0)

    while que:
        cur_y, cur_x = que.popleft()

        for i in range(4):
            next_y, next_x = cur_y + dy[i], cur_x + dx[i]

            if 0 > next_y or next_y >= n or 0 > next_x or next_x >= m:
                continue

            if _visit[next_y][next_x] or board[next_y][next_x] == 0:
                continue

            que.append((next_y, next_x))
            _visit[next_y][next_x] = True
            board[next_y][next_x] = _number


def connect_horizon(y, x):
    global n, m, board, graph
    
    dist = 0

    sour = board[y][x]
    for i in range(x - 1, -1, -1):
        des = board[y][i]
        
        if des == sour:
            break

        if des == 0:
            dist += 1
            continue

        if dist == 1:
            break
        
        heappush(graph, (dist, sour, des))
        break
        
    dist = 0
    for i in range(x + 1, m):
        des = board[y][i]
        if des == sour:
            break

        if des == 0:
            dist += 1
            continue

        if dist == 1:
            break
        
        heappush(graph, (dist, sour, des))
        break


def connect_vertical(y, x):
    global n, m, board, graph

    dist = 0

    sour = board[y][x]
    for i in range(y - 1, -1, -1):
        des = board[i][x]
        
        if des == sour:
            break

        if des == 0:
            dist += 1
            continue

        if dist == 1:
            break
        
        heappush(graph, (dist, sour, des))
        break
        
    dist = 0
    for i in range(y + 1, n):
        des = board[i][x]
        if des == sour:
            break

        if des == 0:
            dist += 1
            continue

        if dist == 1:
            break
        
        heappush(graph, (dist, sour, des))
        break


def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])

    return parents[x]


def union(x, y):
    global parents

    px, py = find_parent(x), find_parent(y)

    if px == py:
        return

    parents[py] = px


number = 1
visit = [[False] * m for _ in range(n)]

# 섬 번호 붙이기
for i in range(n):
    for j in range(m):
        if board[i][j] and not visit[i][j]:
            bfs(i, j, visit, number)
            number += 1


# 섬 연결
for i in range(n):
    for j in range(m):
        if board[i][j]:
            connect_vertical(i, j)
            connect_horizon(i, j)

#MST
answer = 0
parents = [i for i in range(number)]
visit = [[False] * number for _ in range(number)]

while graph:
    cost, sour, des = heappop(graph)
    if visit[sour][des]:
        continue
    
    sour_parent, des_parent = find_parent(sour), find_parent(des)

    if sour_parent == des_parent:
        continue

    union(sour_parent, des_parent)
    visit[sour][des] = visit[des][sour] = True
    answer += cost
    

# 다 같은 집합에 존재하는 확인
parent = find_parent(1)
flag = True
for child in range(2, number):
    if parent != find_parent(child):
        flag = False
        break

    
print(answer if flag else -1)
