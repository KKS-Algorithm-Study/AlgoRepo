from sys import stdin
import heapq

input = stdin.readline

n, m = map(int, input().split())

v = int(input())

INF = int(1e9)
graph = [{} for _ in range(n + 1)]

for _ in range(m):
    sour, des, cost = map(int, input().split())
    if graph[sour].get(des) is None:
        graph[sour][des] = INF
    graph[sour][des] = min(cost, graph[sour][des])
    

def dijkstra(start, _n, _INF):
    heap = []
    
    costs = [INF] * (_n + 1)
    costs[start] = 0
    
    heapq.heappush(heap, (0, start))

    while heap:
        cur_cost, sour = heapq.heappop(heap)

        if costs[sour] < cur_cost:
            continue
        
        for des in graph[sour].keys():
            next_cost = graph[sour][des] + cur_cost
            
            if next_cost >= costs[des]:
                continue

            costs[des] = next_cost
            heapq.heappush(heap, (next_cost, des))
            
    print_value(costs, INF)


def print_value(array, _INF):
    for data in array[1:]:
        print('INF' if _INF == data else data)


dijkstra(v, n, INF)
