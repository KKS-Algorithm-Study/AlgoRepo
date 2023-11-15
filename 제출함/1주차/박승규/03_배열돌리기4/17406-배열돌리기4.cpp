#include <iostream>
#include <vector>
#include <queue>
#include <string.h>
#include <algorithm>
#define ll long long
#define endl "\n"
using namespace std;

int n, m, k;
int mat[51][51];
int tmp[51][51];
vector<tuple<int, int, int>> pos;

void copy() {
    for (int i = 1 ;i<=n;i++)
        for (int j = 1; j<=m;j++)
            tmp[i][j] = mat[i][j];
}

int getMinRow() {
    int ret = 1e9;
    for (int i = 1; i<=n;i++) {
        int sum = 0;
        for (int j = 1; j<=m; j++) {
            sum += tmp[i][j];
        }
        ret = min(ret, sum);
    }
    return ret;
}

void rotate(int idx) {
    int row, col, w;
    tie(row, col, w) = pos[idx-1];

    for (int t = w; t > 0; t--) {

        // 위
        int upperTmp = tmp[row - t][col + t];
        for (int i = col + t; i >= col - t + 1; i--) {
            tmp[row - t][i] = tmp[row - t][i - 1];
        }

        // 오른쪽
        int rightTmp = tmp[row + t][col + t];
        for (int i = row + t; i >= row - t + 2; i--) {
            tmp[i][col + t] = tmp[i-1][col + t];
        }
        tmp[row - t + 1][col + t] = upperTmp;

        // 아래
        int downTmp = tmp[row + t][col - t];
        for (int i = col - t; i <= col + t - 2; i++) {
            tmp[row + t][i] = tmp[row + t][i + 1];
        }
        tmp[row + t][col + t - 1] = rightTmp;

        // 왼쪽 
        for (int i = row - t; i <= row + t - 2; i++) {
            tmp[i][col - t] = tmp[i+1][col - t];
        }
        tmp[row + t - 1][col - t] = downTmp;
    }
}

int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);
    // freopen("input.txt", "r", stdin);

    cin >> n >> m >> k;

    for (int i = 1; i<=n;i++)
        for (int j = 1; j<=m;j++)
            cin >> mat[i][j];

    vector<int> ord;
    for (int i = 1; i<=k;i++) {
        int r, c, s;
        cin >> r >> c >> s;
        pos.push_back({r, c, s});
        ord.push_back(i);
    }
    
    int res = 1e9;
    do {
        copy();
        for (int idx : ord)
            rotate(idx);
        res = min(res, getMinRow());
    } while (next_permutation(ord.begin(), ord.end()));
    cout << res;
    return 0;
}