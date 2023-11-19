#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#define endl "\n"
using namespace std;

int n;
int board[21][21];
int dx[4] = {-1,0,1,0};
int dy[4] = {0,1,0,-1};
int ans;

void Input() {
    cin >> n;
    for (int i = 0; i <n;i++)
        for (int j = 0; j <n;j++)
            cin >> board[i][j];
}

int get_ans() {
    int res = -1;
    for (int i = 0; i <n;i++)
        for (int j =0; j <n;j++)
            res = max(res, board[i][j]);
    return res;
}
void rotation() {
    int tmp[21][21];

    for (int i =0; i <n;i++)
        for (int j = 0;j < n;j++)
            tmp[i][j] = board[i][j];

    for (int i =0; i <n;i++)
        for (int j = 0;j < n;j++)
            board[j][n-i-1] = tmp[i][j];
}

void recover(int tmp[][21]) {
    for (int i = 0; i < n;i++){
        for (int j = 0; j < n;j++){
            board[i][j] = tmp[i][j];
        }
    }
}
void push_left(int cnt) {
    
    while(cnt--) rotation();
    for (int i = 0; i <n;i++){
        for (int j = 0; j <n;j++){
            for (int k = j + 1; k < n;k++){
                if (board[i][k] == 0) continue;
                if (board[i][j] == board[i][k]) {
                    board[i][j] *= 2;
                    board[i][k] = 0;
                }
                break;
            }
        }
        vector<int> v;
        for (int j = 0; j <n;j++) {
            if (board[i][j] != 0) v.push_back(board[i][j]);
        }
        for (int j = 0; j < n;j++){
            if (j < v.size()) board[i][j] = v[j];
            else board[i][j] = 0;
        }
    }    
}

void copy(int tmp[][21]) {

    for (int i = 0; i <n;i++){
        for (int j = 0; j <n;j++){
            tmp[i][j] = board[i][j];
        }
    }
}
void dfs(int idx) {
    // 5번 이동 완료
    if (idx == 5) {
        ans = max(ans, get_ans());
        return;
    }

    // 0,1,2,3을 상하좌우로 정하고 회전
    // 회전하고 왼쪽으로 밀기
    // 0,1,2,3 수 만큼 회전하기
    // 회전하고 밀면서 board 바뀜 -> 상태 저장해야함
    int tmp[21][21];
    copy(tmp);
    for (int i = 0; i < 4;i++){
        push_left(i);
        dfs(idx + 1);
        recover(tmp);
    }
}
void Solve() {

    dfs(0);
    cout << ans << endl;
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(0);cout.tie(0);

    Input();
    Solve();
    return 0;
}
