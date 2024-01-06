# CP template Version 1.006
import os
import sys
#import string
#from bshpctools import cmp_to_key, reduce, partial
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
#   체스 보드에 피스를 놓을 수 있는 칸과 놓을 수 없는 칸이 주어질 때,
#   비숍을 서로 공격하지 않도록 하며 놓을 수 있는 최대 갯수를 출력하는 문제
#   
#   피스를 놓을 수 있는 곳에 비숍을 놓으며 마지막 칸에 비숍을 놨을 때의 갯수 중 최대값을 가져오면 된다.
#   
#   백트래킹으로 접근할 수 있는데, 놓을 수 있는 곳의 왼쪽 대각선과 오른쪽 대각선을 체크해서 놓을 수 없는 곳은 가지치기하며 진행한다.
#   대각선 줄의 갯수는 {2 * N - 1}개인데, 왼쪽 대각선은 {x + y}로, 오른쪽 대각선은 {x - y + N}으로 인덱스를 만들 수 있다. 
#   
#   체스 보드를 생각했을 때, 흰 칸에 놓인 비숍은 흰 칸만, 검은 칸에 놓은 비숍은 검은 칸만 공격 가능하므로,
#   흰 칸과 검은 칸을 나눠 계산하면, 최대 2 ** 100 이었던 연산 횟수가 2 ** 50 두번으로 줄어들어 시간을 아낄 수 있다.
#
#   추가적으로, 대각선 체크 배열은 놓였는지 안놓였는지 체크하는 boolean 배열이 들어가므로, 비트 마스킹을 사용하면 더 효율적으로 만들 수 있다.
#   
def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########
    
    def placeBishop(unoccupiedSpaces, index, count, rightDiagBit, leftDiagBit, maxCount):
        if index == len(unoccupiedSpaces):
            return count

        x, y = unoccupiedSpaces[index]
        rightDiagRow = x - y + N
        leftDiagRow = x + y
        if rightDiagBit & (1 << rightDiagRow) == 0 and leftDiagBit & (1 << leftDiagRow) == 0 :
            maxCount = max(maxCount, placeBishop(unoccupiedSpaces, index + 1, count + 1, rightDiagBit | (1 << rightDiagRow), leftDiagBit | (1 << leftDiagRow), maxCount))

        maxCount = max(maxCount, placeBishop(unoccupiedSpaces, index + 1, count, rightDiagBit, leftDiagBit, maxCount))

        return maxCount

    BLACK = 0
    WHITE = 1
    N = int(input().strip())

    blackSquares = []
    whiteSquares = []
    for row in range(N):
        isPlaceable = list(map(int, input().split()))
        for col in range(N):
            if not isPlaceable[col]:
                continue
            isSquareWhite = (row % 2 ^ col % 2) == WHITE
            if isSquareWhite:
                whiteSquares.append((row, col))
            else:
                blackSquares.append((row, col))
    whiteBishopCount = placeBishop(whiteSquares, 0, 0, 0, 0, 0)
    blackBishopCount = placeBishop(blackSquares, 0, 0, 0, 0, 0)

    return whiteBishopCount + blackBishopCount

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