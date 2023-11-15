import sys
input = sys.stdin.readline

go = []
for _ in range(19):
    go.append(list(map(int, input().split())))

dx = [0, 1 , 1 , 1] # ↓ → ↗ ↘
dy = [1, 0 , -1 , 1]

def getwiner(go):
    for row in range(19):
        for col in range(19):
            if go[row][col] != 0 :
                point = go[row][col]
                for d in range(4):
                    by = row - dy[d]
                    bx = col - dx[d]
                    if 0 <= bx < 19 and 0 <= by < 19 and go[by][bx] == point:
                        continue
                    count = 1
                    ny = row + dy[d]
                    nx = col + dx[d]
                    
                    while 0 <= nx < 19 and 0 <= ny < 19 and go[ny][nx] == point :
                        count += 1
                        nx += dx[d]
                        ny += dy[d]

                    if count == 5:
                        return f"{point}\n{row+1} {col+1}"
                    
    return 0

print(getwiner(go))