#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

int group[100001];
vector<pair<tuple<int, int, int>, int>> v;

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
    ios_base::sync_with_stdio(false);
    cin.tie(NULL); 
    cout.tie(NULL);
    
    int N, Q;
    cin >> N >> Q;
    for (int i = 1; i <= N; i++) {
        group[i] = i;
    }

    for (int i = 1; i <= N; i++) {
        int x1, x2, y;
        cin >> x1 >> x2 >> y;
        v.push_back({{x1, x2, y}, i});
    }
    sort(v.begin(), v.end());

    for (int i = 1; i < N; i++) {
        if (get<0>(v[i].first) <= get<1>(v[i - 1].first) && get<0>(v[i].first) >= get<0>(v[i - 1].first)) {
            unionFun(v[i].second, v[i - 1].second);
            get<0>(v[i].first) = get<0>(v[i - 1].first);
            if (get<1>(v[i].first) < get<1>(v[i - 1].first)) get<1>(v[i].first) = get<1>(v[i - 1].first);
        }
    }

    for (int i = 0; i < Q; i++) {
        int a, b;
        cin >> a >> b;
        if (findFun(a) == findFun(b)) cout << "1\n";
        else cout << "0\n";
    }
}
