"""
1. 문제 접근 방식
  1) 반복문을 최대한 덜 수행하도록 봄, 여름, 겨울을 1개로 묶었다.

2. 시간 복잡도
  1) k년 동안 진행 -> O(k)
  2) 1년에서 n * n 격자를 2번 수행한다. -> O(n**2)
  3) n * n 안에서 최대의 나무 갯수만큼 순회한다. -> O(max(len(trees))
  4) 따라서 1개의 칸에 있는 최대 나무 갯수를 l이라고 할 때 최종 시간 복잡도는 O(k * n**2 * l)이다.

** pypy는 통과인데 파이썬은 바로 시간초과 남
"""
def spring_summber_winter():
  for i in range(n):
    for j in range(n):
      new_tree_olds = []
      summer_nutrition = 0

      tree_map[i][j].sort()

      for k in range(len(tree_map[i][j])):
        old = tree_map[i][j][k]

        if nutrition_map[i][j] >= old:
          # 자신의 나이만큼 양분을 소비하고 나이가 1 증가
          nutrition_map[i][j] -= old
          new_tree_olds.append(old + 1)
        else:
          # 만약 양분을 먹지 못하고 죽으면 나이를 음수로 처리한다.
          summer_nutrition += old // 2

      tree_map[i][j] = new_tree_olds
      # 여름, 겨울의 영양 정보를 for문을 돌지 않고 바로 처리
      nutrition_map[i][j] += summer_nutrition + feed_info[i][j]

  return nutrition_map, tree_map


def fall():
  # 나무의 나이가 5의 배수인 나무들에 대해 나이가 1인 나무들이 팔방으로 퍼진다.
  for i in range(n):
    for j in range(n):
      copied_tree_map = tree_map[i][j][:]

      for old in copied_tree_map:
        if old % 5 == 0:
          for k in range(8):
            nx, ny = i + dx[k], j + dy[k]

            if 0 <= nx < n and 0 <= ny < n:
              tree_map[nx][ny].append(1)

  return tree_map


import sys

input = sys.stdin.readline

dx = [-1, -1, -1, +0, +0, +1, +1, +1]
dy = [-1, +0, +1, -1, +1, -1, +0, +1]

n, m, k = map(int, input().split())
feed_info = [[*map(int, input().split())] for _ in range(n)]
trees_info = [[*map(int, input().split())] for _ in range(m)]

nutrition_map = [[5] * (n) for _ in range(n)]
tree_map = [[[] for _ in range(n)] for _ in range(n)]

# 나무 정보를 입력
for x, y, old in trees_info:
  x, y = x - 1, y - 1
  tree_map[x][y].append(old)

# k년 동안 사계절 진행
for _ in range(k):
  spring_summber_winter()
  fall()

# 나무의 갯수 세기
answer = 0
for i in range(n):
  for j in range(n):
    answer += len(tree_map[i][j])

print(answer)