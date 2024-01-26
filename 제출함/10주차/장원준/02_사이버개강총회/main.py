"""
1. 문제 접근 방식
  1) 시간의 전후를 판단하는 함수 선언
  2) 출석한 명단의 set, 퇴장한 명단의 set을 구한다.
  3) 두 개의 set의 교잡합을 구한다.

2. 시간 복잡도
  1) 로그의 갯수를 M이라고 할 때 O(M)이다.

"""
import sys

def is_after(before, after):
  before_h, before_m = map(int, before.split(':'))
  after_h, after_m = map(int, after.split(':'))

  if after_h == before_h:
    return after_m >= before_m
  elif after_h < before_h:
    return False
  else:
    return True


input = sys.stdin.readline

start_time, end_time, quit_time = input().split()
attend_set, leave_set = set(), set()

while True:
  log = input().rstrip()

  if not log:
    break

  log_time, log_name = log.split()

  if is_after(log_time, start_time):
    attend_set.add(log_name)
  else:
    if is_after(end_time, log_time) and is_after(log_time, quit_time):
      leave_set.add(log_name)

print(len(attend_set & leave_set))
