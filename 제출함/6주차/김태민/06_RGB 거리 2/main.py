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
#   RGB 거리 문제처럼 옆 집과 다른 색으로 페인트를 칠해야하지만 첫 집과 마지막 집도 서로 인접한 경우,
#   최소 비용으로 페인트 칠하는 방법을 구하는 문제
#
#   마찬가지로 DP 배열에 "이 페인트를 골랐을 때 내야하는 최소비용" 을 저장하되,
#       "첫집을 빨간색으로 고르고(빨간색 제외 나머지 무한대), 마지막 집을 빨간색을 고르지 않은 경우"
#       "첫집을 초록색으로 고르고(초록색 제외 나머지 무한대), 마지막 집을 초록색을 고르지 않은 경우"
#       "첫집을 파란색으로 고르고(파란색 제외 나머지 무한대), 마지막 집을 파란색을 고르지 않은 경우"
#   위 경우들의 최소값을 출력하면 된다
#   
# 시간 복잡도:
#   N개의 집마다 3개의 페인트를 칠하는 경우를 총 3번 계산하므로, O(N)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    INF = int(1e9)
    houseCount = int(input().strip())
    paintCosts = [list(map(int, input().split())) for _ in range(houseCount)]
    answer = INF

    for col in range(3):
        paintCostDp = [[INF] * 3 for _ in range(houseCount)]
        paintCostDp[0][col] = paintCosts[0][col]
        for houseNumber in range(1, houseCount):
            paintCostDp[houseNumber][0] = paintCosts[houseNumber][0] + min(paintCostDp[houseNumber-1][1], paintCostDp[houseNumber-1][2])
            paintCostDp[houseNumber][1] = paintCosts[houseNumber][1] + min(paintCostDp[houseNumber-1][0], paintCostDp[houseNumber-1][2])
            paintCostDp[houseNumber][2] = paintCosts[houseNumber][2] + min(paintCostDp[houseNumber-1][0], paintCostDp[houseNumber-1][1])
        paintCostDp[-1][col] = INF

        answer = min(answer, min(paintCostDp[-1]))

    return answer

    # ######## INPUT AREA END ############
        
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