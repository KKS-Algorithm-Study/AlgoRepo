#include <iostream>
#include <algorithm>
using namespace std;

int n, m;
long long gain;
int tree[1000001];

bool ask(int cutH) {
    gain = 0;
    for (int i = 0; i < n; i++) {
        if(tree[i]<=cutH) break;
        gain += tree[i]-cutH;
    }
    bool result = gain >= m;
    return result;
}

int main() {
    cin >> n >> m;
    for (int i = 0; i < n; i++) {
        cin >> tree[i];
    }
    sort(tree,tree+n,greater<int>());
    
    long long left = 0;
    long long right = 2000000000;
    long long mid;
    long long answer = left;

    while (left < right) {
        mid = (left + right) / 2;
        if (ask(mid)) {
            answer = mid;
            left = mid + 1;
        } else right = mid;
    }
    cout << answer;
}
