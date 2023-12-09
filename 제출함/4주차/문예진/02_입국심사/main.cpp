#include <iostream>

using namespace std;

unsigned long long n, m;
unsigned long long s[100001];

bool ask(unsigned long long TotalTime) {
    unsigned long long friends = 0;
    for (int i = 0; i < n; i++) {
        friends += (TotalTime / s[i]);
    }
    return friends >= m;
}

int main() {
    cin >> n >> m;
    unsigned long long maxval = 0;
    for (int i = 0; i < n; i++) {
        cin >> s[i];
        if (s[i] > maxval) maxval = s[i];
    }
    unsigned long long right = maxval * m;
    unsigned long long left = 0;
    unsigned long long mid;

    while (left < right) {
        mid = (left + right) / 2;
        if (ask(mid)) right = mid;
        else left = mid + 1;
    }
    cout << right;
}
