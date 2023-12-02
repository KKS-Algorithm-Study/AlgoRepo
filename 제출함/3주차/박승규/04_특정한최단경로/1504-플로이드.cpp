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
ll d[801][801];

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> e;

    for (int i = 1; i<=n;i++) {
        fill(d[i], d[i] + n + 1, INF);
        d[i][i] = 0;
    }

    while (e--) {
        int u, v, c;

        cin >> u >> v >> c;

        d[u][v] = d[v][u] = c;
    }
    cin >> v1 >> v2;

    for (int k = 1; k<=n;k++) {
        for (int i = 1 ; i<=n;i++) {
            for (int j = 1; j<=n;j++) {
                if (d[i][j] > d[i][k] + d[k][j])
                    d[i][j] = d[i][k] + d[k][j];
            }
        }
    }

    ll t1 = d[1][v1] + d[v1][v2] + d[v2][n];
    ll t2 = d[1][v2] + d[v2][v1] + d[v1][n];

    ll ans = min(t1, t2);
    if (ans >= INF) ans = -1;
    cout << ans;
    return 0;
}
