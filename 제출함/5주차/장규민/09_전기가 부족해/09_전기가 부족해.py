import sys
from heapq import heappush, heappop

input = sys.stdin.readline

n, m, k = map(int, input().split())

generator = dict.fromkeys(list(map(int, input().split())), True)
MST_parents = [i for i in range(n + 1)]
path_parents = [i for i in range(n + 1)]
graph = []
path = []

for u, v, w in [list(map(int, input().split())) for _ in range(m)]:
    heappush(graph, (w, u, v))
    

def find_parent(x, parents):
    global generator

    if parents[x] == x:
        return x

    parents[x] = find_parent(parents[x], parents)

    return parents[x]

    
def union(px, py, parents):
    global generator

    parents[py] = px


def find_parent2(x, parents):
    global generator

    if parents[x] == x or generator.get(x) is not None:
        return x

    parents[x] = find_parent(parents[x], parents)

    return parents[x]

    
def union2(px, py, parents):
    global generator

    if generator.get(px) is not None and generator.get(py) is not None:
        return

    if px == py:
        return

    if generator.get(py) is not None:
        py, px = px, py
    
    parents[py] = px

    
answer = 0
while graph:
    cost, sour, des = heappop(graph)
    
    sour_parent, des_parent = find_parent(sour, MST_parents), find_parent(des, MST_parents)
    sour_path_parent, des_path_parent = find_parent2(sour, path_parents), find_parent2(des, path_parents)

    if sour_parent == des_parent:
        continue
    
    heappush(path, (-cost, sour, des))
    union2(sour_path_parent, des_path_parent, path_parents)
    MST_parents[sour_parent] = des_parent
    answer += cost


while path:
    cost, sour, des = heappop(path)
    
    py, px = find_parent2(sour, path_parents), find_parent2(des, path_parents)
    
    if py == px:
        continue
    answer += cost
        
    
print(answer)
