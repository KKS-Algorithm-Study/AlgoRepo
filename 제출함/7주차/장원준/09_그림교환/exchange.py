"""
답보고 품

아이디어 -> TSP랑 똑같이 dfs로 풀고


1. 문제 접근 방식 (답 보고 품)
  1) dfs로 사고 판 경로를 저장 (비트 마스킹 사용)
  2) dp에는 다음과 같이 저장한다.
    - dp[마지막에 산 사람][마지막에 산 사람까지 오게된 경로] = 최대명수

2. 시간 복잡도
  1) dfs가 최대 2**n * n번 만큼 수행됨 (배열 크기만큼)
  2) 1번의 dfs에서 for문으로 n번 탐색
  3) 최종 시간복잡도는 O(2**n * n**2)이다.
"""
import sys
input = sys.stdin.readline

n = int(input().rstrip())
graph = [list(map(int, input().rstrip())) for _ in range(n)]
dp = [[0] * (1 << n) for _ in range(n)]

def dfs(current_person, visited, prev_price):
  if dp[current_person][visited] != 0:
    return dp[current_person][visited]

  max_people = 0

  for next_person in range(n):
    if visited & (1 << next_person):
      continue

    if prev_price <= graph[current_person][next_person]:
      max_people = max(max_people, dfs(next_person, visited | (1 << next_person), graph[current_person][next_person]) + 1)

  dp[current_person][visited] = max_people

  return max_people

print(dfs(0, 1, 0) + 1)
