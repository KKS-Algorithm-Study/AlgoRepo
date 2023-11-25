#include <iostream>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int cal_d(int x) {

    return (x * 2) % 10000;
}

int cal_s(int x) {

    return x == 0 ? 9999 : x - 1;
}

int cal_l(int x) {

    if (x >= 1000) {
        int tmp = x / 1000;
        x *= 10;
        x -= tmp * 10000;
        x += tmp;
        return x;
    }
    return x * 10;
}

int cal_r(int x) {

    int tmp = x % 10;
    x /= 10;
    x += tmp * 1000;
    return x;
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    int t;

    cin >> t;

    while (t--) {
        int a, b;
        int vis[10001];

        memset(vis, 0, sizeof(vis));
        cin >> a >> b;

        queue<pair<int, string>> q;

        q.push({a, ""});
        vis[a] = 1;
        
        while (!q.empty()) {
            int x; string str;
            tie(x, str) = q.front(); q.pop();

            if (x == b) {
                cout << str << endl; break;
            }
            
            int dx = cal_d(x);
            if (!vis[dx]) {
                vis[dx] = 1;
                q.push({dx, str + 'D'});
            }

            int sx = cal_s(x);
            if (!vis[sx]) {
                vis[sx] = 1;
                q.push({sx, str + 'S'});
            }

            int lx = cal_l(x);
            if (!vis[lx]) {
                vis[lx] = 1;
                q.push({lx, str + 'L'});
            }

            int rx = cal_r(x);
            if (!vis[rx]) {
                vis[rx] = 1;
                q.push({rx, str + 'R'});
            }
        }
    }
    return 0;
}
