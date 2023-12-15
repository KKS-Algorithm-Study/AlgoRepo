n, m = map(int, input().split())
trees = list(map(int, input().split()))

left, right = 0, max(trees)
answer = 0

while left <= right:
  mid = (left + right) // 2
  sum_tree = 0

  for tree in trees:
    if tree > mid:
      sum_tree += tree - mid

  if sum_tree < m:
    right = mid - 1
  else:
    left = mid + 1
    answer = max(mid, answer)

print(answer)