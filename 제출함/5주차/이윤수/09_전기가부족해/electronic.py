import sys
from heapq import heappop, heapify

def find_rep(rep_of_city, city):
    if rep_of_city[city] == city:
        return city
    rep_of_city[city] = find_rep(rep_of_city, rep_of_city[city])
    return rep_of_city[city]

def is_same_set(rep_of_city, city1, city2):
    return find_rep(rep_of_city, city1) == find_rep(rep_of_city, city2)

def union_set(rep_of_city, city1, city2):
    rep_of_city[find_rep(rep_of_city,city2)] = find_rep(rep_of_city, city1)

input = sys.stdin.readline
    
num_cities, num_cables, _ = map(int, input().split())
rep_of_city = [i for i in range(num_cities+1)]
#가상의 노드 0과 발전소 있는 도시들 연결
pq = list(map(lambda x: (0,0,x), map(int, input().split())))
pq += list(tuple(reversed(tuple(map(int, input().split())))) for _ in range(num_cables))
heapify(pq)

total_cost = 0
while pq:
    cur_cost, city1, city2 = heappop(pq)
    if is_same_set(rep_of_city, city1, city2):
        continue
    union_set(rep_of_city, city1, city2)
    total_cost += cur_cost
        
print(total_cost)