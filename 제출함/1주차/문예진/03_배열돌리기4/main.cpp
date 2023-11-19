#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
struct rot_info {
    int r, c, s;
} rot_data[7];

int N, M, K;
vector<vector<int>> arr;
vector<int> seq;
int minval = 1000000;

void findArrMin(vector<vector<int>> &tmp) { // 최소값 찾기
    for (int i = 1; i <= N; i++) {
        int sum = 0;
        for (int j = 1; j <= M; j++) {
            sum += tmp[i][j];
        }
        if (sum < minval) minval = sum;
    }
}

void rotateArr(int r, int c, int s, vector<vector<int>> &boardResult) { // 배열 회전하기
    r -= s, c -= s;
    int len = 2 * s;
    int firstItem = boardResult[r][c];

    for (int i = 0; i < len; i++) {
        boardResult[r + i][c] = boardResult[r + i + 1][c];
    }
    r += len;
    for (int i = 0; i < len; i++)
        boardResult[r][c + i] = boardResult[r][c + i + 1];
    c += len;
    for (int i = 0; i < len; i++)
        boardResult[r - i][c] = boardResult[r - i - 1][c];
    r -= len;
    for (int i = 0; i < len; i++)
        boardResult[r][c - i] = boardResult[r][c - i - 1];
    c -= len;
    boardResult[r][c + 1] = firstItem;
}

void simul(vector<int> &seq) {
    vector<vector<int>> tmp = arr;
    for (auto &s: seq) {
        for (int i = 1; i <= rot_data[s].s; i++) {
            rotateArr(rot_data[s].r, rot_data[s].c, i, tmp);
        }
    }
    findArrMin(tmp);
}

int main() {
    cin >> N >> M >> K;
    arr.resize(N + 1, vector<int>(M + 1));
    for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) {
            cin >> arr[i][j];
        }
    }

    for (int i = 1; i <= K; i++) {
        cin >> rot_data[i].r >> rot_data[i].c >> rot_data[i].s;
        seq.push_back(i);
    }

    // 모든 경우를 돌려 보기
    do {
        simul(seq);
    } while (next_permutation(seq.begin(), seq.end()));

    cout << minval;
    return 0;
}
