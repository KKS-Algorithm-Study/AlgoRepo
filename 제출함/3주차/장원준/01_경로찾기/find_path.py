"""
1. 문제 접근 방식
  1) "모든 정점에 대해" 특정 지점까지 갈 수 있는 경로가 있는지 구해야 하는 문제
    -> 플로이드 워셜 알고리즘 이용

2. 시간 복잡도
  1) 플로이드 워셜 알고리즘은 3중 반복문을 사용하므로 시간 복잡도는 O(N^3)이다.
"""

n = int(input())
graph = [list(map(int, input().split())) for _ in range(n)]

for x in range(n):
  for y in range(n):
    for z in range(n):
      # 두 개를 통해 갈 수 있다면!
      if graph[y][x] and graph[x][z]:
        graph[y][z] = 1

for i in range(n):
  for j in range(n):
    print(graph[i][j], end=" ")
  print()