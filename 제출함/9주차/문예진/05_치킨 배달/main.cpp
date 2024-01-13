#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int N, M, res = 987654321;
int map[51][51];
vector<pair<int, int>> chickenPlace;
vector<pair<int, int>> house;

int main() {
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> map[i][j];
            if (map[i][j] == 1) house.push_back({i, j});
            else if (map[i][j] == 2) chickenPlace.push_back({i, j});
        }
    }
    int total_chicken = chickenPlace.size();

    //next_permutation 으로 조합 구하기 위한 서브 배열
    int subarr[total_chicken];
    fill_n(subarr, total_chicken, 0);
    for (int i = 0; i < M; i++) subarr[i] = 1;

    do {
        vector<pair<int, int>> cand;
        for (int i = 0; i < total_chicken; i++) {
            if (subarr[i] == 1) cand.push_back({chickenPlace[i].first, chickenPlace[i].second});
        }
        //점수 거리 계산
        int ttmp = 0;
        for (int i = 0; i < house.size(); i++) {
            int pres = 987654321;
            for (int j = 0; j < M; j++) {
                int tmp = abs(house[i].first - cand[j].first) + abs(house[i].second - cand[j].second);
                if (tmp < pres) pres = tmp;
            }
            ttmp += pres;
        }
        if (ttmp < res) res = ttmp;
    } while (prev_permutation(subarr, subarr + total_chicken));

    cout << res;
}
