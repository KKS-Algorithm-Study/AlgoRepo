import sys

input = sys.stdin.readline

t = int(input())


def solution(paper):

    length = len(paper)
    if length == 1:
        return True
    
    for i in range(length // 2):
        if paper[i] == paper[length - i - 1]:
            return False

    return solution(paper[:length // 2]) and solution(paper[length // 2 + 1:])


for _ in range(t):
    paper = input().rstrip()

    if solution(paper):
        print('YES')
    else:
        print('NO')
