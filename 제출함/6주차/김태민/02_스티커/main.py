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
#   스티커가 2*n 으로 붙어 있는데, 하나를 떼면 인접한 스티커를 못쓰게 된다
#   이 스티커들의 점수가 주어질 때, 잘 떼진 스티커들의 점수 합이 최대가 되게 떼면 몇 점이 나오는지 출력하는 문제
#
#   역시 DP 문제로, "윗줄 혹은 아랫줄의 i번째 스티커를 꼭 뗀다고 했을 때 얻을 수 있는 최대 점수의 합" 을 저장하면 된다
#   
#   만약 윗줄의 i번째 스티커를 뗀다면, 윗줄의 i-1 번째 스티커는 못쓰게 되었지만 아랫줄의 i-1번째 스티커는 쓸 수 있다.
#   하지만 아랫줄의 i-1번째 스티커를 쓰는 것보다 i-2번째를 쓰는게 값이 높을 수 있으므로,
#   아랫줄의 i-1과 i-2 스티커 중 더 큰 값을 골라 윗줄의 i번째 스티커와 합해 dp를 갱신시키면 된다.
#   
# 시간 복잡도:
#   테스트 케이스 갯수 T 만큼 for문을 N번 돌게 되므로, O(T * N)

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    caseCount = int(input().strip())
    answer = []

    for _ in range(caseCount):
        stickerCount = int(input())
        upperRow = [0] + list(map(int, input().split()))
        lowerRow = [0] + list(map(int, input().split()))

        for i in range(2, stickerCount + 1):
            upperRow[i] += max(lowerRow[i-1], lowerRow[i-2])
            lowerRow[i] += max(upperRow[i-1], upperRow[i-2])

        answer.append(str(max(upperRow[stickerCount], lowerRow[stickerCount])))
    
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