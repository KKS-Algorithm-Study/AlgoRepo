#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int group[1001];
char mw[1001];
vector<pair<int, pair<int, int>>> vec;

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
    int N, M;
    cin >> N >> M;
    for (int i = 1; i <= N; i++) {
        char c;
        cin >> c;
        group[i] = i;
        mw[i] = c;
    }

    for (int i = 0; i < M; i++) {
        int u, v, d;
        cin >> u >> v >> d;
        vec.push_back({d, {u, v}});
    }
    sort(vec.begin(), vec.end());

    int res = 0;
    for (int i = 0; i < M; i++) {
        if (mw[vec[i].second.first] != mw[vec[i].second.second]) {
            if (findFun(vec[i].second.first) != findFun(vec[i].second.second)) {
                unionFun(vec[i].second.first, vec[i].second.second);
                res += vec[i].first;
            }
        }
    }

    for (int i = 1; i <= N; i++) {
        if (findFun(i) != 1) {
            cout << "-1";
            return 0;
        }
    }
    cout << res;
}
