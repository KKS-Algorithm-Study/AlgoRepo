#include <iostream>

using namespace std;

int arr[1025][1025];
int sum[1025][1025];

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    
    int N, M, K;
    cin >> N >> M;

    for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) { //가로로 더하고
            cin >> arr[i][j];
            sum[i][j] = arr[i][j] + sum[i][j - 1];
        }
        for (int j = 1; j <= M; j++) { //누적시키기
            sum[i][j] += sum[i - 1][j];
        }
    }

    cin >> K;
    for (int i = 0; i < K; i++) {
        int a, b, c, d;
        cin >> a >> b >> c >> d;

        cout << sum[c][d] - sum[a - 1][d] - sum[c][b - 1] + sum[a - 1][b - 1] << '\n';
    }
}
