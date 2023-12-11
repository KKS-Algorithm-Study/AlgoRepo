from heapq import heappush, heappop

n, m = map(int, input().split())

gender = [0] + list(input().split())

graph = []
path = [[] for _ in range(n + 1)]
parents = [i for i in range(n + 1)]

for sour, des, cost in [list(map(int, input().split())) for _ in range(m)]:
    heappush(graph, (cost, sour, des))


def find_parent(x):
    global parents

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x])

    return parents[x]


def union(px, py):
    global parents

    if px == py:
        return

    parents[py] = px


answer = 0
while graph:
    cost, sour, des = heappop(graph)

    if gender[sour] == gender[des]:
        continue

    sour_parent, des_parent = find_parent(sour), find_parent(des)

    if sour_parent == des_parent:
        continue
    path[sour].append(des)
    path[des].append(sour)
    
    answer += cost
    union(sour_parent, des_parent)

parent = find_parent(1)
flag = True
for i in range(2, n + 1):
    if parent != find_parent(i):
        flag = False
        break

print(answer if flag else -1)
