"""
1. 문제 접근 방식
  1) 그리디 알고리즘으로 접근한다.
  2) 먼저 1차 시험 결과로 정렬하면 서류 1위는 무조건 합격이다.
    ** 그러므로 서류 1위의 면접 순위보다 낮으면 탈락된다.
  3) 지원자를 순서대로 탐색하며 이미 합격한 사람의 면접 순위와 비교한다.
  4) 만약 합격자가 나오면 비교할 이전 순위를 합격자의 면접 순위로 바꿔준다.

2. 시간 복잡도
  1) 테스트 케이스만큼 반복문을 수행한다. -> O(t)
  2) 1번의 테스트 케이스에서 면접자의 정보를 정렬하는 것이 가장 오래걸리므로 O(nlogn)이다.
  3) 따라서 최종 시간 복잡도는 O(t * nlogn)이다.

"""
import sys

input = sys.stdin.readline
test_cases = int(input().rstrip())

for _ in range(test_cases):
  arr = []
  n = int(input().rstrip())
  for _ in range(n):
    arr.append(tuple(map(int, input().split())))

  # 1차 서류시험 성적 순으로 정렬
  arr.sort()

  # 지원자를 순회하며 2차 순위를 비교한다.
  answer = 0
  prev_degree2 = arr[0][1]

  for degree1, degree2 in arr:
    # 만약 나보다 2차 순위가 높으면 기준을 그 사람으로 변경한다.
    if prev_degree2 >= degree2:
      prev_degree2 = degree2
      answer += 1

  print(answer)
