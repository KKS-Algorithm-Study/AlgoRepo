"""
1. 문제 접근 방식
이거 전 문제에서 오답내서 쉽게 풀 수 있었음
1에서 N까지 가는 경로 구해서
각 경로 막았을 때 시간 구하고 최대로 늘릴 수 있는 시간 구하면 됨

2. 시간복잡도
O(V*E*logE)
1000 * 10000 * log10000 = 40,000,000
"""
from heapq import heappop, heappush

N,M = map(int, input().split(' '))
graph = {}

INF = 100000000

for i in range(N+1):
    graph[i] = {}

for _ in range(M):
    a,b,t = map(int, input().split(' '))
    graph[a][b] = t
    graph[b][a] = t

dist = [INF] * (N+1)
dist[1] = 0
courses = [[] for _ in range(N+1)]

que = []
heappush(que, [dist[1], [1]])

while que:
    distance, course = heappop(que)
    vertex = course[-1]
    if distance > dist[vertex]:
        continue

    for linkedVertext in graph[vertex]:
        if dist[linkedVertext] > distance + graph[vertex][linkedVertext]:
            new_course = course[:]
            new_course.append(linkedVertext)
            courses[linkedVertext] = new_course
            dist[linkedVertext] = distance + graph[vertex][linkedVertext]
            heappush(que, [dist[linkedVertext], new_course])

origin = dist[N]
answer = 0

def dijkstra():
    dist = [INF] * (N+1)
    dist[1] = 0
    
    que = []
    heappush(que, [dist[1], 1])

    while que:
        distance, vertex = heappop(que)

        if distance > dist[vertex]:
            continue

        for linkedVertext in graph[vertex]:
            if dist[linkedVertext] > distance + graph[vertex][linkedVertext]:
                dist[linkedVertext] = distance + graph[vertex][linkedVertext]
                heappush(que, [dist[linkedVertext], linkedVertext])

    return dist[N]

course = courses[N]

for idx in range(len(course)-1):
    front = course[idx]
    back = course[idx+1]

    val = graph[front][back]

    del(graph[front][back])
    del(graph[back][front])
    res = dijkstra()
    graph[front][back] = val
    graph[back][front] = val

    answer = max(answer, res - origin)

print(answer if answer < INF - origin else -1)