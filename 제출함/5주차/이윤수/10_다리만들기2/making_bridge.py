import sys
from collections import defaultdict, deque
from heapq import heapify, heappop
input = sys.stdin.readline
islands = defaultdict(list)
is_island = defaultdict(int)
bridges =defaultdict(lambda: float('inf'))
max_row, max_col = map(int, input().split())
board = [list(map(int, input().split())) for _ in range(max_row)]
visited = [[0 for _ in range(max_col)] for _ in range(max_row)]

def bfs(num, row, col):
    
    q = deque()
    q.append([row, col])
    visited[row][col] = 1
    
    while q:
        cur_row, cur_col = q.popleft()
        islands[num].append((cur_row, cur_col))
        is_island[(cur_row,cur_col)] = num
        
        for dr, dc in [(-1,0), (1,0), (0,1), (0,-1)]:
            n_row, n_col = cur_row + dr, cur_col + dc
            if n_row < 0 or n_row >=max_row or n_col <0 or n_col>=max_col: continue
            if visited[n_row][n_col]: continue
            if board[n_row][n_col]:
                q.append([n_row, n_col])
                visited[n_row][n_col] = 1

def get_possible_bridges(num, row, col):
    #상
    tmp_row = row -1
    tmp_dist = 0
    while tmp_row >=0:
        if is_island[(tmp_row, col)] == num: break
        elif is_island[(tmp_row, col)] !=0:
            if tmp_dist >=2:
                bridge = tuple(sorted((num, is_island[(tmp_row, col)])))
                bridges[bridge] = min(bridges[bridge], tmp_dist)
            break
        tmp_dist+=1
        tmp_row-=1

    #하
    tmp_row = row +1
    tmp_dist = 0
    while tmp_row < max_row:
        if is_island[(tmp_row, col)] == num: break
        elif is_island[(tmp_row, col)] !=0:
            if tmp_dist >=2:
                bridge = tuple(sorted((num, is_island[(tmp_row, col)])))
                bridges[bridge] = min(bridges[bridge], tmp_dist)
            break
        tmp_dist+=1
        tmp_row+=1

    #좌
    tmp_col = col -1
    tmp_dist = 0
    while tmp_col >=0:
        if is_island[(row, tmp_col)] == num: break
        elif is_island[(row, tmp_col)] != 0:
            if tmp_dist >=2:
                bridge = tuple(sorted((num, is_island[(row, tmp_col)])))
                bridges[bridge] = min(bridges[bridge], tmp_dist)
            break
        tmp_dist+=1
        tmp_col-=1

    #우
    tmp_col = col +1
    tmp_dist = 0
    while tmp_col < max_col:
        if is_island[(row, tmp_col)] == num: break
        elif is_island[(row, tmp_col)] != 0:
            if tmp_dist >=2:
                bridge = tuple(sorted((num, is_island[(row, tmp_col)])))
                bridges[bridge] = min(bridges[bridge], tmp_dist)
            break
        tmp_dist+=1
        tmp_col+=1
    
island_cnt = 0
for i in range(max_row):
    for j in range(max_col):
        if board[i][j] and not visited[i][j]:
            island_cnt+=1
            bfs(island_cnt,i,j)
            

for island_num in range(1, island_cnt+1):
    for row, col in islands[island_num]:
        get_possible_bridges(island_num, row, col)

reps = [i for i in range(island_cnt+1)]

def find_rep(num):
    if reps[num] == num:
        return num
    reps[num] = find_rep(reps[num])
    return reps[num]

def union_set(num1, num2):
    reps[find_rep(num2)] = find_rep(num1)
    
def is_same_set(num1, num2):
    return find_rep(num1) == find_rep(num2)

total_dist= 0
reps = [i for i in range(island_cnt+1)]
pq = [ (v, *k) for k,v in bridges.items()]
heapify(pq)

while pq:
    dist, island1, island2 = heappop(pq)
    if not is_same_set(island1, island2):
        total_dist += dist
        union_set(island1, island2)

rep_cnt = 0
for i in range(1, island_cnt+1):
    if reps[i] == i:
        rep_cnt+=1
print(total_dist) if rep_cnt==1 else print(-1)
