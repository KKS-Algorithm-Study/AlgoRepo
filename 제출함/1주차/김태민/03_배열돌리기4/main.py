# CP template Version 1.006
#import io
import os
import sys
#import string
#from functools import cmp_to_key, reduce, partial
#import itertools
from itertools import permutations
#import collections
#from collections import deque
#from collections import Counter, defaultdict as dd
#import math
#from math import log, log2, ceil, floor, gcd, sqrt
#from heapq import heappush, heappop
#import bisect
#from bisect import insort_left as il
DEBUG = False

def main(f=None):
    init(f)
    #sys.setrecursionlimit(10**4)
    # ######## INPUT AREA BEGIN ##########            

    # 보드 크기(N, M), 쿼리 갯수(K) 받기
    N, M, K = map(int, input().split())

    # 보드를 1차원 배열로 받기                               만약 원래 보드가 이렇다면, 1차원 배열은 이렇게 생김                                                
    boardOriginal = []                                          # [[1, 2, 3],           [1, 2, 3,
    for y in range(N):                                          #  [4, 5, 6],            4, 5, 6,
        boardOriginal.extend(list(map(int, input().split())))   #  [7, 8, 9]]            7, 8, 9]

    # 쿼리 받기
    queries = [list(map(int, input().split())) for _ in range(K)]

    # 정답 초기화
    answer = int(1e9)

    # 쿼리 진행 순서를 순열로 뽑고
    for possibleOrders in permutations(range(K)):
        board = boardOriginal[:]

        # 해당 쿼리 진행 순서대로 쿼리를 실행
        for order in possibleOrders:
            row, col, sizes = queries[order]
            center = row*M + col
            # 각 사각형의 대각선 끝을 잡고, 파이썬의 리스트 스왑을 이용해 한칸씩 돌림
            for siz in range(1, sizes+1):
                topLft = center - (siz*M) - siz
                topRgt = center - (siz*M) + siz
                botRgt = center + (siz*M) + siz
                botLft = center + (siz*M) - siz
                board[topLft+1:topRgt+1], board[topRgt+M:botRgt+M:M], board[botRgt-1:botLft-1:-1], board[botLft-M:topRgt-M:-M] =\
                board[topLft:topRgt],     board[topRgt:botRgt:M],     board[botRgt:botLft:-1],     board[botLft:topRgt:-M] 
        
        # 정답값 갱신
        for y in range(1+M, (N+1)*M, M):
            answer = min(answer, sum(board[y:y+M]))

    return answer
                    
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