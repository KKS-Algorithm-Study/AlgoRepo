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
#from heapq import heappush, heappop, heapreplace
#import bisect
#from bisect import bisect_left as bl, bisect_right as br
DEBUG = False

# 문제 접근 방법:
#   입력 받은 문자열을 다르게 배열해 나올 수 있는 모든 경우를 사전 순서대로, 중복 없이 출력하는 문제
#
#   중복 없이 나열해야하기 때문에, 스왑 방식의 순열 추출은 불가능하다.
#   
#   알파벳 사용 가능 횟수를 저장해놓고, "a"부터 먼저 사용해 단어를 재귀 방식으로 만들어나가면 된다.
#
# 시간 복잡도:
#   단어 갯수가 N, 단어 길이가 K라고 하면
#   원래의 백트래킹이라면 O(N * K!) 이겠지만, 
#   애너그램 수가 100,000 이하인 단어로 제한되어 있으므로, 
#   O(N)이 된다.

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    def anagramize(depthCount, pattern, letterUseCount, result):
        if not depthCount:
            result.append(pattern)
            return
        
        for letterIndex in range(26):
            if not letterUseCount[letterIndex]:
                continue
            letterUseCount[letterIndex] -= 1
            anagramize(depthCount - 1, pattern + chr(letterIndex + baseUnicodeNumber), letterUseCount, result)
            letterUseCount[letterIndex] += 1
    
    TC = int(input().strip())
    answer = []
    baseUnicodeNumber = ord("a")
    for _ in range(TC):
        word = input().strip()
        counter = [0] * 26
        for letter in word:
            index = ord(letter) - baseUnicodeNumber
            counter[index] += 1
        
        anagramize(len(word), "", counter, answer)
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
    input = sys.stdin.readline  # by default
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