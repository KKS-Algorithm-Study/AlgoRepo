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
#   사다리 타기 게임을 조작해 각 줄이 시작했던 줄로 돌아오도록 만들기 위해 필요한 가로줄 최소 갯수를 세는 문제
#   단, 가로줄 최대 갯수는 3개다. (출력에 조건 존재)
#   
#   가로줄을 그릴 수 있는 위치가 N-1 * H개에, 그 위치에 전부 놓아보는 조합은 엄청 많겠지만
#   최대 갯수가 3으로 고정되어있으므로, 270 * 269 * 268 = 약 2천만 이라 완전 탐색으로 가능하다.
#   
#   0~3개의 가로줄을 재귀 방식으로 놓아보고, 다 놓았을 때 위치가 바뀐 게 있으면 다시 탐색, 없으면 그대로 선의 갯수를 출력하면 된다.
#
#   시간 복잡도:
#   위에서 말했듯 완전 탐색이므로, O((N*H)C3) 이다.
#   

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########
    
    def countWrongLines(crossDirections, lineCount, rowCount):
        wrongLineCount = 0
        for originalLine in range(lineCount - 1):
            crossedLine = originalLine
            for row in range(rowCount):
                crossedLine += crossDirections[row][crossedLine]
            if originalLine != crossedLine:
                wrongLineCount += 1
        return wrongLineCount
    
    def isSolvableWithLineCount(crossDirections, lineCount):
        if lineCount == 0:
            wrongLineCount = countWrongLines(crossDirections, len(crossDirections[0]), len(crossDirections))
            return wrongLineCount == 0
        
        for row in range(H):
            for col in range(N-1):
                if crossDirections[row][col] == crossDirections[row][col+1]:
                    crossDirections[row][col], crossDirections[row][col+1] = CROSS_RIGHT, CROSS_LEFT
                    if isSolvableWithLineCount(crossDirections, lineCount - 1):
                        return True
                    crossDirections[row][col], crossDirections[row][col+1] = 0, 0
        return False

    N, M, H = map(int, input().split())
    DRAWABLE_LINE_COUNT = 3
    CROSS_RIGHT = 1
    CROSS_LEFT = -1
    crossDirections = [[0] * N for _ in range(H)]

    for _ in range(M):
        row, lineNumber = map(int, input().split())
        row -= 1
        crossDirections[row][lineNumber-1], crossDirections[row][lineNumber] = CROSS_RIGHT, CROSS_LEFT
    
    if countWrongLines(crossDirections, N, H) > DRAWABLE_LINE_COUNT * 2:
        return -1

    for lineCount in range(4):
        if isSolvableWithLineCount(crossDirections, lineCount):
            return lineCount
    else:
        return -1


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