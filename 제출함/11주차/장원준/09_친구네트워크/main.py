"""
1. 문제 접근 방식
  1) 2개의 id를 입력 받을 때 앞의 아이디를 부모로 취급한다.
  2) union 시 자식으로 취급되는 id의 네트워크 갯수를 부모 id에 더해준다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복한다 -> O(t)
  2) 1번의 테스트 케이스에서 f만큼 입력을 받는다 -> O(f)
  3) 1번의 id를 입력받을 때 union을 한다 -> 최대 O(k) (k는 지금까지 입력받은 친구의 수)
  4) 따라서 최종 시간복잡도 -> O(t* f * k)이다.

"""
import sys

input = sys.stdin.readline
test_case = int(input().rstrip())

def find(x):
  if friends[x] == x:
    return x

  friends[x] = find(friends[x])
  return friends[x]

def union(x, y):
  x, y = find(x), find(y)

  if x != y:
    friends[y] = x
    networks[x] += networks[y]


for _ in range(test_case):
  f = int(input().rstrip())
  friends = {}
  networks = {}

  for _ in range(f):
    id1, id2 = input().split()

    if not id1 in friends:
      friends[id1] = id1
      networks[id1] = 1

    if not id2 in friends:
      friends[id2] = id2
      networks[id2] = 1

    union(id1, id2)

    print(networks[find(id1)])