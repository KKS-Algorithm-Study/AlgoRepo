"""
1. 문제 접근 방식 (답 보고 품)
  1) 처음에 위상 정렬 후 루트부터 타고 내려오는 것을 생각함
  2) 근데 답이 안나와서 답을 찾아봄
  3) 리프 노드부터 루트로 올라오며 값을 갱신해오면 되었다.

2. 시간 복잡도
  1) 위상 정렬을 통해 노드, 간선을 모두 탐색하므로 O(n + n) = O(n)이다.
  2) 각 노드를 탐색할 때마다 역순 정렬을 하므로 O(NlogN)이다.
  3) 따라서 최종 시간 복잡도는 O(N**2logN)이다.
"""

from collections import deque

n = int(input())

if n == 1:
  print(0)
  exit()

arr = [*map(int, input().split())]
# arr = list(map(int, input().split()))
child = [0] * n

# 특정 인덱스의 리스트에 자식으로부터 몇 초를 지나 도착했는지 넣어줌
val = [[] for _ in range(n)]

# 각 노드가 직속 부하를 얼마나 가지고 있는지 체크
for idx in arr[1:]:
  child[idx] += 1

queue = deque()

# 마지막 부하부터 타고올라갈 것이므로 리프 노드를 큐에 넣어줌
for i in range(1, len(arr)):
  if child[i] == 0:
    # 자신의 인덱스와 시간 정보를 넣어줌
    queue.append([i, 0])

while queue:
  idx, time = queue.popleft()

  if idx == 0:
    print(time)

  # 부모의 인덱스를 가져옴
  parent = arr[idx]
  val[parent].append(time + 1)
  child[parent] -= 1

  # 모든 자식을 방문한 부모 노드일 때
  # 부모 노드까지 도달한 자식들의 값을 계산
  if child[parent] == 0:
    val[parent].sort(reverse=True)
    children_times = []

    for i in range(len(val[parent])):
      # 자식들을 1번에 1명씩 밖에 방문을 못하니까
      # 이미 시간이 오래 걸린 자식을 먼저 방문한다는 의미로 sort후 i초씩 더해줌
      children_times.append(val[parent][i] + i)

    queue.append([parent, max(children_times)])