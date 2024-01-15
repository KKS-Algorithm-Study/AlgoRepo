from bisect import bisect_left

n = int(input())

electric_wires = [0] * (501)
wire_idx = {}

for sour, des in [map(int, input().split()) for _ in range(n)]:
    electric_wires[sour] = des
    wire_idx[sour] = True

dp = []

for idx in sorted(wire_idx.keys()):
    electric_wire = electric_wires[idx]

    if not dp or dp[-1] < electric_wire:
        dp.append(electric_wire)
        continue

    change_idx = bisect_left(dp, electric_wire)
    dp[change_idx] = electric_wire

print(n - len(dp))
