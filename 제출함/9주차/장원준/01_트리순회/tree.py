"""
1. 문제 접근 방식
  1) 이진트리이므로 left, right만 신경쓰면 됨
  2) 전위, 중위, 후위 순서에 맞게 탐색 후 출력

2. 시간 복잡도
  1) 모든 노드를 탐색하므로 O(n)의 시간복잡도를 가진다.
"""

def parse_to_num(ch):
  return ord(ch) - 65

def parse_to_ch(num):
  return chr(num + 65)


# 전위 순회
def pre_order(now, visited, result):
  visited[now] = True
  result.append(parse_to_ch(now))

  left, right = graph[now]

  if left != '.' and not visited[left]:
    pre_order(left, visited, result)

  if right != '.' and not visited[right]:
    pre_order(right, visited, result)

  return result


def in_order(now, visited, result):
  left, right = graph[now]

  if left != '.' and not visited[left]:
    in_order(left, visited, result)

  visited[now] = True
  result.append(parse_to_ch(now))

  if right != '.' and not visited[right]:
    in_order(right, visited, result)

  return result


def post_order(now, visited, result):
  left, right = graph[now]

  if left != '.' and not visited[left]:
    post_order(left, visited, result)

  if right != '.' and not visited[right]:
    post_order(right, visited, result)

  visited[now] = True
  result.append(parse_to_ch(now))

  return result


n = int(input())
graph = {}

for _ in range(n):
  node, left, right = input().split()

  node = parse_to_num(node)

  if left != '.':
    left = parse_to_num(left)

  if right != '.':
    right = parse_to_num(right)

  graph[node] = (left, right)


pre_order_result = []
visited = [False] * n
print(''.join(pre_order(0, visited, pre_order_result)))

in_order_result = []
visited = [False] * n
print(''.join(in_order(0, visited, in_order_result)))

post_order_result = []
visited = [False] * n
print(''.join(post_order(0, visited, post_order_result)))
