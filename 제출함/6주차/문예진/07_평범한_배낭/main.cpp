#include <iostream>

using namespace std;

int bagDP[101][100001];
int item[101][101];

int maxx(int a, int b) {
    if (a > b) return a;
    else return b;
}

int main() {
    int N, K;
    cin >> N >> K;

    for (int i = 1; i <= N; i++) {
        cin >> item[i][0];
        cin >> item[i][1];
    }

    for (int i = 1; i <= N; i++) {
        int itemW = item[i][0];
        int itemP = item[i][1];
        for (int j = 0; j <= K; j++) {
            if (j >= itemW) {
                bagDP[i][j] = maxx(bagDP[i - 1][j - itemW] + itemP, bagDP[i - 1][j]);
            } else bagDP[i][j] = bagDP[i - 1][j];
        }
    }

    cout << bagDP[N][K];
}
