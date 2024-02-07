"""
1. 문제 접근 방식
  1) 에라토스테네스의 체를 이용하자.

2. 시간 복잡도
  1) 에라토스테네스의 체를 만든다 -> O(mloglogm)
  2) 소수를 찾는데 n ~ m + 1만큼 반복한다. -> O(m - n)
  3) 따라서 최종 시간 복잡도는 O(m - n)이다.

"""
def sieve_of_eratosthenes(upper_bound):
  sieve = [False, False] + [True] * (upper_bound - 1)

  for i in range(2, upper_bound + 1):
    if sieve[i] == True:
      for j in range(i * i, upper_bound + 1, i):
        sieve[j] = False

  return sieve


n, m = map(int, input().split())
sieve = sieve_of_eratosthenes(m)

for i in range(n, m + 1):
  if sieve[i]:
    print(i)