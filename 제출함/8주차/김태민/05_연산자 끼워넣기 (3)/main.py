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
import math
#from math import log, log2, ceil, floor, gcd, sqrt
#from heapq import heappush, heappop, heapreplace
#import bisect
#from bisect import bisect_left as bl, bisect_right as br
DEBUG = False

# 문제 접근 방법:
#   숫자의 나열과 연산자들의 갯수가 주어질 때,
#   연산자를 숫자 사이에 임의로 끼워넣어 얻을 수 있는 최댓값과 최솟값을 출력하는 문제
#   단, 연산자는 사칙연산 순으로 적용해야 한다
#
#   중복없이 모든 연산자 순열을 탐색한다 해도, 총 갯수는 10개이므로 
#   최악의 경우 10! / (2! * 2! * 3! * 3!) = 약 25000개가 나와 완전 탐색을 할 수 있다.
#
#   사칙연산 순서대로 연산자를 적용한다고 해서 헷갈릴 수 있지만, 괄호가 없으므로
#   숫자를 스택에 쌓는데 곱셈과 나눗셈은 바로 이전 숫자에 적용시켜서
#   마지막에 쌓인 모든 숫자를 더해주면 된다. 
#
#   그마저도 스택은 생략할 수 있는데,
#   어차피 다음 연산자에 영향 받는 것은 스택의 마지막 숫자라는 점을 이용하면 된다.
#   함수에는 스택 대신 합산 결과, 이전 숫자를 매개변수로 넣고,
#   연산자가 덧셈 뺄셈이라면, 이전 숫자는 합산 결과에 더하고, 현재 숫자를 다음 연산의 이전 숫자로 놓고 (뺄셈이라면 -해서 놓는다),
#   연산자가 곱셈 나눗셈이라면, 합산 결과는 놔두고, 이전 숫자에 현재 숫자를 곱하거나 나눠 그대로 이전 숫자로 가져가면 된다.  
#
#   시간 복잡도:
#   모든 순열을 완전 탐색 하므로, O(N!)이다.

def main(f=None):
    init(f)
    # sys.setrecursionlimit(10**9)
    # ######## INPUT AREA BEGIN ##########

    def add(total, prevNumber, number):
        return (total + prevNumber, number)
    
    def subtract(total, prevNumber, number):
        return (total + prevNumber, -number)
    
    def multiply(total, prevNumber, number):
        return (total, prevNumber * number)
    
    def devide(total, prevNumber, number):
        return (total, math.trunc(prevNumber / number))
    
    def insertOperator(numberIndex, operatorCounts, total, prevNumber, numbers, operations, minValue, maxValue):
        if numberIndex == len(numbers):
            total += prevNumber
            minValue = min(minValue, total)
            maxValue = max(maxValue, total)
            return (minValue, maxValue)
        
        currNumber = numbers[numberIndex]
        for operatorIndex in range(4):
            if operatorCounts[operatorIndex] == 0:
                continue

            operation = operations[operatorIndex]
            nextTotal, nextNumber = operation(total, prevNumber, currNumber)

            operatorCounts[operatorIndex] -= 1
            resultMin, resultMax = insertOperator(numberIndex + 1, operatorCounts, nextTotal, nextNumber, numbers, operations, minValue, maxValue)
            operatorCounts[operatorIndex] += 1

            minValue = min(minValue, resultMin)
            maxValue = max(maxValue, resultMax)
        
        return (minValue, maxValue)
    
    INF = int(1e9)
    N = int(input().strip())
    numbers = list(map(int, input().split()))
    operatorCounts = list(map(int, input().split()))
    operations = (add, subtract, multiply, devide)
    minValue, maxValue = insertOperator(1, operatorCounts, 0, numbers[0], numbers, operations, INF, -INF)

    return f'{maxValue}\n{minValue}'

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