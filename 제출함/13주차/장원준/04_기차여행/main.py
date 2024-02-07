"""
1. 문제 접근 방식 (답 보고 품)
  1) 철도의 사용 횟수를 가지고 값을 비교해야 된다는 것 까지는 알았는데, 사용횟수를 누적합으로 구하는 건 처음 봄
  2) 두 지점을 건널 때, 인덱스가 작은쪽에 +1을 해주고 끝 점을 -1해주면, 중간에 있는 길은 누적합을 구했을 떄 모두 1이 됨
  3) 따라서 이를 이용하여 철도의 사용횟수를 구할 수 있음

2. 시간 복잡도
  1) 도시의 갯수가 n이므로 최종 시간 복잡도는 O(n)이다.

"""
import sys
input = sys.stdin.readline
n, m = map(int, input().split())

# 여행중 들릴 계획
plan = list(map(int, input().split()))

# 각 철도의 가격표
cost = [list(map(int, input().split())) for _ in range(n-1)]

# 각 철도별 사용횟수를 누적합으로 구할수 있게 저장
# 철도의 숫자 최대값은 n이므로 크기는 n+1
count = [0 for _ in range(n+1)]

# 계획일정에 맞춰서 사용횟수 저장
# 단, 숫자가 낮아질경우 반대로 저장
# 3 > 1로 가는걸 1 > 3으로 가는것과 철도 사용횟수는 같다.
for i in range(m-1):
  if plan[i] < plan[i+1]:
    count[plan[i]] += 1
    count[plan[i+1]] -= 1

  else:
    count[plan[i+1]] += 1
    count[plan[i]] -= 1

sum_ = 0
answer = 0

# 누적합으로 각 철도별 실제 사용횟수를 구함
# 그다음 사용횟수에따라 ic카드 구매비용 vs 티켓사용비용을 비교해서 작은값 결과에 추가
for i in range(n - 1):
  sum_ += count[i+1]
  answer += min(cost[i][0] * sum_, cost[i][1] * sum_ + cost[i][2])

print(answer)