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
#   배낭에 들어갈 수 있는 무게 제한이 K, 물건의 갯수 N, 그리고 각 물건마다 무게와 그 물건의 가치가 다를 때
#   배낭에 넣을 수 있는 물건 조합 중 최대 가치를 구하는 문제
#
#   배낭 무게 제한을 가로축, 물건 갯수를 세로축으로 2차원 DP 배열을 만들고,
#   "물건 중에서 1 ~ {row}번 까지 물건을 사용해 배낭의 무게 제한 중 {col} 까지 채울 때 얻을 수 있는 최대 가치"를 저장한다.
#   각 계산은 
#        이 물건을 넣지 않았을 경우의 최대 가치, 
#        이 물건을 넣기 위해 무게를 남겨뒀을 때의 최대 가치 + 이 물건의 가치
#   이 두 값을 비교해 둘 중 큰 것으로 계산하면 된다
#   
# 시간 복잡도:
#   N개의 물건을, 0 ~ K까지 무게제한에 넣을 수 있는지 탐색하므로, O(N*K)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    itemCount, backpackLimit = map(int,input().split())
    items = [list(map(int, input().split())) for _ in range(itemCount)]

    backpackDP = [0] * (backpackLimit + 1)
    for currentItemWeight, currentItemValue in items:
        for backpackSpace in range(backpackLimit, currentItemWeight - 1, -1):
            valueWithoutCurrentItem = backpackDP[backpackSpace - currentItemWeight]
            backpackDP[backpackSpace] = max(backpackDP[backpackSpace], valueWithoutCurrentItem + currentItemValue)

    return backpackDP[-1]

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
    input = sys.stdin.readline  # by default
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