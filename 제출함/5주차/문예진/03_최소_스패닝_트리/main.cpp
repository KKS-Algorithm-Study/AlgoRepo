#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int group[10001];
vector<pair<int, pair<int,int>>> v;

int findFun(int x) {
    if(group[x] == x) return x;
    else group[x] = findFun(group[x]);
    return group[x];
}

void unionFun(int a, int b) {
    a = findFun(a);
    b = findFun(b);

    if(a>b) swap(a,b);
    group[b]=a;
}

int main() {
    int V,E, sum=0;
    cin>>V>>E;

    for(int i=1; i<=V; i++) {
        group[i]=i;
    }

    for(int i=0; i<E; i++) {
        int a,b,c;
        cin>>a>>b>>c;
        v.push_back({c, make_pair(a,b)});
    }
    sort(v.begin(),v.end());

    for(int i=0; i<E; i++) {
        if(findFun(v[i].second.first)!= findFun(v[i].second.second)) {
            unionFun(v[i].second.first,v[i].second.second);
            sum+=v[i].first;
        }
    }
    cout<<sum;
}
