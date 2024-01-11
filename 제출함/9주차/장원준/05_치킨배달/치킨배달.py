"""
1. 문제 접근 방식
  1) 치킨집과 집을 따로 저장한다.
  2) 치킨집을 m개만큼 조합하면서, 백트래킹 수행

2. 시간 복잡도
  1) 백트래킹이므로 최대 13!이다.

"""
from copy import deepcopy

def dfs(chicken_places, index, visited, depth, selected):
  global chicken_dist

  if depth == m:
    chicken_dist = min(chicken_dist, calculate_chicken_dist(selected))

  for i in range(index, len(chicken_places)):
    visited[i] = True
    selected.append(chicken_places[i])

    dfs(chicken_places, i + 1, visited, depth + 1, selected)

    visited[i] = False
    selected.remove(chicken_places[i])


# 1개의 치킨집 리스트에서 각 집의 최소 치킨 거리를 구한다.
def calculate_chicken_dist(chicken_places):
  homes = deepcopy(default_homes)

  for chicken_x, chicken_y in chicken_places:
    for key in homes.keys():
      home_x, home_y = key
      chicken_dist = abs(home_x - chicken_x) + abs(home_y - chicken_y)
      homes[key] = min(homes[key], chicken_dist)

  return sum(homes.values())


INF = int(1e9)
chicken_dist = INF
chicken_places = []
default_homes = {}

n, m = map(int, input().split())
town = [list(map(int, input().split())) for _ in range(n)]

# 집, 치킨집을 따로 저장한다.
for i in range(n):
  for j in range(n):
    if town[i][j] == 2:
      chicken_places.append((i, j))
    elif town[i][j] == 1:
      default_homes[(i, j)] = INF


visited = [False] * len(chicken_places)
dfs(chicken_places, -1, visited, 0, [])

print(chicken_dist)