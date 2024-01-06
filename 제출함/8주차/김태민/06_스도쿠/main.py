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
#   몇 칸이 빈 스도쿠를 채워 출력하는 문제
#
#   재귀 방식으로 빈 칸을 순회하며, 실제 숫자를 하나씩 넣어보며
#   모든 칸을 채웠을 때 풀이를 출력하면 된다
#
# 시간 복잡도:
#   보드의 크기가 9로 고정이지만, 이를 N으로 하면
#   최대 빈칸의 갯수는 N**2, 선택지는 N개이므로
#   O(N**(N**2))이다.
#   하지만 가지치기가 들어있으므로, 실제 연산 속도는 그보다 훨씬 빠르다.
#   
def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    board, blanks = [], []
    for row in range(9):
        numbers = list(map(int, input().split()))
        for col in range(9):
            if numbers[col] == 0:
                blanks.append((row, col))
        board.append(numbers)

    def solve(blankIndex):
        if blankIndex == len(blanks):
            return True

        row, col = blanks[blankIndex]
        rowBlockStart, colBlockStart = row // 3 * 3, col // 3 * 3
        for number in range(1, 10):
            for idx in range(9):
                rowBlock, colBlock = (idx // 3) + rowBlockStart, idx % 3 + colBlockStart
                if number == board[row][idx] or number == board[idx][col] or number == board[rowBlock][colBlock]:
                    break
            else:
                board[row][col] = number
                if solve(blankIndex + 1):
                    return True
                board[row][col] = 0
        else:
            return False
    
    return '\n'.join((' '.join(list(map(str, row))) for row in board))

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