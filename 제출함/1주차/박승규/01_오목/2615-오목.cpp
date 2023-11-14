#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int board[20][20];
int vis[8][20][20];
int dx[4] = {-1,0,1,1};
int dy[4] = {1,1,1,0};
const int N = 19;

int oob(int x, int y) {
    return x <= 0 || x > N || y <= 0 || y > N;
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    for (int i = 1; i<= N;i++)
        for (int j = 1; j <= N;j++)
            cin >> board[i][j];

    for (int i = 1; i<= N;i++) {
        for (int j = 1; j <= N;j++) {
            if (board[j][i] == 0) continue;
            int cur_color = board[j][i];

            for (int dir = 0; dir < 4;dir++) {
                if (vis[dir][j][i]) continue;
                int nx = j;
                int ny = i;
                int cnt = 0;
                while (!oob(nx, ny) && board[nx][ny] == cur_color) {
                    vis[dir][nx][ny] = 1;
                    cnt++;
                    nx += dx[dir];
                    ny += dy[dir];
                }
                if (cnt == 5) {
                    cout << cur_color << endl;
                    cout << j << " " << i << endl;
                    return 0;
                }
            }
        }
    }

    cout << 0;
    return 0;
}
