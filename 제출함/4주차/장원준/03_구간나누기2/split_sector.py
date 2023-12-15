zn, m = map(int, input().split())
arr = list(map(int, input().split()))

def split(max_score):
  max_num = min_num = arr[0]
  num_sections = 1

  # 투 포인터로 배열을 잘라가며, 구간의 갯수를 계산한다.
  for i in range(1, n):
    max_num = max(max_num, arr[i])
    min_num = min(min_num, arr[i])

    if max_num - min_num > max_score:
      num_sections += 1
      max_num = arr[i]
      min_num = arr[i]

  return num_sections


left, right = 0, 10000
result = 0

while left <= right:
  mid = (left + right) // 2

  num_sections = split(mid)

  if num_sections <= m:
    result = mid
    right = mid - 1
  else:
    left = mid + 1

print(result)


