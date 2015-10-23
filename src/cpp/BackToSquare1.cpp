#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

int main(int argc, char **argv) {

	int n;
	cin >> n;

	while (n > 0) {

		// based on formula:
		// C = 1 + (1 + p_0 + p_0*p_1 + p_0*p_1*p_2 + ... + p_0*p_1*...*p_{n-1} )/p_0*p_1*p_2*...*p_n
		double s = 1;
		double p;
		double prod = 1;

		if (n > 1) {
			for (int i=0; i<n-2; ++i) {
				cin >> p;
				prod = prod*p;
				s += prod;
			}
			cin >> p;
			prod = prod*p;
			s = 1 + s/prod;
		}

		// round s to nearest integer
		int si = round(s);

		cout << si << std::endl;

		// prepare for next test case
		cin >> n;
	}

	return 0;
}
