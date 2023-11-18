import sys
input = sys.stdin.readline

# 방향키(2,3,5,6시 방향)
dx = [-1,0,1,1]
dy = [ 1,1,1,0]

graph = []
for _ in range(19):
    graph.append(list(map(int, input().split())))

def getwinner(graph):
    for row in range(19):
        for col in range(19):
            if graph[row][col] != 0:
                value = graph[row][col]
    
                for i in range(4):
                    bx = row - dx[i]
                    by = col - dy[i]
                    if 0 <= bx < 19 and 0 <= by < 19 and graph[bx][by] == value:
                        continue
                    
                    cnt = 1
                    nx = row + dx[i]
                    ny = col + dy[i]
    
                    while 0 <= nx < 19 and 0 <= ny < 19 and graph[nx][ny] == value:
                        cnt += 1
    
                        nx += dx[i]
                        ny += dy[i]
    
                    if cnt == 5:
                            return f"{value}\n{row + 1} {col + 1}"
    return 0

print(getwinner(graph))