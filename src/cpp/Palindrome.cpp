#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

int max_palindrome(string s) {

	int n = s.length();
	int** len = new int*[n+1];

	// initialize
	for (int r=0; r<=n; r++) {
		len[r] = new int[n+1-r];
		len[r][0] = 0;
		len[0][r] = 0;
	}

	// rows
	for (int r=1; r<=n; r++) {
		char c1 = s.at(r-1);
		// columns
		for (int c=1; c<(n-r+1); c++) {
			char c2 = s.at(n-c);
			if (c1 == c2) {
				len[r][c] = len[r-1][c-1] + 1; // increase length by one
			} else {
				if (len[r-1][c] > len[r][c-1]) {
					len[r][c] = len[r-1][c];
				} else {
					len[r][c] = len[r][c-1];
				}
			}
		}
	}

	// check diagonal for longest subsequence
	int maxr = 0;
	int maxl = 0;
	for (int r=1; r<=n; r++) {
		if (len[r][n-r] > maxl) {
			maxl = len[r][n-r];
			maxr = r;
		}
	}

	// double length for palindrome
	maxl = maxl<<1;

	// check if we can add an odd letter in the middle
	int r = maxr;
	int c = n-maxr;
	if (s.at(r) != s.at(c)) {
		++maxl;
	}
	return maxl;
}

int main() {

    string s;
    cin>>s;

    int maxl = max_palindrome(s);
    cout << maxl << std::endl;

    return 0;
}
