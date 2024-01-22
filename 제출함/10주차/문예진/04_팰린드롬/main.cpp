#include <iostream>
#include <vector>

using namespace std;

int arr[2001][2001];
int N, M;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    vector<int> nums = {0,};
    cin >> N;
    for (int i = 1; i <= N; i++) {
        int a;
        cin >> a;
        nums.push_back(a);
        arr[i][i] = 1;
    }

    for (int i = 1; i < N; i++) {
        if (nums[i] == nums[i + 1]) arr[i][i + 1] = 1;
    }

    for (int i = N - 1; i >= 1; i--) {
        for (int j = i + 2; j <= N; j++) {
            if (nums[i] == nums[j] && arr[i + 1][j - 1] == 1) arr[i][j] = 1;
        }
    }

    cin >> M;
    for (int i = 0; i < M; i++) {
        int a, b;
        cin >> a >> b;
        cout << arr[a][b] << '\n';
    }
}
