"""
1. 문제 접근 방식
  1) 재귀를 이용한다.
  2) n을 구할 수 있는 최대의 길이를 먼저 계산한다.
  3) 그 길이부터 시작하여 재귀를 사용해 범위를 좁혀나간다.

2. 시간 복잡도
  1) 재귀의 깊이를 k라고 할 때 O(k)이다.

"""
def recursive(total_len, mid_len, index):
  if total_len <= 3:
    return moo[index - 1]

  left_len = (total_len - mid_len) // 2

  if index <= left_len:
    return recursive(left_len, mid_len - 1, index)

  if index > left_len + mid_len:
    # 오른쪽인 경우 찾고자하는 index를 조정해줌 (왼쪽, 오른쪽 대칭이기 때문에 가능)
    return recursive(left_len, mid_len - 1, index - (left_len + mid_len))

  # 가운데 영역에 있을 때
  if left_len + 1 == index:
    return 'm'
  else:
    return 'o'


n = int(input())
max_len = 3
moo = ['m', 'o', 'o']

# k에 해당하는 moo 수열의 길이를 저장한다.
k = 0

while max_len < n:
  k += 1
  max_len = max_len * 2 + 1 + (k + 2)

# mid_len은 k + 3이 된다.
print(recursive(max_len, k + 3, n))
