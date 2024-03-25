import sys
from collections import deque

input = sys.stdin.readline

n, m = map(int, input().split())

graph = [[] for _ in range(n + 1)]

for sour, des in [map(int, input().split()) for _ in range(m)]:
    graph[sour].append(des)
    graph[des].append(sour)

for i in range(1, n + 1):
    graph[i].sort()

s, e = map(int, input().split())

route = [0] * (n + 1)

def bfs(start, end, cnt, route):
    global graph, n
    
    visit = [False] * (n + 1)
    visit[start] = True

    idx = route[start]
    while idx > 0:
        visit[idx] = True
        idx = route[idx]
    visit[end] = False

    que = deque()
    que.append((start, cnt))

    while que:
        sour, cur_cnt = que.popleft()

        for des in graph[sour]:
            if visit[des]:
                continue

            que.append((des, cur_cnt + 1))
            visit[des] = True
            route[des] = sour
            
            if des == end:
                return cur_cnt + 1


cnt = bfs(s, e, 0, route)
print(bfs(e, s, cnt, route))
