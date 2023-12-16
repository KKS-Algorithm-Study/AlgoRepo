#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int group[1001];
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

    for (int i = 0; i <= N; i++) group[i] = i;

    for (int i = 0; i <= M; i++) {
        int A, B, C;
        cin >> A >> B >> C;
        vec.push_back({C, {A, B}});
    }
    sort(vec.begin() + 1, vec.end());

    int maxRes = 0;
    for (int i = 0; i <= M; i++) {
        if (findFun(vec[i].second.first) != findFun(vec[i].second.second)) {
            unionFun(vec[i].second.first, vec[i].second.second);
            if (vec[i].first == 0) maxRes += 1;
        }
    }
    maxRes = maxRes * maxRes;

    for (int i = 0; i <= N; i++) group[i] = i;
    sort(vec.begin() + 1, vec.end(), greater<>());

    int minRes = 0;
    for (int i = 0; i <= M; i++) {
        if (findFun(vec[i].second.first) != findFun(vec[i].second.second)) {
            unionFun(vec[i].second.first, vec[i].second.second);
            if (vec[i].first == 0) minRes += 1;
        }
    }
    minRes = minRes * minRes;
    cout << maxRes - minRes;
}
