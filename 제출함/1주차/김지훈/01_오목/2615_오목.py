{\rtf1\ansi\ansicpg949\cocoartf2758
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 import sys\
input = sys.stdin.readline\
\
# \uc0\u48169 \u54693 \u53412 (2,3,5,6\u49884  \u48169 \u54693 )\
dx = [-1,0,1,1]\
dy = [ 1,1,1,0]\
\
graph = []\
for _ in range(19):\
    graph.append(list(map(int, input().split())))\
\
def getwinner(graph):\
    for row in range(19):\
        for col in range(19):\
            if graph[row][col] != 0:\
                value = graph[row][col]\
    \
                for i in range(4):\
                    bx = row - dx[i]\
                    by = col - dy[i]\
                    if 0 <= bx < 19 and 0 <= by < 19 and graph[bx][by] == value:\
                        continue\
                    \
                    cnt = 1\
                    nx = row + dx[i]\
                    ny = col + dy[i]\
    \
                    while 0 <= nx < 19 and 0 <= ny < 19 and graph[nx][ny] == value:\
                        cnt += 1\
    \
                        nx += dx[i]\
                        ny += dy[i]\
    \
                    if cnt == 5:\
                            return f"\{value\}\\n\{row + 1\} \{col + 1\}"\
    return 0\
\
print(getwinner(graph))}