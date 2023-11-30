"""
1. 문제 접근 방식
    1. 1번 정점에서 N번 정점으로 이동할 때 반드시 통과해야 하는 두 점이 있다.
    2. 입력 : N = 정점, E = 간선 v1, v2 = 거쳐야 할 정점 번호
    3. 겹치는 간선은 없다.
    4. 모든 정점에서 모든 정점으로 가는 최단 거리를 구한다.
    5. 1번에서 N번까지 가는 최단거리 코스를 저장한다.
    6. 경로 상에 v1, v2가 있는지 확인
    7. 없다면 경로 상에 있는 모든 지점의 최단 경로를 확인하며 v1과 v2와 가장 가깝게 왔다갔다 할 수 있는 거리를 더함
    => 시간 초과
    v1과 v2를 반드시 들려야 하기 때문에
    1 -> v1 -> v2 -> N or 1 -> v2 -> v1 -> N 거리만 구하면 됨

2. 시간 복잡도
O(ElogE)
2 * ElogE => 2,120,411
모든 정점 최단 거리 시간 복잡도
V * ElogE => 848,164,799(시간 초과)
"""
import sys
from heapq import heappop, heappush

N, E = map(int, input().split(' '))
INF = 10000000

graph = {}

for i in range(1, N+1):
    graph[i] = {}

for _ in range(E):
    a, b, c = map(int, sys.stdin.readline().split(' '))
    graph[a][b] = c
    graph[b][a] = c

v1, v2 = map(int, input().split(' '))

def dijkstra(V):
    dist = [INF for _ in range(N+1)]
    dist[V] = 0
    que = []
    heappush(que, [0, V])

    while que:
        distance, cur = heappop(que)

        if dist[cur] < distance:
            continue

        for dst in graph[cur]:
            if dist[dst] > distance + graph[cur][dst]:
                dist[dst] = distance + graph[cur][dst]
                heappush(que, [dist[dst], dst])

    return dist

layover1 = dijkstra(v1)
layover2 = dijkstra(v2)

minVal = min(layover1[1]+layover1[v2]+layover2[N], layover2[1]+layover2[v1]+layover1[N])
print(minVal if minVal < INF else -1)