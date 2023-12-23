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
#   N 개의 서로 다른 동전을 사용해 M원을 거슬러 줄 수 있는 경우의 수를 찾는 문제
#   
#   완전 탐색으로는 풀 수 없는 DP 문제로,
#   DP 배열에는 "i원을 거슬러주는 모든 경우의 수"를 저장하면 된다
#   
#   j원 동전을 사용해 i원을 거슬러주는 경우를 0 ~ M원까지 탐색하며 더해주면 된다
#   예를 들어 [1, 4, 5]원으로 8원을 거슬러주는 경우의 수를 찾는다면
#     일단 dp 배열 초기화는 [1, 0, 0, 0...] 으로 해주고
#       1원 동전으로 1원을 거슬러주는 경우의 수 => dp[1] += dp[1 - 1] => 1
#       1원 동전으로 2원을 거슬러주는 경우의 수 => dp[2] += dp[2 - 1] => 1
#       1원 동전으로 3원을 거슬러주는 경우의 수 => dp[3] += dp[3 - 1] => 1 ...
#     이때 dp 배열은 [1, 1, 1, 1...] 이 되고,
#       1원과 2원 동전으로 1원을 거슬러주는 경우의 수 => 2원으로는 1원을 거슬러줄 수 없으므로, dp[1] => 1
#       1원과 2원 동전으로 2원을 거슬러주는 경우의 수 => dp[2] += dp[2 - 2] => 2
#       1원과 2원 동전으로 3원을 거슬러주는 경우의 수 => dp[3] += dp[3 - 2] => 2
#     이때 dp 배열은 [1, 1, 2, 2...] 이 되고,
#   이런 식으로 나머지 동전도 계산해 출력하면 된다.
#   
# 시간 복잡도:
#   동전 갯수 N개를 거스름돈 0 ~ M원까지 돌게 되므로, O(N * M)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    coinCount, totalChangeValue = map(int, input().split())
    coinValues = []
    for _ in range(coinCount):
        coinValue = int(input().strip())
        if coinValue <= totalChangeValue:
            coinValues.append(coinValue)

    changeValueDp = [0] * (totalChangeValue + 1)
    changeValueDp[0] = 1
    
    for coinValue in coinValues:
        for changeValue in range(coinValue, len(changeValueDp)):
            changeValueDp[changeValue] += changeValueDp[changeValue - coinValue]

    return changeValueDp[totalChangeValue]
    

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