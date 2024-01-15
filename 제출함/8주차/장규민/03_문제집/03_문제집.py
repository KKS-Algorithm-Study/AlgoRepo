import sys
from heapq import heappush, heappop

input = sys.stdin.readline

n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]
indegree = [0] * (n + 1)


for prev, _next in [map(int, input().split()) for _ in range(m)]:
    indegree[_next] += 1
    graph[prev].append(_next)

heap = []
for i in range(1, n + 1):
    if indegree[i]:
        continue

    heappush(heap, i)


def topological_sort():
    global heap, graph, indegree

    answer = []

    while heap:
        cur = heappop(heap)
        answer.append(str(cur))

        while graph[cur]:
            _next = heappop(graph[cur])

            indegree[_next] -= 1

            if indegree[_next]:
                continue

            heappush(heap, _next)
            

    print(' '.join(answer))


topological_sort()
