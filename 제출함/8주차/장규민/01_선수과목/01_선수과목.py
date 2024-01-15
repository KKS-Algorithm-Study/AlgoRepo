import sys
from collections import deque

input = sys.stdin.readline

n, m = map(int, input().split())

indegree = [0] * (n + 1)
graph = [[] for _ in range(n + 1)]

for prev, _next in [map(int, input().split()) for _ in range(m)]:
    indegree[_next] += 1
    graph[prev].append(_next)

que = deque()
for i in range(1, n + 1):
    if indegree[i]:
        continue

    que.append((i, '1'))


def topological_sort():
    global graph, indegree, que, n

    answer = ['0'] * (n + 1)
    
    while que:
        number, semester = que.popleft()
        answer[number] = semester

        for _next in graph[number]:
            indegree[_next] -= 1

            if indegree[_next]:
                continue

            que.append((_next, str(int(semester) + 1)))

    print(' '.join(answer[1:]))


topological_sort()
