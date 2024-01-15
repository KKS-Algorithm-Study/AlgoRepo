n, m = map(int, input().split())

board = [list(map(int, input().split())) for _ in range(n)]
house, chicken = [], []

for i in range(n):
    for j in range(n):
        if board[i][j] == 1:
            house.append((i, j))
            continue

        if board[i][j] == 2:
            chicken.append((i, j))

count = len(chicken)


def find_shorted_dist(opened):
    global answer, house, n
    
    total = 0

    for h in house:
        min_value = n * n
            
        for o in opened:
            min_value = min(min_value, abs(h[0] - o[0]) + abs(h[1] - o[1]))

        total += min_value

        if total > answer:
            return
                
    answer = min(total, answer)


def backtracking(start, depth, opened):
    global baord, m, visit, answer, count

    if depth == m:
        find_shorted_dist(opened)
        return

    for i in range(start, count):
        if visit[i]:
            continue

        visit[i] = True
        opened.append(chicken[i])
        
        backtracking(i + 1, depth + 1, opened)
        
        opened.pop()
        visit[i] = False


visit = [False] * count
answer = int(1e4)
backtracking(0, 0, [])
print(answer)
