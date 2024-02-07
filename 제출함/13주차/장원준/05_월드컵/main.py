"""
1. 문제 접근 방식 (답 보고 품)
  1) 모든 경기의 경우의 수를 구한 다음, 특정 경기에 대해 승, 무, 패를 가리는 경우를 센다.

2. 시간 복잡도
  1) 최대 15번 재귀를 수행하므로 O(3*15)이다.

"""
from itertools import combinations

def dfs(depth):
  global cnt

  # 15번째 경기에 도달했을 때
  if depth == 15:
    cnt = 1
    for sub in res:
      # 전체 승무패의 합계가 0이 아니면
      if sub.count(0) != 3:
        cnt = 0
        break
    return

  # 전체 경기 15번의 조합
  g1, g2 = games[depth]
  # 각 경기의 승무패
  for x, y in ((0,2), (1,1), (2,0)):
    # 각 경기가 어떤 결과인지는 모르지만, 승무패 중 1개는 속한다.
    # 그리고 인덱스 0 -> 승, 1 -> 무, 2 -> 패를 가리킨다.
    if res[g1][x] > 0 and res[g2][y] > 0:
      res[g1][x] -= 1
      res[g2][y] -= 1
      dfs(depth+1)
      res[g1][x] += 1
      res[g2][y] += 1


answers = []
games = list(combinations(range(6), 2))

for _ in range(4):
  tmp = list(map(int, input().split()))
  res = [tmp[i:i+3] for i in range(0, 16, 3)]
  cnt = 0
  dfs(0)
  answers.append(cnt)

print(*answers)