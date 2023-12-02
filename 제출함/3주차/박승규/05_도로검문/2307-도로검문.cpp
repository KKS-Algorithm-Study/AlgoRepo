#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

const int INF = 1e9;
int n, m;
int d[1001];
int pre[1001];
vector<pair<int, int>> g[1001];

int dik(int st, int en) {
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;

    fill(d, d + n + 1, INF);
    d[1] = 0;
    pq.push({0, 1});

    while (!pq.empty()) {
        int c, v, nc, nv;
        tie(c, v) = pq.top(); pq.pop();
        if (d[v] != c) continue; 
        for (auto e : g[v]) {
            tie(nc, nv) = e;
            if (v == st && nv == en) continue;
            if (nv == st && v == en) continue;
            nc += c;
            if (d[nv] > nc) {
                d[nv] = nc;
                pq.push({d[nv], nv});
            }
        }
    }
    return d[n];
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> m;

    fill(d, d + n + 1, INF);
    while (m--) {
        int u, v, c;

        cin >> u >> v >> c;
        g[u].push_back({c, v});
        g[v].push_back({c, u});
    }

    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;

    d[1] = 0;
    pq.push({0, 1});

    while (!pq.empty()) {
        int c, v, nc, nv;
        tie(c, v) = pq.top(); pq.pop();
        if (d[v] != c) continue;
        for (auto e : g[v]) {
            tie(nc, nv) = e;
            nc += c;
            if (d[nv] > nc) {
                d[nv] = nc;
                pre[nv] = v;
                pq.push({d[nv], nv});
            }
        }
    }

    if (d[n] == INF) {
        cout << -1; return 0;
    }
    int cur = n;
    vector<int> path;
    while (cur != 1) {
        path.push_back(cur);
        cur = pre[cur];
    }
    path.push_back(1);
    reverse(path.begin(), path.end());

    int t = d[n];
    vector<int> v;
    for (int i = 0;i < path.size()-1;i++) {
        v.push_back(dik(path[i], path[i+1]));
    }
    int ans = *max_element(v.begin(), v.end());
    if (ans == INF) ans = -1;
    else ans -= t;
    cout << ans;
    return 0;   
}
