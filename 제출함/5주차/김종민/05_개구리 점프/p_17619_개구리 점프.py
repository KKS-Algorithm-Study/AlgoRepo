from sys import stdin
from heapq import heappush, heappop, heapify

input = stdin.readline

N, Q = map(int, input().split())

parents = [i for i in range(N + 1)]
logs = [list(map(int, input().split())) + [i] for i in range(1, N + 1)]
heapify(logs)


def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])

    return parents[x]


def union(x, y):
    global parents

    px, py = find_parent(x), find_parent(y)

    if py == px:
        return

    parents[py] = px


prev_x1, prev_x2, prev_y, prev_idx = heappop(logs)
while logs:
    next_x1, next_x2, next_y, next_idx = heappop(logs)
    
    if (prev_x1 <= next_x1 <= prev_x2) or (prev_x1 <= next_x2 <= prev_x2)\
    or (next_x1 <= prev_x2 <= next_x2):
        union(prev_idx, next_idx)
        prev_x2 = max(prev_x2, next_x2)
    else:
        prev_x1, prev_x2, prev_y, prev_idx = next_x1, next_x2, next_y, next_idx

answer = []
for sour, des in [list(map(int, input().split())) for _ in range(Q)]:
    sour_parent, des_parent = find_parent(sour), find_parent(des)
    if sour_parent == des_parent:
        answer.append('1')
    else:
        answer.append('0')

print('\n'.join(answer))
