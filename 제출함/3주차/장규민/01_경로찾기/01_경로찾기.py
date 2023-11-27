n = int(input())

board = [list(input().split()) for _ in range(n)]

max_value = int(1e6)

path = [[1 if board[i][j] == '1' else max_value for j in range(n)] for i in range(n)]


for k in range(n):
    for i in range(n):
        for j in range(n):
            path[i][j] = min(path[i][j], (path[i][k] + path[k][j]))

for i in range(n):
    for j in range(n):
        print(1 if path[i][j] != max_value else 0, end=' ')
    print()

