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
#   N개의 추와 M개의 구슬이 있을 때, 
#   N개의 추를 적절하게 양팔저울에 올려 각 구슬의 무게를 측정할 수 있는지 출력하는 문제
#
#   완전 탐색이라면, 각 추마다 다음의 경우를 계산해줘야 한다
#       1. 추를 안놓는다
#       2. 추를 왼쪽에 놓는다
#       3. 추를 오른쪽에 놓는다
#   이러면 각 경우마다 3갈래로 나뉘므로, 추의 최대 갯수인 30 만큼 3을 곱한 (== 3 ** 30) 연산 횟수를 갖는다
#   즉, 완전 탐색은 불가능하다
#
#   이 문제는 배낭문제와 같이 DP로 접근할 수 있다
#   가로축은 구슬 무게, 세로축은 이용한 추의 갯수라고 했을 때,
#   "첫 번째 추부터 {row} 개 만큼의 추를 이용해 {col} 무게의 구슬을 잴 수 있는지"를 boolean으로 넣어주면 된다
#   각 계산은 
#       이번 추를 사용하지 않았을 때 잴 수 있었던 구슬의 무게들을 기준으로
#           추를 놓지 않아, 기존에 잴 수 있었던 무게를 그대로 재는 경우
#           추를 왼쪽에 놓아, 기존에 잴 수 있었던 무게에서 추의 무게를 뺀 경우
#           추를 오른쪽에 놓아, 기존에 잴 수 있었던 무게에서 추의 무게를 더한 경우
#   이 세 경우를 계산해 다음 dp 배열을 갱신시켜주면 된다
#
#   주의할 점은, 작은 추 -> 큰 추 순으로 탐방하는 경우, 작은 추가 오른쪽, 큰 추가 왼쪽에 놓이며 작은 추에서 큰 추를 빼 음수가 나오는 경우가 생기는데,
#   양팔 저울은 양쪽을 바꾸어 놓아도 되니, 큰 추에서 작은 추를 빼는 경우로 바꾸는, 즉 절댓값을 취해 그 값을 넣어야 음수 무게를 제외시킬 수 있다 
#   
#   가로축의 길이는 잴 수 있는 구슬의 최대 무게이므로, 추를 모두 더한 값이 되어야 한다
#
#   여기까지만 해도 추의 갯수 * 잴 수 있는 구슬의 최대 무게 == 30 * 30 * 500 으로 정답이 나온다
#   하지만 한걸음 더 나아가서 "이번 추를 사용하지 않았을 때 잴 수 있었던 구슬의 무게들"을 
#   boolean[] 배열이 아닌, HashSet<Integer> 으로 관리해, 중복 체크 및 false 값은 탐색 제외할 수 있어서
#   훨씬 빠르게 탐색할 수 있다

# 시간 복잡도:
#   2차원 DP 배열이든, 해시셋이든, 최대 500g의 추 30개로 잴 수 있는 구슬의 최대 무게는 15000 이고, 이를 추의 갯수 30번만큼 반복하게 된다
#   따라서 추의 갯수 N에 무게 M이라고 하면, 시간복잡도는 O(N**2 * M)이다

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########
    
    INF = 40001
    weightCount = int(input().strip())
    weights = list(map(int, input().split()))
    queryCount = int(input().strip())
    queries = list(map(int, input().split()))

    weighableMarbles = {0}

    for weight in weights[::-1]:

        
        for marble in weighableMarbles.copy():
            # 이번 추를 왼쪽에 올리는 경우
            weighableMarbles.add(abs(marble - weight))
            # 이번 추를 오른쪽에 올리는 경우
            weighableMarbles.add(marble + weight)

    answer = []
    for marble in queries:
        if marble in weighableMarbles:
            answer.append("Y")
        else:
            answer.append("N")

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