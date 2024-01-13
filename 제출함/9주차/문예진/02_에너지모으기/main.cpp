#include <iostream>
#include <vector>

using namespace std;

int result;

void solution(vector<int> &v, int val) {
    if (v.size() == 3) {
        val += v[0] * v[2];
        if (val > result) result = val;
        return;
    }

    for (int i = 1; i < v.size() - 1; i++) {
        vector<int> tmp = v;
        tmp.erase(tmp.begin() + i);
        solution(tmp, val + (tmp[i - 1] * tmp[i]));
    }
}

int main() {
    vector<int> v;
    int N;
    cin >> N;
    for (int i = 0; i < N; i++) {
        int a;
        cin >> a;
        v.push_back(a);
    }

    solution(v, 0);
    cout << result;
}
