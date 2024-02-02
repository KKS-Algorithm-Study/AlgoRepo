"""
1. 문제 접근 방식
  1) 백트래킹으로 풀이
  2) n*m은 최대 100개인데 최대 4번의 재귀까지 수행되므로 연산이 1억번이 된다.
    - 그러나 재귀만 1억번이지 내부의 for문도 존재하므로 시간 초과가 발생한다.
  3) 따라서 다음 재귀를 수행할 때는 이전에 덧셈에 추가한 행부터 시작하도록 한다.
  4) visited를 False로 복구할 때는 그 인덱스가 False로 바꾼 칸만 원상복구 할 수 있게 한다.

2. 시간 복잡도
  1) N x M 칸의 판을 k번 재귀로 수행하므로 O((N*M) ** k) 이다.

"""
dx = [+1, -1, +0, +0]
dy = [+0, +0, +1, -1]

def dfs(depth, num, prev_x):
  global result

  if depth == k:
    result = max(result, num)
    return

  for x in range(prev_x, n):
    for y in range(m):
      if not visited[x][y]:
        to_visit = [(x, y)]
        visited[x][y] = True
        for w in range(4):
          nx, ny = x + dx[w], y + dy[w]
          if 0 <= nx < n and 0 <= ny < m and not visited[nx][ny]:
            visited[nx][ny] = True
            to_visit.append((nx, ny))

        dfs(depth + 1, board[x][y] + num, x)

        # i, j를 순회하면서 visited = True 했던 요소만 다시 원래대로 복구
        for nx, ny in to_visit:
          visited[nx][ny] = False


n, m, k = map(int, input().split())
board = [[*map(int, input().split())] for _ in range(n)]
visited = [[False] * m for _ in range(n)]
result = -int(1e9)

dfs(0, 0, 0)
print(result)
