#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    vector<int> v;

    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        int a;
        cin >> a;
        v.push_back(a);
    }
    sort(v.begin(), v.end());

    int maxval = 0;

    for (int i = 0; i < N; i++) {
        if (maxval < v[i] * (N - i)) maxval = v[i] * (N - i);
    }
    cout << maxval;
}
