#include <iostream>

using namespace std;

int map[51][51];
int N, M, cnt, curX, curY, curD;
int dir[] = {0,1,2,3};
int checkDir[4][2] = {{-1,0},{0,1},{1,0},{0,-1}};
int backDir[4][2] = {{1,0},{0,-1},{-1,0},{0,1}};

bool checkEmpty() { // 주변이 비었는지 확인
    for(int i=0; i<4; i++) {
        if(map[curX+checkDir[i][0]][curY+checkDir[i][1]]==0) {
            return true;
        }
    }
    return false;
}

bool checkFront(int curd) { // 앞이 비었는지 확인
    for(int i=0; i<4; i++) {
        curd = curd-1;
        if(curd<0) curd=3;
        if(map[curX+checkDir[curd][0]][curY+checkDir[curd][1]]==0) {
            curD = curd;
            curX = curX+checkDir[curd][0];
            curY = curY+checkDir[curd][1];
            return true;
        }
    }
    return false;
}

int main() {
    cin >> N >> M;
    cin>>curX>>curY>>curD;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            cin >> map[i][j];
        }
    }

    while (1) {
        if(map[curX][curY]==0) {
            cnt++;
            map[curX][curY] = 2; // 청소 완료
        }
        if (!checkEmpty()) { // 만약 주변이 비어있지 않다면 후진가능여부 확인
            if(map[curX+backDir[curD][0]][curY+backDir[curD][1]]==1) {
                break;
            }
            else { // 후진
                curX = curX+backDir[curD][0];
                curY = curY+backDir[curD][1];
            }
        }
        else {
            checkFront(curD);
        }

    }
    cout<<cnt;
    return 0;
}
