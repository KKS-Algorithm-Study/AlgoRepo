#include <iostream>
#include <algorithm>
#include <math.h>

using namespace std;

int A[1000001];
int B[1000001];

int largesearch(int left, int right, int num) {
    int mid = (left + right) / 2;
    if (B[mid] == num) return B[mid];
    if (B[mid] > num) {
        if (mid == left) return B[mid];
        else return min(largesearch(left, mid - 1, num), B[mid]);
    } else {
        if (mid == right) return 987654321;
        else return largesearch(mid + 1, right, num);
    }
}

int smallsearch(int left, int right, int num) {
    int mid = (left + right) / 2;
    if (B[mid] == num) return B[mid];
    if (B[mid] < num) {
        if (mid == right) return B[mid];
        else return max(smallsearch(mid + 1, right, num), B[mid]);
    } else {
        if (mid == left) return -1;
        else return smallsearch(left, mid - 1, num);
    }
}

int main() {
    int T;
    cin >> T;
    for (int testcase = 0; testcase < T; testcase++) {
        int N, M;
        long long result = 0;
        cin >> N >> M;
        for (int i = 1; i <= N; i++) cin >> A[i];
        for (int i = 1; i <= M; i++) cin >> B[i];
        sort(B + 1, B + M + 1);
        for (int i = 1; i <= N; i++) {
            int a = smallsearch(1, M, A[i]);
            int b = largesearch(1, M, A[i]);
            if (a == -1) a = B[1];
            if (b == -1) b = B[M];
            if (abs(A[i] - a) <= abs(b - A[i])) result += a;
            else result += b;
        }
        cout << result << '\n';
    }
}
