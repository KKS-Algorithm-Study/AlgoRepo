# CP template Version 1.006
#import io
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
#from bisect import bisect_left as bl
DEBUG = False

# 문제 접근 방법:
#   10 x 10 크기의 판에 종이로 덮어야 하는 부분이 1로 마킹되어있고,
#   종이는 1x1 ~ 5x5 크기로 각각 5개씩 있을 때,
#   덮어야 하는 부분만 덮는 경우 중 최소로 사용한 종이 장 수를 출력하는 문제
#   
#   실제로 종이를 놔보면서 백트래킹으로 풀되,
#   종이를 다 쓴 경우, 덮을 필요가 없는 칸이 있는 경우 등을 가지치기 하며 푸는 문제다.
#
#   비트마스킹을 사용하면 여러 방식으로 연산횟수를 줄일 수 있다.
#   예를 들어, "종이가 덮을 칸 중 필요없는 칸"을 계산할 때 이중 반복문을 쓰면 최대 25번이지만, 비트 마스킹을 쓰면 최대 5번으로 끝난다. 
#
# 주의할 점:
#   "무조건 큰 종이로 덮는게 최선이다" 하는 그리디 방식처럼 풀면 안되는데,
#   6 x 6 크기로 덮어야 하는 경우,
#   그리디 방식으로 풀면 크기 4 종이 1개, 크기 2 종이 5개로 총 6장으로 풀리는데,
#   정답은 크기 3 종이 4개, 총 4장이기 때문이다.
#
# 시간 복잡도:
#   백트래킹으로, O(N!)이다

def main(f=None):
    init(f)
    # ######## INPUT AREA BEGIN ##########

    def findNextPoint(board, pointer):
        while pointer != 100 and ~board[(pointer//10)] & (1 << (pointer%10)):
            pointer += 1
        return pointer

    
    def coverBoard(pointer, paperLeft, result):

        pointer = findNextPoint(board, pointer)
        if pointer == BOARD_SIZE ** 2:
            return PAPER_USE_COUNT * PAPER_SIZE_COUNT - sum(paperLeft[1:])
        
        row, col = pointer//10, pointer%10
        for paperSize in range(PAPER_SIZE_COUNT, 0, -1):
            paperBit = ((1 << paperSize) - 1) << col
            # 해당 크기의 종이를 전부 쓴 경우
            if paperLeft[paperSize] == 0:
                continue
            # 해당 크기의 종이를 쓰면 격자를 넘어가는 경우
            if row + paperSize > BOARD_SIZE or col + paperSize > BOARD_SIZE:
                continue
            # 덮을 공간에 덮을 필요 없는 칸이 있는 경우
            if sum([~board[row + nxtRow] & paperBit for nxtRow in range(paperSize)]) != 0:
                continue

            board[row : row + paperSize] = [board[coverRow] & ~paperBit for coverRow in range(row, row + paperSize)]
            paperLeft[paperSize] -= 1
            result = min(result, coverBoard(pointer + paperSize, paperLeft, result))
            board[row : row + paperSize] = [board[coverRow] | paperBit for coverRow in range(row, row + paperSize)]
            paperLeft[paperSize] += 1

        return result
        
    BOARD_SIZE = 10
    NEED_COVER = '1'
    PAPER_USE_COUNT = 5
    PAPER_SIZE_COUNT = 5
    INF = 26

    board = [0] * BOARD_SIZE
    for row in range(BOARD_SIZE):
        for col, square in enumerate(input().split()):
            if square == NEED_COVER:
                board[row] |= 1 << col

    paperLeft = [PAPER_USE_COUNT] * (PAPER_SIZE_COUNT + 1)
    answer = coverBoard(0, paperLeft, INF)
    return answer if answer != INF else -1

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
    input = sys.stdin.readline #io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
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