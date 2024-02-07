"""
1. 문제 접근 방식
  1) bfs로 풀이
    ** dfs로 풀면 아쉬운 이유는, 너무 깊게 탐색을 해야하기 때문 (1번의 테스트 케이스 당 최대 9000번)
    ** 또한 to_num을 만났을 때 최소 거리라는 게 보장이 안되므로 끝까지 탐색을 해야 함
    ** 다음 소수까지 갈 때 1자리의 소수만 바꾸면 되므로 거리가 1인 그래프라고 생각할 수 있는 것 같음

  2) 1개의 depth에서 가능한 4자리의 소수를 set에 담아 다음 탐색 대상으로 지정한다.

2. 시간 복잡도
  1) t개의 테스트 케이스를 수행한다 -> O(t)
  2) 1개의 테스트 케이스 당 bfs를 수행한다. -> 4자리 수의 갯수를 n이라고 할 때 O(n)이다.
    ** 참고로 1000 ~ 9999의 범위에 속하는 소수는 1061개이다.

"""
from collections import deque

def sieve_of_eratosthenes():
  upper_bound = 10000
  sieve = [False, False] + [True] * (upper_bound - 2)

  for i in range(2, upper_bound):
    if sieve[i] == True:
      for j in range(i * i, upper_bound, i):
        sieve[j] = False

  return sieve


def get_next_primes(number):
  primes = set()

  first_digit = number // 1000
  second_digit = (number % 1000) // 100
  third_digit = (number % 100) // 10
  fourth_digit = number % 10

  # 첫번 째 자리 바꾸기
  for i in range(1, 10):
    tmp = i * 1000 + second_digit * 100 + third_digit * 10 + fourth_digit
    if sieve[tmp]:
      primes.add(tmp)

  # 두번 째 자리 바꾸기
  for i in range(10):
    tmp = first_digit * 1000 + i * 100 + third_digit * 10 + fourth_digit
    if sieve[tmp]:
      primes.add(tmp)

  # 세번 째 자리 바꾸기
  for i in range(10):
    tmp = first_digit * 1000 + second_digit * 100 + i * 10 + fourth_digit
    if sieve[tmp]:
      primes.add(tmp)

  # 네번 째 자리 바꾸기
  for i in range(10):
    tmp = first_digit * 1000 + second_digit * 100 + third_digit * 10 + i
    if sieve[tmp]:
      primes.add(tmp)

  return primes


t = int(input())
INF = int(1e9)
sieve = sieve_of_eratosthenes()

for _ in range(t):
  answer = INF
  from_num, to_num = map(int, input().split())

  queue = deque([(from_num, 0)])
  visited = [False] * 10000

  while queue:
    now, depth = queue.popleft()

    if now == to_num:
      answer = min(answer, depth)

    for next in get_next_primes(now):
      if not visited[next]:
        queue.append((next, depth + 1))
        visited[next] = True

  if answer == INF:
    print('Impossible')
  else:
    print(answer)