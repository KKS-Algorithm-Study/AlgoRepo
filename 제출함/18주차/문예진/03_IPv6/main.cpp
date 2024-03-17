#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main() {
    string s;
    cin >> s;

    vector<string> v;

    int len = s.length();
    string tmp = "";
    for (int i = 0; i < len; i++) { // 벡터에 입력값들 넣기
        if (s[i] == ':') {
            if (i == 1 && s[i - 1] == ':') continue; // 맨 앞에서 연속 2개 콜론일 때 빈 문자열 2개 들어가는 거 방지
            v.push_back(tmp);
            tmp = "";
        } else tmp += s[i];
        if (i == len - 1) v.push_back(tmp);
    }

    for (int i = 0; i < v.size(); i++) { // 콜론 2개 처리
        if (v[i] == "") {
            v[i] = "0000";
            int vlen = v.size();
            for (int j = 0; j < 8 - vlen; j++) {
                v.insert(v.begin() + i, "0000");
            }
        }
    }

    for (int i = 0; i < v.size(); i++) { // 4글자 아닌 입력 처리
        if (v[i].length() < 4) {
            int vlen = v[i].length();
            for (int j = 0; j < 4 - vlen; j++) {
                v[i] = "0" + v[i];
            }
        }
    }

    for (int i = 0; i < 7; i++) {
        cout << v[i] << ":";
    }
    cout << v[7];
}
