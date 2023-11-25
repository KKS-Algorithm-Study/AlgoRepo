#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int dx[4] = {-1,0,1,0};
int dy[4] = {0,1,0,-1};
int ddx[8] = {-1,-2,-2,-1,1,2,2,1};
int ddy[8] = {-2,-1,1,2,2,1,-1,-2};

int k, w, h;
int board[201][201];
int vis[32][201][201];

int oob(int x, int y) {
    return x < 0 || x >= h || y < 0 || y >= w;
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> k;
    cin >> w >> h;
    for (int i = 0; i < h; i++) {
        for (int j = 0 ; j < w;j++) {
            cin >> board[i][j];
        }
    }

    queue<tuple<int, int, int>> q;
    memset(vis, -1, sizeof(vis));
    q.push({0, 0, 0});
    vis[0][0][0] = 0;

    while (!q.empty()) {
        int m_cnt, x, y;
        tie(m_cnt, x, y) = q.front(); q.pop();

        if (x == h - 1 && y == w - 1) {
            cout << vis[m_cnt][x][y]; return 0;
        }
        if (m_cnt < k) {
            for (int dir = 0; dir < 8; dir++) {
                int nx = x + ddx[dir];
                int ny = y + ddy[dir];

                if (oob(nx, ny)) continue;
                if (board[nx][ny]) continue;
                if (vis[m_cnt + 1][nx][ny] != -1) continue;
                vis[m_cnt+1][nx][ny] = vis[m_cnt][x][y] + 1;
                q.push({m_cnt+1, nx, ny});
            }
        }
        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (oob(nx, ny)) continue;
            if (board[nx][ny]) continue;
            if (vis[m_cnt][nx][ny] != -1) continue;
            vis[m_cnt][nx][ny] = vis[m_cnt][x][y] + 1;
            q.push({m_cnt, nx, ny});
        }
    }
    cout << -1;
    return 0;
}
