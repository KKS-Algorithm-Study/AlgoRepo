#include <iostream>

using namespace std;
char sound[2501];

int main() {
    int res = 0, tmp = 0;
    string s;
    cin >> s;
    int len = s.length();
    if (len % 5 != 0) {
        cout << "-1";
        return 0;
    }
    char arr[5] = {'q', 'u', 'a', 'c', 'k'};
    int idx = -1;
    while (1) {
        for (int i = 0; i < len; i++) {
            if (s[i] == '0') continue;
            if (s[i] == arr[idx + 1]) {
                if (s[i] == 'k') {
                    idx = -1;
                    tmp++;
                } else idx++;
                s[i] = '0';
            }
        }
        if (tmp > 0) {
            res++;
            tmp = 0;
        } else {
            cout << "-1";
            return 0;
        }
        int rem = 0;
        for (int i = 0; i < len; i++) {
            if (s[i] != '0') rem++;
        }
        if (rem == 0) {
            cout << res;
            return 0;
        }
        if (rem < 5) {
            cout << "-1";
            return 0;
        }
    }
}
