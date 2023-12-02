#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

const int INF = 1e9;
int n, e, v1, v2;
vector<pair<int, int>> g[801];
int d[801];

ll dik(int st, int en) {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;

    fill(d, d + n + 1, INF);
    d[st] = 0;
    pq.push({0, st});

    while (!pq.empty()) {
        int c, v, nc, nv;
        tie(c, v) = pq.top(); pq.pop();
        if (d[v] != c) continue;
        for (auto e : g[v]) {
            tie(nc, nv) = e;
            nc += c;
            if (d[nv] > nc) {
                d[nv] = nc;
                pq.push({d[nv], nv});
            }
        }
    }
    return d[en];
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> e;

    while (e--) {
        int u, v, c;

        cin >> u >> v >> c;
        g[u].push_back({c, v});
        g[v].push_back({c, u});
    }

    cin >> v1 >> v2;

    ll t1 = dik(1, v1) + dik(v1, v2) + dik(v2, n);
    ll t2 = dik(1, v2) + dik(v2, v1) + dik(v1, n);
    ll ans = min(t1, t2);
    if (ans >= INF) ans = -1;
    cout << ans;
    return 0;
}
