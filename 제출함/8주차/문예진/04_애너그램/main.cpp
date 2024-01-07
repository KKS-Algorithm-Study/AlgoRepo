#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        string s;
        cin >> s;

        sort(s.begin(), s.end());
        do {
            cout << s << '\n';
        } while (next_permutation(s.begin(), s.end()));
    }
}
