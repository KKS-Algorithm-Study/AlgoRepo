import sys
from heapq import heappush, heappop

input = sys.stdin.readline

n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]

for sour, des, cost in [list(map(int, input().split())) for _ in range(m)]:
    graph[sour].append((des, cost))
    graph[des].append((sour, cost))

s, t = map(int, input().split())


def dijkstra():
    global graph, s, t, n 

    INF = int(1e9)
    costs = [INF] * (n + 1)

    heap = []

    heappush(heap, (0, s))

    while heap:
        cur_cost, sour = heappop(heap)

        if cur_cost > costs[sour]:
            continue

        for des, cost in graph[sour]:
            next_cost = cost + cur_cost

            if next_cost >= costs[des]:
                continue

            costs[des] = next_cost
            heappush(heap, (next_cost, des))

    return costs[t]


print(dijkstra())
