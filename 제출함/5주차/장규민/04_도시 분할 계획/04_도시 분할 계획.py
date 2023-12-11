from sys import stdin
from heapq import heappush, heappop

input = stdin.readline

n, m = map(int, input().split())

graph = []
parents = [i for i in range(n + 1)]

for sour, des, cost in [list(map(int, input().split())) for _ in range(m)]:
    heappush(graph, (cost, sour, des))

    
def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])

    return parents[x]


def union(x, y):
    global parents

    if x == y:
        return

    parents[y] = x

answer = 0
temp = 0
while graph:
    cost, sour, des = heappop(graph)
    sour_parent, des_parent = find_parent(sour), find_parent(des)
    if sour_parent == des_parent:
        continue
    answer += cost
    temp = cost
    union(sour_parent, des_parent)

print(answer - temp)
