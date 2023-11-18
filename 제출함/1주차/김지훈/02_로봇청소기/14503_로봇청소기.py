import sys

# 방향키 (북,동,남,서)
directionRow = [-1,0,1,0]
directionCol = [0,1,0,-1]

input = sys.stdin.readline
n,m = map(int, input().split())               # 첫째줄 입력받기
row,col,direction = map(int, input().split()) # 둘째줄 입력받기

room = []
for _ in range(n):
    room.append(list(map(int, input().split())))

cnt = 0
while True:
    notMoved = True
    # print(row,col)

    if room[row][col] == 0: 
        room[row][col] = 2
        cnt += 1

    for i in range(1,5):
        nextDirection = (direction - i) % 4         # d가 1이면 0321 순 출력 #(d-i+4)%4
        nextRow = row + directionRow[nextDirection]
        nextCol = col + directionCol[nextDirection]
        
        if room[nextRow][nextCol] == 0:
            row = nextRow
            col = nextCol
            direction = nextDirection
            notMoved = False
            break

    if notMoved == True:
        backRow = row - directionRow[direction]
        backCol = col - directionCol[direction]
        row = backRow
        col = backCol

        if room[row][col] == 1:
            break

# print(*room, sep = "\n")
print(cnt)