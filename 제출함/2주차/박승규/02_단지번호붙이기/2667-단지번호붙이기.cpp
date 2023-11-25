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

int n;
char board[26][26];
int vis[26][26];
vector<int> v;

void bfs(int i, int j) {
    int cnt = 0;
    queue<pair<int, int>> q;

    q.push({i, j});
    vis[i][j] = 1;

    while (!q.empty()) {
        int x, y;
        tie(x, y) = q.front(); q.pop();

        cnt++;
        for (int dir = 0; dir < 4;dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
            if (vis[nx][ny]) continue;
            if (board[nx][ny] == '0') continue;
            q.push({nx, ny});
            vis[nx][ny] = 1;
        }
    }
    v.push_back(cnt);
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n;

    for (int i = 0; i < n;i++)
        for (int j = 0; j < n;j++)
            cin >> board[i][j];
    
    for (int i = 0; i < n;i++) {
        for (int j = 0; j <n;j++) {
            if (board[i][j] == '0' || vis[i][j]) continue;
            bfs(i, j);
        }
    }

    sort(v.begin(), v.end());
    cout << v.size() << endl;
    for (int e : v)
        cout << e << endl;
    return 0;
}
