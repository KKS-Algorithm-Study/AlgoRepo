# CP template Version 1.006
import os
import sys
#import string
#from functools import cmp_to_key, reduce, partial
#import itertools
#from itertools import combinations
#import collections
#from collections import deque
#from collections import Counter, defaultdict as dd
#import math
#from math import log, log2, ceil, floor, gcd, sqrt
# from heapq import heappush, heappop
#import bisect
# from bisect import bisect_left as bl
DEBUG = False

# 문제 접근 방법:
#   건물의 건설 시간이 각자 다르고,
#   어떤 건물을 건설하기 전에 먼저 건설해야 하는 건물이 주어질 때,
#   특정 건물을 최대한 빨리 건설하려면 얼마나 걸리는지 계산하는 문제
#
#   작업의 순서가 있기 때문에 위상 정렬 문제다.
#   위상 정렬을 돌리며, {이 건물을 짓는데 걸렸던 시간 + 다음 건물을 짓는데 걸리는 시간} 을 
#   dp[다음 건물 짓는 시간] 에 최댓값으로 갱신하며 저장해주면 된다.
#   
# 시간 복잡도:
#   위상 정렬이므로 O(N + K)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(100000)
    # ######## INPUT AREA BEGIN ##########

    TC = int(input().strip())
    answer = []

    for _ in range(TC):
        N, K = map(int, input().split())
        buildTimes = [0] + list(map(int, input().split()))
        requisites = [[] for _ in range(N+1)]
        resultDp = [0] * (N+1)
        indegree = [0] * (N+1)

        for _ in range(K):
            before, after = map(int, input().split())
            requisites[before].append(after)
            indegree[after] += 1

        queue = []
        
        for building in range(1, N+1):
            if indegree[building] == 0:
                resultDp[building] = buildTimes[building]
                queue.append(building)

        goal = int(input().strip())
        
        for currBuilding in queue:
            for nextBuilding in requisites[currBuilding]:
                resultDp[nextBuilding] = max(resultDp[nextBuilding], resultDp[currBuilding] + buildTimes[nextBuilding])
                indegree[nextBuilding] -= 1
                if indegree[nextBuilding] == 0:
                    queue.append(nextBuilding)
        
        answer.append(str(resultDp[goal]))
    
    return '\n'.join(answer)


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