"""
1. 문제 접근 방식
  1) 1열을 각각 순회하며 시작 지점으로 잡는다.
  2) 오른쪽으로 진행하되 최대한 위로 붙어서 파이프를 연결한다.
  3) 그리디하게 dfs로 풀고, 문제 조건 상 연결 시도 했지만 끝까지 연결을 못했을 때 지나온 지점을 다시 사용하는 경우는 없는듯.

2. 시간 복잡도
  1) 첫번 째 열의 행의 갯수 만큼 반복한다 -> O(r)
  2) 1번의 반복에서 최대 3**c 만큼 반복한다.
    ** 그러나 가지치기로 인해 시간 내에 풀이 가능
  3) 따라서 최종 시간 복잡도는 O(r * 3**c)이다.

"""
def connect(now_x, now_y):
  global answer

  # 빵집까지 연결했을 때
  if now_y == c - 1:
    return True

  for i in range(3):
    nx, ny = now_x + dx[i], now_y + dy[i]

    if 0 <= nx < r and 0 <= ny < c and map[nx][ny] == '.' and not visited[nx][ny]:
      # 그리디하게 연결하므로 다시 False로 돌려놓지 않아도 된다.
      # 가다가 막혔을 때 이미 visited = True된 지점을 다시 사용하는 경우는 없는 듯
      visited[nx][ny] = True

      # 계속 연결이 가능하면
      if connect(nx, ny):
        return True

  return False


import sys

input = sys.stdin.readline

# 오른쪽 위 대각선, 오른쪽, 오른쪽 아래 대각선 순으로 우선순위 부여
dx = [-1, +0, +1]
dy = [+1, +1, +1]

# 입력 받기
r, c = map(int, input().split())
map = [list(input().rstrip()) for _ in range(r)]
visited = [[False] * c for _ in range(r)]
answer = 0

# 첫 열의 각 점을 시작으로 연결 시작
for i in range(r):
  if connect(i, 0):
    answer += 1

print(answer)