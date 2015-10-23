#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <string>

using namespace std;

bool is_palindrome_anagram(string s) {

	char c[26];
	for (int i=0; i<26; ++i) {
		c[i] = 0;
	}

	for (unsigned int i=0; i<s.length(); ++i) {
		c[s.at(i)-'a']++;
	}

	int oddCount = 0;
	for (int i=0; i<26; ++i) {
		if (c[i] % 2 == 1) {
			if (oddCount > 0) {
				return false;
			}
			oddCount++;
		}
	}

	return true;
}

int main() {

    string s;
    cin>>s;

    bool valid = is_palindrome_anagram(s);

    if(valid) {
        cout<<"YES";
    } else {
        cout<<"NO";
    }
    return 0;
}
