"""
1. 문제 접근 방식
  1) 가장 큰 범위의 Z를 생각했을 때 찾고자하는 지점이 몊번 째의 사분면에 있는지 계속 찾는다.
  2) 범위를 계속 좁혀가면서 칸을 더해준다.

2. 시간 복잡도
  1) n번만 while 문을 반복하면 되므로 O(N)이다.

"""
def find_plane(left_upper, right_upper, left_lower, target):
  x, y = target
  mid_x = (left_upper[0] + left_lower[0]) // 2
  mid_y = (left_upper[1] + right_upper[1]) // 2
  length = (right_upper[1] - left_upper[1] + 1) // 2

  if mid_x - length < x <= mid_x:
    if mid_y - length < y <= mid_y:
      plane_num = 1
    else:
      plane_num = 2
  else:
    if mid_y - length < y <= mid_y:
      plane_num = 3
    else:
      plane_num = 4

  return (plane_num, length)


n, r, c = map(int, input().split())
left_upper = (0, 0)
right_upper = (0, 2**n - 1)
left_lower = (2**n - 1, 0)
result = 0

while left_upper[0] < left_lower[0] :
  plane_num, length = find_plane(left_upper, right_upper, left_lower, (r, c))
  result += (plane_num - 1) * (length ** 2)

  # 몇사분면인지에 따라 좁힐 범위를 넣어줌
  if plane_num == 1:
    right_upper = (right_upper[0], right_upper[1] - length)
    left_lower = (left_lower[0] - length, left_lower[1])
  elif plane_num == 2:
    left_upper = (left_upper[0], left_upper[1] + length)
    left_lower = (left_lower[0] - length, left_lower[1] + length)
  elif plane_num == 3:
    left_upper = (left_upper[0] + length, left_upper[1])
    right_upper = (right_upper[0] + length, right_upper[1] - length)
  else:
    left_upper = (left_upper[0] + length, left_upper[1] + length)
    right_upper = (right_upper[0] + length, right_upper[1])
    left_lower = (left_lower[0], left_lower[1] + length)

print(result)