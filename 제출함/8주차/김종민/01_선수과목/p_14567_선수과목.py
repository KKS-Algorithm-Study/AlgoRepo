from collections import deque

N,M = map(int, input().split(' '))

graph = {}
indegrees = [0] * (N+1)
res = [1] * (N+1)

for i in range(1,N+1):
    graph[i] = []
    
for _ in range(M):
    C, P = map(int, input().split(' '))
    graph[C].append(P)
    indegrees[P] += 1
    
que = deque()

for i in range(1,N+1):
    if indegrees[i] == 0:
        que.append(i)
        
while que:
    cur = que.popleft()
    
    for node in graph[cur]:
        indegrees[node] -= 1
        res[node] = max(res[node], res[cur]+1)
        if indegrees[node] == 0:
            que.append(node)

    
print(*res[1:])