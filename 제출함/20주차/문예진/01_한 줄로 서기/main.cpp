#include <iostream>

using namespace std;

int arr[10];

int main() {
    int N;
    cin >> N;
    for (int i = 1; i <= N; i++) {
        int a;
        cin >> a;
        for (int j = 0; j < N; j++) {
            if (arr[j] != 0) continue;
            if (a == 0 && arr[j] == 0) {
                arr[j] = i;
                break;
            } else if (a > 0) a--;
        }
    }
    for (int i = 0; i < N; i++) cout << arr[i] << " ";
}
