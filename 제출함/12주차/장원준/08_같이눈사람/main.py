"""
1. 문제 접근 방식
  1) 총 4개를 골라야하므로, 2개는 이중 for문, 나머지 2개는 투 포인터로 고르자.

2. 시간 복잡도
  1) 2개를 고르기 위한 이중 for문 -> O(n**2)
  2) 2개를 고르고 나머지 2개를 고르기 위한 투 포인터 -> O(n)
  3) 따라서 최종 시간 복잡도는 O(n**3)이다.

"""
n = int(input())
snows = [*map(int, input().split())]
answer = int(1e9)

snows.sort()

for i in range(n):
  for j in range(i + 3, n):
    left, right = i + 1, j - 1

    while left < right:
      tmp = (snows[i] + snows[j]) - (snows[left] + snows[right])

      if abs(answer) > abs(tmp):
        answer = abs(tmp)

      if tmp < 0:
        right -= 1
      else:
        left += 1

print(answer)