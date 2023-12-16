#include <iostream>

using namespace std;

int group[1000001];

int findFun(int x) {
    if (group[x] == x) return x;
    else group[x] = findFun(group[x]);
    return group[x];
}

void unionFun(int a, int b) {
    a = findFun(a);
    b = findFun(b);

    if (a > b) { //a가 작고 b가 크도록
        swap(a, b);
    }
    group[b] = a;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    int n, m;
    cin >> n >> m;
    for (int i = 0; i <= n; i++) {
        group[i] = i;
    }

    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        if (a == 0) unionFun(b, c);
        else {
            //if(b>c) swap(b,c); //b가 더 작게
            if (findFun(b) == findFun(c)) {
                cout << "YES\n";
            } else cout << "NO\n";
        }
    }
}
