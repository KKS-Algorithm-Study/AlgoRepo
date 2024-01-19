#include <iostream>

using namespace std;

int main() {
    int std = 0, cnt = 0;
    char prevC = 'x';
    bool flag = false;

    string s;
    cin >> s;

    for (int i = 0; i < s.length(); i++) {
        if (prevC == 'x' && s[i] == 'w') {
            flag = false;
            std++;
            if (s[i + 1] != 'w') prevC = 'w';
        } else if (prevC == 'w' && s[i] == 'o') {
            cnt++;
            if (s[i + 1] != 'o') {
                if (cnt != std) {
                    cout << "0";
                    return 0;
                }
                prevC = 'o';
                cnt = 0;
            }
        } else if (prevC == 'o' && s[i] == 'l') {
            cnt++;
            if (s[i + 1] != 'l') {
                if (cnt != std) {
                    cout << "0";
                    return 0;
                }
                prevC = 'l';
                cnt = 0;
            }
        } else if (prevC == 'l' && s[i] == 'f') {
            cnt++;
            if (s[i + 1] != 'f') {
                if (cnt != std) {
                    cout << "0";
                    return 0;
                }
                prevC = 'x';
                cnt = 0;
                std = 0;
                flag = true;
            }
        } else {
            cout << "0";
            return 0;
        }
    }
    if (flag) cout << "1";
    else cout << "0";
}
