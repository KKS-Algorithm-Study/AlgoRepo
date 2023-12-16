#include <iostream>
#include <vector>

using namespace std;

int cost[10001];
int group[10001];

int findFun(int x) {
    if (group[x] == x) return x;
    else group[x] = findFun(group[x]);
    return group[x];
}

void unionFun(int a, int b) {
    a = findFun(a);
    b = findFun(b);

    if (a < b) swap(a, b);
    group[a] = b;
}

int main() {
    int N, M, k;
    cin >> N >> M >> k;

    for (int i = 1; i <= N; i++) {
        int a;
        cin >> a;
        cost[i] = a;
        group[i] = i;
    }

    for (int i = 0; i < M; i++) {
        int v, w;
        cin >> v >> w;

        unionFun(v, w);
    }

    int sum = 0;
    for (int i = 1; i <= N; i++) {
        int min = 10001;
        for (int j = 1; j <= N; j++) {
            if (findFun(j) == i) {
                if (cost[j] < min) min = cost[j];
            }
        }
        if (min != 10001) sum += min;
    }
    if (sum > k) cout << "Oh no";
    else cout << sum;
}
