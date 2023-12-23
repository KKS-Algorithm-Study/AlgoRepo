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
#from math import factorial
#from heapq import heappush, heappop
#import bisect
#from bisect import bisect_left as bl, bisect_right as br
DEBUG = False

# 문제 접근 방법:
#   N개의 'a'와 M개의 'z'로 만든 단어들의 사전이 있을 때,
#   해당 사전에서 K번째 단어는 무엇인지 출력하는 문제
#   
#   N과 M 모두 최대 100이고, 이를 이용한 중복 없는 순열의 갯수는 
#   빈 자리 200개 중에서 a가 들어갈 100 개의 자리를 뽑는 조합의 갯수와 동일하며,
#   이는 200! / (100! * 100!)개 (== 90548514656103281165404177077484163874504589675413336841320 개) 이므로, 
#   완전 탐색은 불가능하다는 걸 알 수 있다
#   
#   사전에는 수학적 규칙이 있는데, 이는 a의 갯수와 z의 갯수를 다르게 설정한 사전들을 직접 만들어보면 알 수 있다
#   예를 들어, a가 2개 z가 2개인 사전을 만들어보면
#     a가 1개 z가 2개인 사전의 단어들 앞에 a를 붙여준 것과
#     a가 2개 z가 1개인 사전의 단어들 앞에 z를 붙여준 것
#   두 사전의 합으로 이루어진 것을 볼 수 있다
#   즉, [a가 N개 z가 M개]인 사전이 가지는 단어 갯수는 가로 N, 세로 M 크기의 2차원 누적합 배열로 구할 수 있다
#
#   위에서 만들어진 2차원 누적합 배열을 가지고
#   [a가 N개 z가 M개]인 사전에서 K번째 단어를 찾으려면, 다음과 같은 while 문을 돌리면 된다:
#     만약 K번째 단어가 [a가 N-1개 z가 M개]인 사전의 단어 갯수에 포함된다면
#       단어 앞에 a를 붙이고 [a가 N-1개 z가 M개]인 사전에서 K번째 인덱스 단어를 찾고,
#     만약 그렇지 않다면
#       단어 앞에 z를 붙이고 [a가 N개 z가 M-1개]인 사전에서 {K - [a가 N-1개 z가 M개]인 사전의 단어 갯수}번째 인덱스 단어를 찾으면 된다

# 시간 복잡도:
#   누적합을 만드는데 N번만큼 M번을 계산하므로, O(N*M)
#   단어를 찾는데 while 문으로 N과 M을 1씩 줄여나가므로, O(N+M)
#   따라서, 둘 중 더 큰 O(N*M)이다

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    aCount, zCount, wordIndex = map(int, input().split())
    dictionaryRowCounts = [[1] * (zCount+1) for _ in range(aCount+1)]
    answer = ''
    for a in range(1, aCount+1):
        for z in range(1, zCount+1):
            dictionaryRowCounts[a][z] = dictionaryRowCounts[a - 1][z] + dictionaryRowCounts[a][z - 1]

    if dictionaryRowCounts[aCount][zCount] < wordIndex:
        return -1

    while True:
        if aCount == 0 or zCount == 0:
            answer += 'a' * aCount + 'z' * zCount
            break

        if wordIndex <= dictionaryRowCounts[aCount-1][zCount]:
            answer += 'a'
            aCount -= 1
        else:
            wordIndex -= dictionaryRowCounts[aCount-1][zCount]
            answer += 'z'
            zCount -= 1
    
    return answer

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