import sys
from collections import deque

input = sys.stdin.readline

t = int(input())


def topological_sort():
    global que, indegree, graph, times, target, n

    answer = [0] * (n + 1)
    
    while que:
        prev = que.popleft()
        answer[prev] += times[prev]
        
        for _next in graph[prev]:
            indegree[_next] -= 1
            answer[_next] = max(answer[_next], answer[prev])

            if indegree[_next]:
                continue

            que.append(_next)

    print(answer[target])
            


for _ in range(t):
    n, m = map(int, input().split())

    times = [0] + list(map(int,input().split()))

    indegree = [0] * (n + 1)
    graph = [[] for i in range(n + 1)]

    for prev, _next in [map(int, input().split()) for _ in range(m)]:
        indegree[_next] += 1
        graph[prev].append(_next)

    que = deque()
    
    for i in range(1, n + 1):
        if indegree[i]:
            continue

        que.append(i)

    target = int(input())

    topological_sort()

