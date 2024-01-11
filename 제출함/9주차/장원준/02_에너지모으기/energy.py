"""
1. 문제 접근 방식
  1) dfs로 풀기

2. 시간 복잡도
  1) O(n!)이지만, 10! = 3628800 이므로 시간 내에 풀이 가능하다.

"""
def calculate_max_energy(marbles, energy):
  global result

  if len(marbles) == 2:
    result = max(result, energy)
    return

  for i in range(1, len(marbles) - 1):
    cur_marble = marbles[i]
    cur_energy = marbles[i - 1] * marbles[i + 1]

    energy += cur_energy
    del marbles[i]

    calculate_max_energy(marbles, energy)

    energy -= cur_energy
    marbles.insert(i, cur_marble)


n = int(input())
marbles = list(map(int, input().split()))
result = 0

calculate_max_energy(marbles, 0)

print(result)