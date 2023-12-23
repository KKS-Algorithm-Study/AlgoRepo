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
#   실행중인 앱 N개에 각 앱이 차지하는 메모리와 재실행 비용이 있고, 다시 확보해야 하는 메모리 M이 있을 때
#   앱을 적절히 종료시켜 M 만큼의 메모리를 회수하되, 그 중 재실행 비용이 최소가 되는 조합을 찾는 문제 
#
#   배낭 무게와 비슷하지만, 이번에는 M이 최대 10,000,000이라, 
#   앱 N개를 0 ~ M까지 탐색하면 100 * 10,000,000 이라 연산횟수가 너무 많아진다
#   따라서 이 문제는 DP 배열에 메모리별 최소 비용을 저장하는 게 아닌,
#   "비용별 확보 가능 최대 메모리"를 저장해야 한다
#
#   비용은 가로축, 앱 갯수를 세로축으로 만들고,
#       이 앱을 종료시키지 않았을 때의 확보 가능 최대 메모리,
#       이 앱을 종료시키기 위해 남겨놓은 비용의 확보 가능 최대 메모리 + 이 앱을 종료해 확보 가능한 메모리
#   두 개의 값 중 최대 값을 저장시키면 된다.
#   
#   가로축의 크기는 모든 앱의 비용의 합이 되는데,
#   M만큼의 메모리를 확보하기 위해 모든 앱을 종료시켜야 할 수 있기 때문이다
#   
#   DP 배열이 완성됐다면 마지막으로 비용을 0부터 for문으로 돌면서, 
#   DP에 저장된 값이 M보다 크거나 같은 경우의 비용을 출력하면 된다 

# 시간 복잡도:
#   DP의 가로축이 비용의 합(갯수 N * 각 비용 최대 100), 세로축이 앱의 갯수 N이므로, O(N ** 2)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    appCount, necessaryMemory = map(int, input().split())
    appMemories = list(map(int, input().split()))
    disableCosts = list(map(int, input().split()))
    totalCost = sum(disableCosts)
    costDp = [0] * (totalCost + 1)

    for appNumber in range(appCount):
        disableCost = disableCosts[appNumber]
        for cost in range(totalCost, disableCost - 1, -1):
            costDp[cost] = max(costDp[cost], costDp[cost - disableCost] + appMemories[appNumber])
    
    for cost in range(totalCost + 1):
        if costDp[cost] >= necessaryMemory:
            return cost

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