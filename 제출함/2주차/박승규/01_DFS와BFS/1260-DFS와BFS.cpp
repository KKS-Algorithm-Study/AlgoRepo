#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int n, m, v;
int vis[1001];
vector<int> g[1001];

void dfs(int v) {
    vis[v] = 1;
    cout << v << " ";
    for (int i = 0; i < g[v].size(); i++) {
        if (vis[g[v][i]]) continue;
        dfs(g[v][i]);
    }
}

void bfs(int v) {
    
    queue<int> q;
    q.push(v);
    vis[v] = 1;

    while (!q.empty()) {
        int nxt = q.front(); q.pop();

        cout << nxt << " ";

        for (int i = 0; i < g[nxt].size();i++) {
            if (vis[g[nxt][i]]) continue;
            vis[g[nxt][i]] = 1;
            q.push(g[nxt][i]);
        }
    }
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> m >> v;

    for (int i = 0; i < m;i++) {
        int u, v;

        cin >> u >> v;
        g[u].push_back(v);
        g[v].push_back(u);
    }

    for (int i = 1; i<=n;i++)
        sort(g[i].begin(), g[i].end());

    dfs(v);
    cout << endl;
    memset(vis, 0, sizeof(vis));
    bfs(v);
    return 0;
}
