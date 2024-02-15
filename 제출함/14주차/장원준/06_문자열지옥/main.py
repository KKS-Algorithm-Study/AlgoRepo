"""
1. 문제 접근 방식
  1) 기준이 되는 첫번 째 문자열을 map에서 순환하며 dfs를 시작한다.
  2) 문자는 상, 하, 좌, 우, 대각 4방향 및 환형이 가능하므로 인덱스는 나머지 연산으로 계산한다.
  3) dfs에서는 만들어가는 문자열이 목표 문자열의 일부가 되지 않으면 얼리 리턴을 한다.

2. 시간 복잡도
  1) 신이 좋아하는 문자열의 갯수만큼 반복문을 수행한다. -> O(k)
  2) 1번의 반복문 안에서 map의 크기만큼 반복문을 수행한다. -> O(n * m)
  3) map의 요소 1개 당 dfs를 수행한다. -> 그 때 신이 좋아하는 문자열의 길이를 l이라고 하면 최대 O(8**l) 만큼 수행
  4) 따라서 최종 시간 복잡도는 O(k * n * m * max(8**l)이다.

** 파이썬의 경우 나머지 연산자 계산 시 항상 분모와 같은 부호의 결과값이 반환된다.
  -10 % 3인 경우 >> -10 = -3 * 4 + 2 이므로 몫은 -4, 나머지는 2이다.
"""
import sys

input = sys.stdin.readline

def dfs(x, y, depth, string, goal):
  if goal[0: depth] != string:
    return

  if len(string) == len(goal):
    if string == goal:
      str_dict[goal] = str_dict.get(goal, 0) + 1
    return

  for i in range(8):
    nx, ny = x + dx[i], y + dy[i]
    next_string = string + map[nx % n][ny % m]
    dfs(nx, ny, depth + 1, next_string, goal)


dx = [-1, +1, +0, +0, -1, -1, +1, +1]
dy = [+0, +0, -1, +1, -1, +1, -1, +1]

n, m, k = map(int, input().split())
map = [list(input().rstrip()) for _ in range(n)]
goals = [input().rstrip() for _ in range(k)]
str_dict = {}

for goal in goals:
  if not goal in str_dict:
    for i in range(n):
      for j in range(m):
        dfs(i, j, 1, map[i][j], goal)

  print(str_dict.get(goal, 0))

