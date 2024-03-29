"""
1. 문제 접근 방식
  1) 최소 값 구하기
    a) 성냥개비 2개 ~ 8개를 사용하여 만들 수 있는 최소의 수를 캐싱한다.
    b) 성냥개비 9개부터는 그리디로 구해주면 된다.
    c) 단 성냥개비가 6개일 때는 1의 자리수에 0이 올수 없으므로 주의!
  2) 최대 값 구하기
    a) 최대값을 구할 때는 자릿수를 최대한 늘리자.
    b) 숫자 1개를 만드는데 최소 성냥이 필요한 1을 최대한 만들자
    c) 만약 성냥이 홀수개면 1로만 이루어진 수를 만들 수 없으므로 맨 앞자리를 7로 해주자.
  3) INF를 줄 때 int(1e9)로 주면 이 값이 최소값이 될 수 있기 때문에 float('inf')로 처리하자.

2. 시간 복잡도
  1) 성냥개비 갯수가 최대 100개 만큼 for문을 수행한다. -> O(n)
  2) 1번의 반복문 안에서 2 ~ 8까지의 반복문을 수행한다. -> O(1)
  3) 따라서 최종 시간 복잡도는 O(n)이다.
"""
n = int(input())
INF = float('inf')

# 성냥개비로 만들 수 있는 최소의 수를 dp에 저장한다.
dp = [INF] * (101)

dp[2] = 1 # 성냥개비 2개로 만들 수 있는 최소의 숫자
dp[3] = 7
dp[4] = 4
dp[5] = 2
dp[6] = 6
dp[7] = 8
dp[8] = 10

for num in range(9, 101):
  # 성냥개비 갯수를 가져온다.
  # 성냥개비의 이전 갯수에서 특정 숫자를 더했을 때의 dp를 구해준다.
  # 자릿수 주의!
  for i in range(2, 8):
    if i != 6:
      dp[num] = min(dp[num], dp[num - i] * 10 + dp[i])
    else:
      # 6인 경우에 + dp[i]를 하지 않는 건 최소 값을 구할 때
      # 성냥개비 6개로 만들 수 있는 수는 0이기 때문이다.
      dp[num] = min(dp[num], dp[num - i] * 10)

for _ in range(n):
  k = int(input())

  # 큰 수를 구할 때는 1을 최대한 많이 만들어 자릿수를 채우는 것이다.
  # 따라서 만약 성냥개비가 홀수개이면 맨 앞자리를 7, 짝수개면 1로 만든다.
  # 그리고 나머지 숫자를 1로 만들어주면 된다.
  max_value = ('7' if k % 2 else '1') + '1' * (k // 2 - 1)
  print(dp[k], max_value)