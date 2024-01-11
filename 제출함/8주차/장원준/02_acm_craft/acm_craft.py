"""
파이파이는 맞음. 파이썬은 틀림 왜?
1. 문제 접근 방식
  1) 앞선 건물이 먼저 완료된 후 다음 건물을 만들 수 있으므로 위상 정렬 알고리즘을 사용한다.
  2) 특정 레벨의 건물들이 모두 건설 완료되어야 다음 건물을 건설할 수 있으므로 매번 이전의 최대값을 찾는다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(T)
  2) 위상정렬을 수행하면서 이전 레벨에 속한 건물의 완료시간 중 최대 값을 찾는다 -> O(N + K * N)
  3) 최종 시간 복잡도는 O(T * (N**2 + N*K))이다.

3. DP를 이용하면 시간 복잡도를 줄일 수 있다.
"""
import sys
from collections import deque

input = sys.stdin.readline

def copleted_times(num_buildings,from_graph, to_graph, indegree, building_times):
  # 이전 레벨에서 가장 늦게 끝난 시간과 나의 빌딩 시간을 더한 값을 저장함
  completed_times = {}
  queue = deque()

  for i in range(1, num_buildings + 1):
    # dict 초기화
    completed_times[i] = 0

    # 진입 차수가 0인 노드를 큐에 추가
    if indegree[i] == 0:
      queue.append((i, building_times[i]))


  # 위상 정렬 시작
  while queue:
    now, now_building_time = queue.popleft()
    completed_times[now] = completed_times.get(now, 0) + now_building_time

    max_time = 0

    for next in to_graph[now]:
      indegree[next] -= 1

      max_time = max(max_time, ddd)
      if indegree[next] == 0:
        queue.append((next, prev_max_building_time + building_times[next]))

    # for next in to_graph[now]:
    #   prev_max_building_time = 0
    #
    #   # next로 들어오는 노드 중 최대의 시간을 가지는 값을 계산한다.
    #   for prev in from_graph[next]:
    #     prev_max_building_time = max(prev_max_building_time, completed_times.get(prev, 0))
    #
    #   indegree[next] -= 1
    #
    #   if indegree[next] == 0:
    #     queue.append((next, prev_max_building_time + building_times[next]))

  return completed_times


test_cases = int(input().rstrip())

for _ in range(test_cases):
  n, k = map(int, input().split())
  build_times = [0] + list(map(int, input().split()))

  to_graph = [[] for _ in range(n + 1)]
  from_graph = [[] for _ in range(n + 1)]
  indegree = [0] * (n + 1)

  for _ in range(k):
    x, y = map(int, input().split())
    # 특정 노드 기준으로, 다음 노드와 나한테 들어오는 노드를 알아야 하기 때문에
    # to_graph, from_graph를 나누어 입력
    to_graph[x].append(y)
    from_graph[y].append(x)
    indegree[y] += 1

  target = int(input().rstrip())
  completed_times = copleted_times(n, from_graph, to_graph, indegree, build_times)

  print(completed_times[target])