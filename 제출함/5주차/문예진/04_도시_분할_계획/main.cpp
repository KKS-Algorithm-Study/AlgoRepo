#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int group[100001];
vector<pair<int, pair<int,int>>> cost;

int findFun(int x) {
    if(group[x]==x) return x;
    else group[x] = findFun(group[x]);
    return group[x];
}

void unionFun(int a, int b) {
    a = findFun(a);
    b = findFun(b);

    if(a<b) swap(a,b);
    group[a] = b;
}

int main() {
    int N,M,sum=0, cnt=0;
    cin>>N>>M;

    for(int i=1; i<=N; i++) {
        group[i]=i;
    }

    for(int i=0; i<M; i++) {
        int a,b,c;
        cin>>a>>b>>c;
        cost.push_back({c,{a,b}});
    }
    sort(cost.begin(),cost.end());

    for(int i=0; i<M; i++) {
        if(findFun(cost[i].second.first)!= findFun(cost[i].second.second)) {
            cnt++;
            if(cnt==N-1) break;
            unionFun(cost[i].second.first, cost[i].second.second);
            sum+=cost[i].first;
        }
    }
    cout<<sum;
}
