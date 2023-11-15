#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;


int board[51][51];
int dx[4] = {-1,0,1,0};
int dy[4] = {0,1,0,-1};
int n, m;
int r, c, d;

int oob(int x, int y) {
    return x < 0 || x >= n || y < 0 || y >=m;
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> m;
    cin >> r >> c >> d;

    for (int i = 0 ; i < n;i++) {
        for (int j = 0; j < m;j++) {
            cin >> board[i][j];
        }
    }

    int res = 0;
    while (1) {
        if (board[r][c] == 0) {
            res++;
            board[r][c] = -1;
        }
        int flag = 1;

        for (int dir = 0; dir < 4;dir++){
            int nx = r + dx[dir];
            int ny = c + dy[dir];
            
            if (oob(nx, ny) || board[nx][ny] == 1) continue;
            if (board[nx][ny] == 0) {
                flag = 0; // 청소 할 곳이 있음.
            }
        }

        if (!flag) {
            if (d== 0) d = 3;
            else d--;
            if (board[r+dx[d]][c+dy[d]] == 0){
                r += dx[d];
                c += dy[d];
            }
        } else {
            if (board[r - dx[d]][c - dy[d]] != 1) {
                r -= dx[d];
                c -= dy[d];
            } else
                break;
        }
    }
    cout << res;
    return 0;
}