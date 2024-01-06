# CP template Version 1.006
import os
import sys
#import string
#from functools import cmp_to_key, reduce, partial
#import itertools
#from itertools import combinations
#import collections
# from collections import deque
#from collections import Counter, defaultdict as dd
#import math
#from math import log, log2, ceil, floor, gcd, sqrt
from heapq import heappush, heappop
#import bisect
# from bisect import bisect_left as bl
DEBUG = False

# 문제 접근법:
#    문제 푸는 순서를 정해야 하는데, 다음 조건을 만족해야 한다:
#    1. 어떤 문제보다 먼저 풀어서 좋은 문제가 있다면 그 문제를 먼저 풀어야 한다
#    2. 낮은 번호의 문제가 쉬운데, 가능하면 쉬운 문제부터 풀어야 한다
#    이 때, 문제 풀이 순서를 출력해야 한다.
#
#    순서가 정해져 있으므로 위상 정렬 문제다.
#    하지만 일반적인 위상 정렬은 "큐에 들어온 순서대로" 처리하는데,
#    이 문제는 낮은 번호의 원소부터 처리해야 한다.
#    간단하게 큐를 heap 자료구조로 만드는 것으로 해결 가능하다. 

# 시간 복잡도:
#    위상정렬이므로 O(N+M)인데,
#    노드 탐색마다 힙 자료구조에 넣는 작업이 추가되어있으므로,
#    최종 시간 복잡도는 O(NlogN)이 된다.

def main(f=None):
    init(f)
    # sys.setrecursionlimit(300000)
    # ######## INPUT AREA BEGIN ##########

    N, M = map(int, input().split())

    indegree = [0] * (N+1)
    problemTree = [[] for _ in range(N+1)]
    for _ in range(M):
        a, b = map(int, input().split())
        problemTree[a].append(b)
        indegree[b] += 1
    
    problemQueue = []
    for number in range(1, N+1):
        if not indegree[number]:
            heappush(problemQueue, number)
    
    result = []
    while problemQueue:
        problem = heappop(problemQueue)
        result.append(str(problem))
        for nextProblem in problemTree[problem]:
            indegree[nextProblem] -= 1
            if indegree[nextProblem] == 0:
                heappush(problemQueue, nextProblem)

    return ' '.join(result)

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