#include <iostream>

using namespace std;

int arr[100001][3];

int main() {
    int n;
    cin >> n;
    arr[1][0] = 1;
    arr[1][1] = 1;
    arr[1][2] = 1;
    // ox xo xx
    for (int i = 2; i <= n; i++) {
        arr[i][0] = (arr[i - 1][1] + arr[i - 1][2]) % 9901;
        arr[i][1] = (arr[i - 1][0] + arr[i - 1][2]) % 9901;
        arr[i][2] = (arr[i - 1][0] + arr[i - 1][1] + arr[i - 1][2]) % 9901;
    }
    cout << (arr[n][0] + arr[n][1] + arr[n][2]) % 9901;
}
