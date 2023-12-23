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
#   N개의 집이 있고 3개의 페인트가 있는데,
#   각 집마다 페인트 칠 비용이 다르고, 옆 집과 다른 색으로 페인트를 칠해야할 때,
#   최소 비용으로 페인트 칠하는 방법을 구하는 문제
#
#   집이 최대 1000개라서, 모든 조합을 탐색하려면 3 ** 1000 번의 연산횟수가 필요해 완전탐색은 불가능하다
#
#   DP 배열에는 "이 페인트를 골랐을 때 내야하는 최소비용" 을 DP에 저장해 풀 수 있다
#   
# 시간 복잡도:
#   N개의 집마다 3개의 페인트를 칠하는 경우를 계산하므로, O(N)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    houseCount = int(input())
    paintCosts = [list(map(int, input().split())) for _ in range(houseCount)]
    
    for houseNumber in range(1, len(paintCosts)):
        paintCosts[houseNumber][0] = paintCosts[houseNumber][0] + min(paintCosts[houseNumber-1][1], paintCosts[houseNumber-1][2])
        paintCosts[houseNumber][1] = paintCosts[houseNumber][1] + min(paintCosts[houseNumber-1][0], paintCosts[houseNumber-1][2])
        paintCosts[houseNumber][2] = paintCosts[houseNumber][2] + min(paintCosts[houseNumber-1][0], paintCosts[houseNumber-1][1])

    return min(paintCosts[-1])

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