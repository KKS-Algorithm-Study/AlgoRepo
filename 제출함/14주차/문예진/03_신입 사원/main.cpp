#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<pair<int, int>> v;

int main() {
    int t;
    cin >> t;
    for (int testcase = 0; testcase < t; testcase++) {
        int n;
        cin >> n;
        v.clear();

        for (int i = 0; i < n; i++) {
            int a, b;
            cin >> a >> b;
            v.push_back({a, b});
        }
        sort(v.begin(), v.end());

        int tmp = 0, res = 1;
        for (int i = 1; i < n; i++) {
            if (v[tmp].second > v[i].second) {
                res++;
                tmp = i;
            }
        }
        cout << res << '\n';
    }
}
