#include <iostream>
using namespace std;

void Eratos(int m, int n)
{
    bool* PrimeArray = new bool[n+1];

    for(int i=2; i<=n; i++)
        PrimeArray[i]= true; //일단 전부 true

    for(int i=2; i*i<=n; i++)
    {
        if(PrimeArray[i])
        {
            for(int j=i*i; j<=n; j+=i)
                PrimeArray[j] = false;
        }
    }
    PrimeArray[1]=false;
    for(int i=m; i<=n; i++)
    {
        if(PrimeArray[i]) cout<<i<<'\n';
    }
}

int main()
{
    ios::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

    int m,n;
    cin>>m>>n;

    Eratos(m,n);
}
