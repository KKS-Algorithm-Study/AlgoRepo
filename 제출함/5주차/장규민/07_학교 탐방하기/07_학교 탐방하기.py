from heapq import heappush, heappop
from sys import stdin

input = stdin.readline

n, m = map(int, input().split())

worst_path_parents = [i for i in range(n + 1)]
best_path_parents = [i for i in range(n + 1)]

worst_path = []
best_path = []

worst_rank = [0] * (n + 1)
best_rank = [0] * (n + 1)

for a, b, c in [list(map(int, input().split())) for _ in range(m + 1)]:
    heappush(worst_path, (c, a, b))
    heappush(best_path, (-c, a, b))


def find_parent(x, parents):
    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x], parents)

    return parents[x]


def union(parents, rank, x, y):
    if rank[y] > rank[x]:
        x, y = y, x

    parents[y] = x

    if rank[y] == rank[x]:
        rank[x] += 1


def find_path(path, parents, rank, mul):
    uphill = 0
    
    while path:
        c, a, b = heappop(path)

        a_parent, b_parent = find_parent(a, parents), find_parent(b, parents)

        if a_parent == b_parent:
            continue
        
        union(parents, rank, a_parent, b_parent)
        
        uphill += (not c) * mul

            
    return uphill ** 2


worst_cost = find_path(worst_path, worst_path_parents, worst_rank, 1)
best_cost = find_path(best_path, best_path_parents, best_rank, -1)

print(worst_cost - best_cost)
    
