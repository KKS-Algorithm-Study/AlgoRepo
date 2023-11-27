from sys import stdin
import heapq

input = stdin.readline

n, m = map(int, input().split())

road = [[] for _ in range(n + 1)]

for _ in range(m):
    u, v, cost = map(int, input().split())

    road[u].append((v, cost))
    road[v].append((u, cost))


def dijkstra(_road, _n, police_y, police_x):
    heap = []

    heapq.heappush(heap, (0, 1))

    INF = int(1e9)
    costs = [INF] * len(_road)
    costs[1] = 0

    path = [[] for _ in range(_n + 1)]

    while heap:
        cur_cost, sour = heapq.heappop(heap)

        if cur_cost > costs[sour]:
            continue

        for des, cost in _road[sour]:
            if police_y != -1 and police_x != -1 and ((police_y == sour and police_x == des)\
                                                      or (police_y == des and police_x == sour)):
                continue
            
            next_cost = cost + cur_cost
            
            if next_cost > costs[des]:
                continue

            if next_cost == costs[des]:
                path[des].append(sour)
                continue
            
            path[des] = []
            path[des].append(sour)
            costs[des] = next_cost
            heapq.heappush(heap, (next_cost, des))
    
    return (path, costs[-1])
        

min_value = dijkstra(road, n, -1, -1)
answer = 0

temp_path = min_value[0]
sour = n
path = []

while sour != 1:
    des = temp_path[sour][0]
    path.append((sour, des))
    sour = des
    
for y, x in path:
    result = dijkstra(road, n, y, x)[1]
    
    if result == int(1e9):
        answer = int(1e9)
        break
    
    answer = max(answer, result - min_value[1])
    
print(answer if answer != int(1e9) else -1)
    
