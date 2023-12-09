#include <iostream>

using namespace std;
int n, m;
int arr[5001];
int res = 987654321;

bool ask(int mid) {
    int cnt = 1; // 구간 하나는 꼭 있다
    int minval = arr[0], maxval = arr[0];

    for (int i = 1; i < n; i++) {
        if (arr[i] < minval) minval = arr[i];
        if (arr[i] > maxval) maxval = arr[i];

        if ((maxval - minval) > mid) { // mid 값보다 큰 차이를 갖는 구간은 존재하지 않는다
            cnt++;
            minval = arr[i];
            maxval = arr[i];
        }
    }
    return cnt <= m; // 구간의 개수가 적다 -> mid 값을 줄여야 한다
}

int main() {
    cin >> n >> m;
    int maxval = -1;
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
        if (arr[i] > maxval) maxval = arr[i];
    }

    int right = maxval; // 구간의 점수는 최대값-최소값이므로 배열의 최대값을 넘을 수 없음
    int left = 0;
    while (left < right) {
        int mid = (left + right) / 2;
        if (ask(mid)) {
            if (res > mid) res = mid;
            right = mid;
        } else left = mid + 1;
    }
    cout << res;
}
