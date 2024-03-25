def backtracking(depth):
    global player, visit, flag, table

    if depth == len(table):
        flag = True
        return

    a, b = table[depth]
    
    player[a][0] -= 1
    player[b][2] -= 1
    if player[a][0] >= 0 and player[b][2] >= 0:
        backtracking(depth + 1)
    player[a][0] += 1
    player[b][2] += 1

    player[a][2] -= 1
    player[b][0] -= 1
    if player[a][2] >= 0 and player[b][0] >= 0:
        backtracking(depth + 1)
    player[a][2] += 1
    player[b][0] += 1

    player[a][1] -= 1
    player[b][1] -= 1
    if player[a][1] >= 0 and player[b][1] >= 0:
        backtracking(depth + 1)
    player[a][1] += 1
    player[b][1] += 1
    

result = [list(map(int, input().split())) for _ in range(4)]

answer = []

table = []

for i in range(6):
    for j in range(i + 1, 6):
        table.append((i, j))

for i in range(4):
    player = []
    visit = [False] * 6
    flag = False
    flag2 = True
    
    for j in range(6):
        temp = result[i][j * 3 : j * 3 + 3]
        
        if sum(temp) != 5:
            flag2 = False
            break
        
        player.append(temp)
        
    if flag2:
        backtracking(0)
    
    answer.append('1' if flag else '0')
    
print(' '.join(answer))
