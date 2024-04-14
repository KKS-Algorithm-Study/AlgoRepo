#include <iostream>

using namespace std;

int main() {
    int N;
    long long c=1, r=1;
    cin >> N;
    bool flag = false;

    while(1) {
        if (c * c - r * r == N) {
            flag = true;
            cout << c << "\n";
        }
        if (c - r == 1 && c * c - r * r > N) break;
        if (c * c - r * r > N) r++;
        else c++;
    }
    if(!flag) cout<<"-1";
}
