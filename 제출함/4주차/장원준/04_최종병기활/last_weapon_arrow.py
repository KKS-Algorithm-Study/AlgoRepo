n, m, k = map(int, input().split())
arr = [int(input()) for _ in range(m)]
range_arr = []

def satisfy_condition(min_len):
  for i in range(len(arr)):
    cnt = 0
    start = i

    for j in range(i + 1, i + m + 1):
      next = j % m

      if arr[next] - arr[start] <= 0:
        length = arr[next] - arr[start] + n
      else:
        length = arr[next] - arr[start]

      if length >= min_len:
        cnt += 1
        start = next

    if cnt >= k:
      return True

  return False


left, right = 1, n
answer = 0  # k개로 고무줄을 잘랐을 때 고무줄의 최소 길이의 최대값

while left <= right:
  mid = (left + right) // 2

  if satisfy_condition(mid):
    answer = mid
    left = mid + 1
  else:
    right = mid - 1

print(answer)