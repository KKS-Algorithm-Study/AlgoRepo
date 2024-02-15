"""
1. 문제 접근 방식 (답 보고 품)
  1) 시작점부터 시작하여 차례대로 커브를 그려가자.

2. 시간 복잡도
  1) n개의 드래곤 커브를 그린다. -> O(n)
  2) 1개의 드래곤 커브를 그릴 때 최대 2**g의 반복문을 수행한다. -> O(2**g)
  3) 정사각형을 찾기 위해 100 * 100의 격자를 탐색한다. -> O(1)
  4) 따라서 최종 시간 복잡도는 O(n * 2**g)이다.

"""
import sys

input = sys.stdin.readline
n = int(input().rstrip())

graph = [[0] * 101 for _ in range(101)]

# 문제의 조건에 맞게 우, 상, 좌, 하로 맞춤
dx = [+0, -1, +0, +1]
dy = [+1, +0, -1, +0]

for _ in range(n):
  y, x, d, g = map(int, input().split())
  graph[x][y] = 1

  curves = [d]
  # 세대 만큼 순회하며 커브 인덱스를 추가
  for i in range(g):
    for k in range(len(curves) - 1, -1, -1):
      curves.append((curves[k] + 1) % 4)

  # 드래곤 커브 만들기
  for i in range(len(curves)):
    x += dx[curves[i]]
    y += dy[curves[i]]

    if 0 <= x <= 100 and 0 <= y <= 100:
      graph[x][y] = 1

answer = 0
for i in range(100):
  for j in range(100):
    if graph[i][j] == 1 and graph[i + 1][j] == 1 and graph[i][j + 1] == 1 and graph[i + 1][j + 1] == 1:
      answer += 1

print(answer)