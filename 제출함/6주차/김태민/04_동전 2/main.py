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
#   N 개의 서로 다른 동전을 사용해 M원을 거슬러 줄 수 있는 최소 동전의 갯수를 찾아주는 문제
#   
#   "최대 값의 동전을 가장 많이 줘서 동전 갯수를 찾는" 그리디 방법은 사용하지 못한다
#   예를 들어 [1, 4, 5] 원으로 8원을 거슬러준다면, 그리디 방식으로는 5+1+1+1로 4개가 필요하지만, 정답은 4+4로 2개이다.
#
#   이 역시 DP를 사용해야 하며, 
#   DP 배열에는 "i원을 거슬러주는 최소 동전의 갯수"를 저장하면 된다.
#   
# 시간 복잡도:
#   동전 갯수 N개를 거스름돈 0 ~ M원까지 돌게 되므로, O(N * M)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    INF = int(1e9)
    coinCount, totalChangeValue = map(int, input().split())
    # 가치가 같은 동전이 주어질 수 있으므로, set을 사용해 중복 값을 걸러주었다
    coinValues = set(int(input().strip()) for _ in range(coinCount))

    changeValueDp = [INF] * (totalChangeValue + 1)
    changeValueDp[0] = 0

    for coinValue in coinValues:
        for changeValue in range(coinValue, totalChangeValue+1):
            changeValueDp[changeValue] = min(changeValueDp[changeValue], changeValueDp[changeValue - coinValue] + 1)
    
    return changeValueDp[-1] if changeValueDp[-1] != INF else -1
        
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