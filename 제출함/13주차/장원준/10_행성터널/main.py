"""
1. 문제 접근 방식 (답 보고 품)
  1) MST를 위한 간선을 만들어야 한다.
  2) 그러나 입력이 크므로 모든 간선을 만들면 메모리 초과가 발생한다.
  3) MST에서는 N-1개의 간선만 필요하므로, x, y, z에 대한 N-1개의 간선만 추출한다.

2. 시간 복잡도
  1) 입력을 위해 n번 반복한다. -> O(n)
  2) 행성의 위치 정보를 정렬한다 -> O(nlogn)
  3) 크루스칼 알고리즘을 수행한다 -> O(nlogn + n * O(union))
  4) 따라서 최종 시간 복잡도는 O(nlogn + n * O(union))이다.

"""
def find(x):
  if groups[x] == x:
    return x

  groups[x] = find(groups[x])

  return groups[x]


def union(x, y):
  x, y = find(x), find(y)

  if x > y:
    x, y = y, x

  groups[y] = x


import sys

input = sys.stdin.readline
n = int(input().rstrip())
groups = list(range(n + 1))
costs = []
graph = []
answer = 0

for i in range(n):
  x, y, z = map(int, input().split())
  costs.append((i, x, y, z))

x_info = costs[:]
y_info = costs[:]
z_info = costs[:]

x_info.sort(key=lambda x: x[1])
y_info.sort(key=lambda x: x[2])
z_info.sort(key=lambda x: x[3])

for i in range(n - 1):
  graph.append((x_info[i + 1][1] - x_info[i][1], x_info[i][0], x_info[i + 1][0]))
  graph.append((y_info[i + 1][2] - y_info[i][2], y_info[i][0], y_info[i + 1][0]))
  graph.append((z_info[i + 1][3] - z_info[i][3], z_info[i][0], z_info[i + 1][0]))

# MST를 위한 간선 정렬
graph.sort()

# MST 수행
for c, a, b in graph:
  if find(a) != find(b):
    union(a, b)
    answer += c

print(answer)


