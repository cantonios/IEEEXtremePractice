#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

void magicsquare(int **s, int n) {

	int numNonMagic = 0;
	int nonMagic[2*n+1];

	int dsum = 0;
	for (int i=0; i<n; ++i) {
		dsum += s[i][i];
	}

	// columns, backwards
	for (int i=0; i<n; ++i) {
		int csum = 0;
		for (int j=0; j<n; j++) {
			csum += s[j][n-i-1];
		}
		if (csum != dsum) {
			nonMagic[numNonMagic] = -n+i;
			numNonMagic++;
		}
	}

	// anti-diagonal
	int asum = 0;
	for (int i=0; i<n; i++) {
		asum += s[i][n-i-1];
	}
	if (asum != dsum) {
		nonMagic[numNonMagic] = 0;
		numNonMagic++;
	}

	// rows
	for (int i=0; i<n; i++) {
		int rsum = 0;
		for (int j=0; j<n; j++) {
			rsum += s[i][j];
		}
		if (rsum != dsum) {
			nonMagic[numNonMagic] = i+1;
			numNonMagic++;
		}
	}

	cout << numNonMagic << std::endl;
	for (int i=0; i<numNonMagic; ++i) {
		cout << nonMagic[i] << std::endl;
	}

}

int main() {

	int n;
	cin >> n;

	int** mat = new int*[n];
	for (int i=0; i<n; ++i) {
		mat[i] = new int[n];
	}

	for (int i=0; i<n; ++i) {
		for (int j=0; j<n; ++j) {
			cin >> mat[i][j];
		}
	}

	magicsquare(mat, n);

	for (int i=0; i<n; ++i) {
		delete mat[i];
	}
	delete[] mat;

    return 0;
}
