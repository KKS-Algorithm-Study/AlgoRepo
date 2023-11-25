#include <iostream>
#include <string.h>
#include <algorithm>
#define endl "\n"
using namespace std;

int n, q;
int board[100][100];
int tmp[100][100];
int dx[4] = {-1,0,1,0};
int dy[4] = {0,1,0,-1};
int visit[100][100];
int check[100][100];

void rotation(int x, int y, int l) {
    for (int i= 0; i<l;i++){
        for (int j = 0; j<l;j++){
            tmp[j][l -1-i] = board[x + i][y + j];
        }
    }
    for (int i= 0; i<l;i++){
        for (int j = 0; j<l;j++){
            board[x + i][y + j] = tmp[i][j];
        }
    }
}

bool oob(int x, int y) {
    return (x < 0 || x>= n || y < 0 || y >= n);
}
void solve(int l) {
    l = 1 << l;

    for (int i = 0; i<n;i+=l){
        for (int j = 0; j<n;j+=l) {
            rotation(i, j, l);
        }
    }
    memset(check, 0, sizeof(check));
    for (int i = 0; i < n;i++){
        for (int j = 0; j< n;j++){
            int cnt = 0;
            for (int dir = 0; dir < 4;dir++){
                int nx = i + dx[dir];
                int ny = j + dy[dir];
                if (oob(nx, ny)) continue;
                if (board[nx][ny] <= 0) continue;
                cnt++;
            }
            if (cnt < 3) check[i][j] = 1;
        }
    }
    for (int i= 0; i<n;i++){
        for (int j = 0; j<n;j++){
            if (check[i][j]) board[i][j]--;
        }
    }
}

int land_cnt = 0;
int dfs_cnt = 0;
int sumation() {
    int ret = 0;
    for (int i =0 ; i<n;i++){
        for (int j = 0; j<n;j++){
            if (board[i][j] > 0) {
                ret += board[i][j];
                land_cnt++;
            }
        }
    }
    return ret;
}

int dfs(int x, int y) {
    visit[x][y] = 1;
    int ret = 1;
    for (int dir = 0; dir < 4;dir++){
        int nx = x + dx[dir];
        int ny = y + dy[dir];
        if (oob(nx, ny)) continue;
        if (visit[nx][ny]) continue;
        if (board[nx][ny] <= 0) continue;
        ret += dfs(nx, ny);
        dfs_cnt++;
    }
    if (land_cnt == dfs_cnt) ret = 0;
    return ret;
}
int max_mass() {
    int ret = 0;
    memset(visit, 0, sizeof(visit));
    for (int i = 0;i < n;i++){
        for (int j = 0; j<n;j++){
            if (board[i][j] <= 0) continue;
            if (visit[i][j]) continue;
            ret = max(ret, dfs(i, j));
        }
    }
    return ret;
}
int main() {
    ios::sync_with_stdio(false);
    cin.tie(0); cout.tie(0);
    // freopen("input.txt", "r", stdin);
    
    cin >> n >> q;
    n = 1 << n;
    for (int i= 0; i< n;i++) {
        for (int j = 0; j< n;j++){
            cin >> board[i][j];
        }
    }

    while (q--){
        int l; cin >> l;
        solve(l);
    }
    cout << sumation() << endl << max_mass() << endl;
    return 0;
}
