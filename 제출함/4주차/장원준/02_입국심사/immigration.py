n, m = map(int, input().split())

times = []

for _ in range(n):
  times.append(int(input()))

left, right = 0, max(times) * m
answer = 0

while left <= right:
  mid = (left + right) // 2
  people = 0

  for time in times:
    # 특정 입국 검사대에 mid 초 동안 몇 명을 입국검사 할 수 있는지 체크
    people += mid // time

  if people < m:
    left = mid + 1
  else:
    # 이 경우는 mid초 동안 조건인 m명 이상의 사람이 입국 검사를 통과하는 경우이다.
    # 최소한 people이 n과 같거나 커야 정답의 가능성이 있으므로 여기서 answer = mid 코드를 넣어준다.
    answer = mid
    right = mid - 1

print(answer)