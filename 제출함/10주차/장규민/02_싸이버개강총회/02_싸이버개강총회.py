import sys

input = sys.stdin.readline

s, e, q = input().split()

start_hour, start_minute = s.split(':')
end_hour, end_minute = e.split(':')
quit_hour, quit_minute = q.split(':')


def time_diff(ori_hour, ori_minute, comp_hour, comp_minute):
    return ori_hour < comp_hour or (ori_hour == comp_hour and ori_minute <= comp_minute)


def solution():
    answer = 0
    
    students = {}

    while True:
        try:
            time, nickname = input().split()
            hour, minute = time.split(':')

            if students.get(nickname) is None and time_diff(hour, minute, start_hour, start_minute):
                students[nickname] = True
                continue

            if students.get(nickname) is not None \
               and time_diff(end_hour, end_minute, hour, minute) \
               and time_diff(hour, minute, quit_hour, quit_minute):
                students.pop(nickname)
                answer += 1
                
        except:
            print(answer)
            break


solution()
