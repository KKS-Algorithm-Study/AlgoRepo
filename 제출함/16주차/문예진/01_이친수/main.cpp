#include <iostream>
using namespace std;

int main()
{
    long arr[90];

    arr[0] = 1;
    arr[1] = 1;

    int a;
    cin>>a;
    for(int i=2; i<a; i++)
    {
        arr[i] = arr[i-1]+arr[i-2];
    }
    cout<<arr[a-1]<<'\n';
}
