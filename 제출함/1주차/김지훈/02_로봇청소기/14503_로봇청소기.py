{\rtf1\ansi\ansicpg949\cocoartf2758
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 import sys\
\
# \uc0\u48169 \u54693 \u53412  (\u48513 ,\u46041 ,\u45224 ,\u49436 )\
directionRow = [-1,0,1,0]\
directionCol = [0,1,0,-1]\
\
input = sys.stdin.readline\
n,m = map(int, input().split())               # \uc0\u52395 \u51704 \u51460  \u51077 \u47141 \u48155 \u44592 \
row,col,direction = map(int, input().split()) # \uc0\u46168 \u51704 \u51460  \u51077 \u47141 \u48155 \u44592 \
\
room = []\
for _ in range(n):\
    room.append(list(map(int, input().split())))\
\
cnt = 0\
while True:\
    notMoved = True\
    # print(row,col)\
\
    if room[row][col] == 0: \
        room[row][col] = 2\
        cnt += 1\
\
    for i in range(1,5):\
        nextDirection = (direction - i) % 4         # d\uc0\u44032  1\u51060 \u47732  0321 \u49692  \u52636 \u47141  #(d-i+4)%4\
        nextRow = row + directionRow[nextDirection]\
        nextCol = col + directionCol[nextDirection]\
        \
        if room[nextRow][nextCol] == 0:\
            row = nextRow\
            col = nextCol\
            direction = nextDirection\
            notMoved = False\
            break\
\
    if notMoved == True:\
        backRow = row - directionRow[direction]\
        backCol = col - directionCol[direction]\
        row = backRow\
        col = backCol\
\
        if room[row][col] == 1:\
            break\
\
# print(*room, sep = "\\n")\
print(cnt)}