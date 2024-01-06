# CP template Version 1.006
import os
import sys
#import string
#from functools import cmp_to_key, reduce, partial
#import itertools
#from itertools import product
#import collections
#from collections import deque
#from collections import Counter, defaultdict as dd
#import math
#from math import log, log2, ceil, floor, gcd, sqrt
#from heapq import heappush, heappop
#import bisect
#from bisect import bisect_left as bl, bisect_right as br
DEBUG = False

# 문제 접근 방법:
#   N*N 크기의 체스판에 서로를 공격할 수 없도록 N개의 퀸을 놓는 경우의 수를 구하는 문제
#
#   백트래킹 문제이나, N*N 체스판의 모든 칸을 순회하며 퀸을 놓을지 말지 결정하면 
#   최대 연산 횟수 2**(N**2)에 N 최대 14, 즉 2**196 이므로 시간초과가 난다.
#
#   따라서 퀸의 이동 특성을 이용해서 가지치기를 최대한 효율적으로 이끌어내야 한다.
#   1. 퀸은 가로, 세로, 대각선으로 이동 가능한 기물이므로, 한 행에 아무리 많아도 퀸은 하나밖에 놓지 못한다.
#   2. 한 행에 하나밖에 못놓는데, 어떤 행이든 퀸을 못놓게 되면 N개의 퀸을 놓을 수 없게 된다.
#
#   이 특성을 이용해, 각 행마다 퀸을 놓을 장소를 탐색하면서
#   한 곳에 놓았을 때 나머지 행의 같은 열을 모두 잠그고, 왼쪽 대각선, 오른쪽 대각선까지 잠가
#   마지막 행까지 퀸을 놓을 수 있다면 경우의 수에 +1을 해주면 된다. 
#
#   이렇게 까지 구현하면 N의 최대가 12인 프로그래머스에선 정답을 받을 수 있지만, 
#   14인 백준에서는 시간초과의 늪에 빠지게 된다.
#   
#   따라서 풀이 방법은 두 개로 나뉘는데,
#   1. 14까지의 연산 결과를 리터럴 배열로 저장해놓고 그 결과를 N에 맞춰 출력하는 코드를 제출하는 방법
#   2. 시간 복잡도를 최대한 줄여 풀어내는 방법
#   
#   밑의 풀이는 2번 풀이 방법으로, 
#   재귀 없이 while 문으로 풀어 함수 콜에 들어가는 오버헤드를 없애고
#   셋 대신 비트마스킹을 이용해 반복문을 없앴다.
#
# 시간 복잡도:
#   백트래킹이므로, O(N!)이다.

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########
    
    N = int(input().strip())
    allPlace = (1 << N) - 1
    placeables = [allPlace]
    blockedColumns = [0]
    blockedLeftDiags = [0]
    blockedRightDiags = [0]
    count = 0

    while len(placeables):
        # 더 이상 놓을 장소 없으면 뒤로 돌아가기
        if placeables[-1] == 0:
            placeables.pop()
            blockedColumns.pop()
            blockedLeftDiags.pop()
            blockedRightDiags.pop()
            continue

        # 이번 행에 놓을 장소 일단 뽑고
        place = placeables[-1] & -placeables[-1]
        placeables[-1] -= place
        # 다음 행에 놓을 때, 열, 좌대각, 우대각 못 놓는 곳 확인
        nextBlockedColumn = blockedColumns[-1] | place
        nextBlockedLeftDiags = (blockedLeftDiags[-1] | place) << 1
        nextBlockedRightDiags = (blockedRightDiags[-1] | place) >> 1
        nextPlaceable = allPlace & ~(nextBlockedColumn | nextBlockedLeftDiags | nextBlockedRightDiags)
        # 다음 행에 놓을 장소가 있으면 더해주고, 없으면 길이 체크해서 +1
        if nextPlaceable != 0:
            placeables.append(nextPlaceable)
            blockedColumns.append(nextBlockedColumn)
            blockedLeftDiags.append(nextBlockedLeftDiags)
            blockedRightDiags.append(nextBlockedRightDiags)
        else:
            if len(placeables) == N:
                count += 1

    return count

    # ######## INPUT AREA END ############


# TEMPLATE ###############################


enu = enumerate


def For(*args):
    return itertools.product(*map(range, args))


def Mat(h, w, default=None):
    return [[default for _ in range(w)] for _ in range(h)]


def nDim(*args, default=None):
    if len(args) == 1:
        return [default for _ in range(args[0])]
    else:
        return [nDim(*args[1:], default=default) for _ in range(args[0])]


def setStdin(f):
    global DEBUG, input
    DEBUG = True
    sys.stdin = open(f)
    input = sys.stdin.readline


def init(f=None):
    global input
    input = sys.stdin.readline  # io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
    if os.path.exists("o"):
        sys.stdout = open("o", "w")
    if f is not None:
        setStdin(f)
    else:
        if len(sys.argv) == 1:
            if os.path.isfile("in/i"):
                setStdin("in/i")
            elif os.path.isfile("i"):
                setStdin("i")
        elif len(sys.argv) == 2:
            setStdin(sys.argv[1])
        else:
            assert False, "Too many sys.argv: %d" % len(sys.argv)


def pr(*args):
    if DEBUG:
        print(*args)


def pfast(*args, end="\n", sep=' '):
    sys.stdout.write(sep.join(map(str, args)) + end)


def parr(arr):
    for i in arr:
        print(i)


if __name__ == "__main__":
    print(main())