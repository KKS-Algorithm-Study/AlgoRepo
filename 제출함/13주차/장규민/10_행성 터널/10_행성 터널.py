import sys
from heapq import heappush, heappop

input = sys.stdin.readline


def find_parent(x):
    global parents

    if x == parents[x]:
        return x

    parents[x] = find_parent(parents[x])
    
    return parents[x]


def union(x, y):
    global parents

    px, py = find_parent(x), find_parent(y)

    if px == py:
        return

    parents[py] = px


def mst():
    global heap

    ret = 0
    while heap:
        dist, sour, des = heappop(heap)

        if find_parent(sour) == find_parent(des):
            continue

        ret += dist
        union(sour, des)

    return ret


n = int(input())

planets = [list(map(int, input().split())) + [i] for i in range(n)]
parents = [i for i in range(n)]


planets.sort(key=lambda x: x[0])

heap = []

for i in range(n-1):
    heappush(heap, (abs(planets[i][0] - planets[i + 1][0]), planets[i][3], planets[i + 1][3]))

planets.sort(key=lambda x: x[1])

for i in range(n-1):
    heappush(heap, (abs(planets[i][1] - planets[i + 1][1]), planets[i][3], planets[i + 1][3]))

planets.sort(key=lambda x: x[2])

for i in range(n-1):
    heappush(heap, (abs(planets[i][2] - planets[i + 1][2]), planets[i][3], planets[i + 1][3]))
    

print(mst())

