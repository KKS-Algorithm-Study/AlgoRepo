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
#   
#   2*i 크기의 직사각형을 1*2 타일로 채워넣는 방법의 수를 구하는 문제.
#   
#   전형적인 DP 문제다.
#   dp의 i번째 항이 "2*i 칸을 타일로 채우는 방법의 수"를 저장한다고 할 때,
#   
#   2*i 크기가 타일로 꽉 채워지는 방법의 수는,
#       2*(i-1) 크기를 꽉 채우고 세로 타일을 딱 놓는 경우 (dp[i-1]에 저장된 것)
#       2*(i-2) 크기를 꽉 채우고 가로 타일 두개를 놓는 경우 (dp[i-2]에 저장된 것) 
#   이 두 경우를 더한 것이다.
#   
# 시간 복잡도:
#   N만큼 for문을 돌기 때문에 O(N)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    N = int(input())
    dp = [1] * (N + 1)
    for i in range (2, N + 1):
        dp[i] = (dp[i-1] + dp[i-2]) % 10007

    return dp[N]

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