#include <iostream>

using namespace std;

int P[100001];
long long cnt_P[100001];
long long cost[100001][3];

long long minn(long long a, long long b) {
    if (a < b) return a;
    else return b;
}

int main() {
    int N, M;
    cin >> N >> M;
    for (int i = 0; i < M; i++) cin >> P[i];

    for (int i = 0; i < M - 1; i++) {
        int start = P[i];
        int end = P[i + 1];
        if (start > end) swap(start, end);
        cnt_P[start - 1] += 1;
        cnt_P[end - 1] -= 1;
    }
    for (int i = 0; i < N - 1; i++) {
        for (int j = 0; j < 3; j++) {
            cin >> cost[i][j];
        }
    }
    long long total_cost = 0, cnt = 0;
    for (int i = 0; i < N - 1; i++) {
        cnt += cnt_P[i];
        long long money1 = cost[i][0] * cnt;
        long long money2 = cost[i][2] + cost[i][1] * cnt;
        total_cost += minn(money1, money2);
    }

    cout << total_cost;
}
