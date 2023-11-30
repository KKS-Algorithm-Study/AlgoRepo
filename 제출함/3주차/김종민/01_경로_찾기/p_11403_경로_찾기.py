"""
1. 문제 접근 방식:
연결 되어 있는지만 체크하면 돼서 dictionary에 리스트로 연결 되어 있는 정점 다 넣고
연결 되어 있는 정점들 그래프로 나타냇음
2. 시간 복잡도 : O(N^2*E)
"""

from collections import deque

N = int(input())
mp = [list(map(int, input().split(' '))) for _ in range(N)]
# 그래프 만들기
gp = {}
for i in range(N):
    gp[i] = []
    
for i in range(N):
    for j in range(N):
        if mp[i][j]:
            gp[i].append(j)

# 그래프 탐색
            
return_mp = [[0 for _ in range(N)] for _ in range(N)]

def search(src, trg):
    que = deque()
    que.append(src)
    visited = [False for _ in range(N)]
    visited[src] = True
        
    while que:
        cur = que.popleft()
        
        for link in gp[cur]:
            if link == trg:
                return True
            if not visited[link]:
                visited[link] = True
                que.append(link)
    
    return False
for i in range(N):
    for j in range(N):
        if search(i,j):
            return_mp[i][j] = 1
            
for row in return_mp:
    print(*row, sep=' ')