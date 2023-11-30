"""
1. 문제 접근 방식:
    다익스트라 알고리즘 합습 후 무지성 문제 풀이 시작.
    => 20000*20000 2차원 배열 풀이
    => 메모리 초과 발생
    => 규민좌 조언으로 4 * 20000 * 20000 해서 1.6GB라 메모리 초과 난거라고 도움 받고 그래프 형식으로 변경
    => 시간 초과
    => 규민좌 도움으로 que에 [거리, 현재 정점]형식으로 넣어 정렬 방식을 거리로 해야하는데 반대로 한 멍청한 실수를 깨닫고 고침
    => 문제 해결
2. 시간 복잡도
    O(ElogE) => 최악의 경우 모든 간선에 대해 탐색하기 때문에 E* 모든 간선에 대한 힙정렬을 실행해야 하기 때문에 logE
"""

from heapq import heappush, heappop
import sys

V, E = map(int, input().split(' '))
start = int(input()) - 1
INF = 10 * 20000 + 1

graph = {}
for i in range(V):
    graph[i] = {}

for _ in range(E):
    u, v, w = map(int, sys.stdin.readline().split(' '))
    graph[u-1][v-1] = min(graph[u-1].get(v-1, INF), w) 
    
dist = [INF] * V
dist[start] = 0
que = []
heappush(que, (dist[start], start))

while que:
    (distance, cur) = heappop(que)
    
    if distance > dist[cur]:
        continue
    
    for key in graph[cur]:
        if dist[key] > distance + graph[cur][key]:
            dist[key] = distance + graph[cur][key]
            heappush(que, (dist[key], key))

for i in range(V):
    print(dist[i] if dist[i] != INF else 'INF')