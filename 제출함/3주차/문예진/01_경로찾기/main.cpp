/*
 * 플로이드 와샬 : i에서 j까지 가는 것과 i에서 k로 가고, k에서 j로 가는 것은 같다
 */

#include <iostream>

using namespace std;
int graph[101][101];

int main() {
    int N;
    cin >> N;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> graph[i][j];
        }
    }

    for (int k = 0; k < N; k++) { // 중간 노드
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (graph[i][k] && graph[k][j]) {
                    graph[i][j] = 1;
                }
            }
        }
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << graph[i][j] << " ";
        }
        cout << '\n';
    }
}
