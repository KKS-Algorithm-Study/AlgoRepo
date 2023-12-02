#include <iostream>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int n, m;
int d[101][101];
int INF = 1e9;

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> m;

    for (int i = 1; i<=n;i++) {
        fill(d[i], d[i] + n + 1, INF);
        d[i][i] = 0;
    }

    for (int i = 0; i< m;i++) {
        int u, v, c;
        cin >> u >> v >> c;

        d[u][v] = min(d[u][v], c);
    }

    for (int k = 1; k <=n;k++) 
        for (int i = 1; i <=n;i++) 
            for (int j = 1; j<=n;j++) 
                if (d[i][j] > d[i][k] + d[k][j])
                    d[i][j] = d[i][k] + d[k][j];
    
    for (int i = 1; i<=n;i++) {
        for (int j = 1; j<=n;j++) {
            if (d[i][j] == INF) cout << 0 << " ";
            else cout << d[i][j] << " ";
        }
        cout << endl;
    }
    return 0;
}
