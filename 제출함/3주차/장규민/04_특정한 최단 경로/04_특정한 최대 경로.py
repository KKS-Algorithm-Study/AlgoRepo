from sys import stdin
import heapq

input = stdin.readline

n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]

for _ in range(m):
    u, v, cost = map(int, input().split())
    
    graph[u].append((v, cost))
    graph[v].append((u, cost))

v1, v2 = map(int, input().split())


def dijkstra(_graph, start, end):
    heap = []

    INF = int(1e9)
    costs = [INF] * (len(_graph))
    costs[start] = 0

    heapq.heappush(heap, (costs[start], start))

    while heap:
        cur_cost, sour = heapq.heappop(heap)

        if costs[sour] < cur_cost:
            continue

        for des, cost in _graph[sour]:
            next_cost = cur_cost + cost
            
            if next_cost >= costs[des]:
                continue
            
            costs[des] = next_cost
            
            if des == end:
                continue
            
            heapq.heappush(heap, (next_cost, des))
            
    return costs[end]


total1 = 0

total1 += dijkstra(graph, 1, v1)
total1 += dijkstra(graph, v1, v2)
total1 += dijkstra(graph, v2, n)

total2 = 0

total2 += dijkstra(graph, 1, v2)
total2 += dijkstra(graph, v2, v1)
total2 += dijkstra(graph, v1, n) 

answer = min(total1, total2)
print(answer if answer < int(1e9) else -1)

    
