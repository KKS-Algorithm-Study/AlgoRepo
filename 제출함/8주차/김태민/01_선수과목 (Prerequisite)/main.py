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
#     선수 과목이 정해져 있는 과목은 다음 학기에 들어야할 때,
#     모든 과목을 수강하기 위해서는 몇 학기가 필요한지 계산하는 문제
#   
#     전형적인 위상정렬 문제로
#     선수 과목이 없는 과목들을 먼저 처리하고,
#     선수 과목이 모두 처리된 과목은 {시간 + 1} 해서 다음 큐에 넣어주는 방식으로 풀 수 있다.
#
# 시간 복잡도:
#     위상 정렬 문제는 BFS 처럼 모든 노드를 한 번씩 순회하고, 모든 간선도 한 번씩 탐색하므로,
#     노드 갯수 N, 간선 갯수 M으로
#     O(N + M)이 된다.


def main(f=None):
    init(f)
    # sys.setrecursionlimit(100000)
    # ######## INPUT AREA BEGIN ##########

    N, M = map(int, input().split())

    prerequisites = [[] for _ in range(N + 1)]
    indegree = [0] * (N + 1)
    answer = ['?'] * (N + 1) 

    for _ in range(M):
        before, after = map(int, input().split())
        prerequisites[before].append(after)
        indegree[after] += 1
    
    cque = []
    for index in range(1, N+1):
        if indegree[index] == 0:
            cque.append(index)
    
    for semester in range(1, N+1):
        nque = []
        for curr_class in cque:
            answer[curr_class] = str(semester)
            for next_class in prerequisites[curr_class]:
                indegree[next_class] -= 1
                if not indegree[next_class]:
                    nque.append(next_class)
        
        if not nque:
            break
        cque = nque
    
    return '\n'.join(answer[1:])

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