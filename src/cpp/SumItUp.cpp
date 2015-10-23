#include <iostream>
using namespace std;

const int MODULO = 1000000000+7;

int main() {

	int n, c;
	int s = 0;  // sum

	cin >> n;  // number of elements

	// sum array
	for (int i=0; i<n; ++i) {
		cin >> c;  // read in number
		s = (s + c) % MODULO;
	}

	cin >> n;  // number of queries
	for (int i=0; i<n; ++i) {
		// read and discard queries
		cin >> c;
		// double sum for every query
		s = (s<<1) % MODULO;
	}

	cout << s << std::endl;
    return 0;
}
