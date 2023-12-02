#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

const int INF = 1e9;
int v, e, st;
int d[20001];
vector<pair<int, int>> g[20001];

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> v >> e >> st;

    fill(d, d + v + 1, INF);
    while (e--) {
        int u, v, w;

        cin >> u >> v >> w;
        g[u].push_back({w, v});
    }

    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    d[st] = 0;
    pq.push({0, st});

    while (!pq.empty()) {
        int c, v, nc, nv;
        tie(c, v) = pq.top(); pq.pop();

        if (d[v] != c) continue;
        for (auto e : g[v]) {
            tie(nc, nv) = e;
            if (d[v] + nc >= d[nv]) continue;
            d[nv] = d[v] + nc;
            pq.push({d[nv], nv});
        }
    }

    for (int i = 1; i<=v;i++) {
        if (d[i] == INF) cout << "INF" << endl;
        else cout << d[i] <<endl;
    }
    return 0;
}
