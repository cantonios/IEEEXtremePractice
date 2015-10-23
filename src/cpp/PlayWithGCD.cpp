#include <iostream>

using namespace std;
const int MODULO = 1000000000+7;

// binary implementation of Euclid's algorithm
int gcd(int a, int b) {
	int d = 0;

	// repeat while both a and b are even (check last bit)
	while ( ((a&1) == 0) && ((b&1) == 0) ) {
		a = a>>1;  // divide by 2
		b = b>>1;
		d++;
	}

	while (a != b) {
		if ( (a&1) == 0) {
			a = a >> 1;
		} else if ((b&1) == 0) {
			b = b >> 1;
		} else if (a > b) {
			a = (a-b) >> 1;
		} else {
			b = (b-a) >> 1;
		}
	}
	return a<<d;
}

//computes a*2^e % MODULO
int modmul2(int a, int e) {
	for (int i=0; i<e; i++) {
		a  = (a<<1) % MODULO;  // *2 mod MODULO
	}
	return a;
}

// computes a*e % MODULO
int modmul(int a, int e) {
	int s = 0;
	for (int i=0; i<e; i++) {
		s = (s + a) % MODULO;
	}
	return s;
}

// a-b % MODULO
int modsub(int a, int b) {
	if (a > b) {
		return a-b;
	} else {
		return a+MODULO-b;
	}
}

void playWithGCD() {

	// number of balls
	int n;
	cin >> n;

	int nballs=0;
	int balls[100];
	int counts[100];

	// load in n balls from std::cin, insert them into ball array
	int b;
	cin >> b; // first ball
	balls[0] = b;
	counts[0] = 1;
	nballs = 1;

	for (int i=1; i<n; ++i) {
		cin >> b;

		// binary insertion sort, counting unique balls
		int left = 0;
		int right = nballs;
		while (left < right) {
			int mid = (right+left)>>1;
			if (b == balls[mid]) {
				left = mid;
				break;
			} else if (b > balls[mid]) {
				left = mid+1;
			} else {
				right=mid;
			}
		}

		if (left < nballs) {
			if (balls[left] == b) {
				++counts[left]; // increase count of ball
			} else {
				// insert balls
				for (unsigned int j=nballs; j>left; --j) {
					balls[j] = balls[j-1];
					counts[j] = counts[j-1];
				}
				balls[left] = b;
				counts[left] = 1;
				++nballs;
			}
		} else {
			// append ball to end of array
			balls[nballs] = b;
			counts[nballs] = 1;
			++nballs;
		}
	}

	int maxGCD = balls[nballs-1];  // maximum possible gcd is largest number in set

	// create table rows=gcd, col=ball
	// t[r][c] = # ways to get GCD(balls[0..c])=r  (first row corresponds to gcd=0 for convenience)
	int** t = new int*[maxGCD+1];
	for (int i=0; i<=maxGCD; ++i) {
		t[i] = new int[nballs];
		for (int j=0; j<nballs; ++j) {
			t[i][j] = 0;  // initialize to zero
		}
	}

	// Adding the ith ball, then we have:
	// for any existing possible gcd, we have many, plus choosing zero of balls i
	//     --> copy previous column
	// for any existing possible gcd=r, we can now form a different gcd by choosing
	//    at least one of ball i
	//    -> for every t[m][i-1]>0, find k=gcd(m, ball[i]),
	//		 there are (2^c[i]-1) ways of choosing at least one ball i
	//       -> t[k][i] += t[m][i-1]*(2^c[i]-1)
	// We can also choose NO previous balls, and at least one of ball[i] to get
	//    gcd(ball[i]) = ball[i]
	//    -> t[ball[i]][i] += (2^c[i]-1)

	// treat first column separate, only one non-zero entry
	t[balls[0]][0] = modsub(modmul2(1, counts[0]), 1); //1*2^e-1

	// loop through columns
	for (int c=1; c<nballs; ++c) {
		// add new entries (rows actually start at 1 and go to maxGCD)
		for (int r = 1; r<=maxGCD; ++r) {
			if (t[r][c-1] > 0) {
				// copy previous entry
				t[r][c] += t[r][c-1];

				// find new GCD and add
				int b = gcd(r, balls[c]);
				int nb = modsub(modmul2(t[r][c-1], counts[c]), t[r][c-1]); // (# ways gcd=r) * (2^(#c)-1)
				t[b][c] = (t[b][c] + nb) % MODULO;
			}
		}

		// new ways of GCD without previous balls
		t[balls[c]][c] = (t[balls[c]][c] + modsub(modmul2(1, counts[c]), 1)) % MODULO; //1*2^e-1;
	}

	// queries
	int Q;
	cin >> Q;
	int x;
	for (int i=0; i<Q; ++i) {
		cin >> x;
		if (x <= maxGCD) {
			cout << t[x][nballs-1] << std::endl;
		} else {
			cout << 0 << std::endl;
		}
	}

	// clear table
	for (int i=0; i<=maxGCD; ++i) {
		delete[] t[i];
	}
	delete[] t;
}

int main(int argc, char **argv) {

	playWithGCD();

	return 0;
}
