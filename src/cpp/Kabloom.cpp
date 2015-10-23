#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

// score between two cards
int score(char a, char b) {

	if (a == b) {
		if ( a >= '2' && a <= '9') {
			return a-'0';
		} else if (a == 'T') {
			return 10;
		} else if (a == 'J' || a == 'Q' || a == 'K') {
			return 15;
		} else if (a == 'A') {
			return 20;
		} else if (a == 'R') {
			return 50;
		}
	} else if (a == 'R') {
		return score(b, b);
	} else if (b == 'R') {
		return score(a, a);
	}

	return -1;
}

int kabloom(char* s1, char* s2, int n) {

	int** s = new int*[n+1];
	for (int i=0; i<=n; ++i) {
		s[i] = new int[n+1];
	}

	for (int r=0; r<=n; ++r) {
		s[r][0] = 0;
		s[0][r] = 0;
	}

	// rows
	for (int r=1; r<=n; ++r) {
		char c1 = s1[r-1];
		// columns
		for (int c=1; c<=n; ++c) {
			char c2 = s2[c-1];

			// check for match
			int bestScore = score(c1, c2);
			if (bestScore >= 0) {
				bestScore += s[r-1][c-1];  // add to score
			}

			// check for largest score between diag, left, up
			if (s[r-1][c] > bestScore) {
				bestScore = s[r-1][c];
			}
			if (s[r][c-1] > bestScore) {
				bestScore = s[r][c-1];
			}

			s[r][c] = bestScore;
		}
	}

	int kscore = 2*s[n][n];

	// clean memory
	for (int i=0; i<=n; ++i) {
		delete[] s[i];
	}
	delete[] s;

	return kscore;
}

int main(int argc, char **argv) {

	int n;
	cin >> n;

	while (n > 0) {
		char* s1 = new char[n];
		char* s2 = new char[n];

		for (int i=0; i<n; ++i) {
			cin >> s1[i];
		}
		for (int i=0; i<n; ++i) {
			cin >> s2[i];
		}

		int kscore = kabloom(s1, s2, n);
		cout << kscore << std::endl;

		delete[] s1;
		delete[] s2;

		cin >> n;
	}

	return 0;
}
