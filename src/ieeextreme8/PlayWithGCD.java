package ieeextreme8;

import java.util.Arrays;

public class PlayWithGCD {
	
	// binary implementation of Euclid's algorithm
	public static int gcdBinary(int a, int b) {
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
	
	private static final int MODULO = 1000000000+7;
	
	//computes a*2^e % MODULO
	private static int modmul2(int a, int e) {
		for (int i=0; i<e; i++) {
			a  = (a<<1) % MODULO;  // *2 mod MODULO
		}
		return a;
	}
	
	// a-b % MODULO
	private static int modsub(int a, int b) {
		if (a > b) {
			return a-b;
		} else {
			return a+MODULO-b;
		}
	}
	
	public static int[] playWithGCD(int[] balllist, int[] Q) {
		
		// sort balls, created ordered list with counts
		Arrays.sort(balllist);
		// max 100 unique balls
		int[] balls = new int[100];     // balls
		int[] counts = new int[100];    // counts
		int nballs = 0;
		int lastball = -1;
		
		// build up list of unique balls and counts
		for (int i=0; i<balllist.length; i++) {
			if (balllist[i] != lastball) {
				nballs++;
				lastball = balllist[i];
				balls[nballs-1] = lastball;
			}
			counts[nballs-1]++;
		}
		
		int maxGCD = lastball;  // maximum possible gcd is largest number in set
		
		// create table rows=gcd, col=ball
		// t[r][c] = # ways to get GCD(balls[0..c])=r  (first row corresponds to gcd=0 for convenience)
		int[][] t = new int[maxGCD+1][nballs];
		
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
		for (int c=1; c<nballs; c++) {
			
			// add new entries (rows actually start at 1 and go to maxGCD)
			for (int r = 1; r<=maxGCD; r++) {
				if (t[r][c-1] > 0) {
					// copy previous entry
					t[r][c] += t[r][c-1];
					
					// find new GCD and add
					int b = gcdBinary(r, balls[c]);
					int nb = modsub(modmul2(t[r][c-1], counts[c]), t[r][c-1]); // (# ways gcd=r) * (2^(#c)-1)
					t[b][c] = (t[b][c] + nb) % MODULO;
				}
			}
			
			// new ways of GCD without previous balls
			t[balls[c]][c] = (t[balls[c]][c] + modsub(modmul2(1, counts[c]), 1)) % MODULO; //1*2^e-1;
		}
		
		int[] out = new int[Q.length];
		for (int i=0; i<Q.length; i++) {
			if (Q[i] <= maxGCD) {
				out[i] = t[Q[i]][nballs-1];
			} else {
				out[i] = 0;
			}
		}
		
		return out;
	}
	
	// brute force implementation
	public static int[] playWithGCDBrute(int[] balls, int[] Q) {
		
		int lastMask = (1<<balls.length)-1;
		int maxBall = balls[0];
		for (int i=1; i<balls.length; ++i) {
			if (balls[i] > maxBall) {
				maxBall = balls[i];
			}
		}
		
		int[] s = new int[maxBall+1];
		
		for (long m = 1; m<=lastMask; m++) {
			
			long mask = m;
			int g = -1;
			for (int i=0; i<balls.length; ++i) {
				if ((mask&1) > 0) {
					if (g < 0) {
						g = balls[i];
					} else {
						g = gcdBinary(g, balls[i]);
					}
				}
				mask = (mask >> 1);
			}
			
			s[g] = (s[g] + 1) % MODULO;
			
		}
		
		int[] out = new int[Q.length];
		for (int i=0; i<Q.length; ++i) {
			if (Q[i] <= maxBall) {
				out[i] = s[Q[i]];
			} else {
				out[i] = 0;
			}
		}
		return out;
		
	}
	
	public static void main(String[] args) {
		
		int[][] balls = {{2,3,5,6,6}, {2, 3, 3, 5, 6, 6, 6}, {2, 2, 1, 4, 6, 6, 4, 6, 4, 10, 21231, 21231, 21231, 21231, 5, 2, 2, 2, 5, 6, 4, 3, 234},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			     2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			     3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			     4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}};
		int[][] Q = {{2, 5}, {1, 2, 3, 5, 6}, {1, 2, 3, 4, 5, 6}, {0, 1, 2, 3, 4}};
		
		for (int i=0; i<balls.length; i++) {
			int[] out = playWithGCD(balls[i], Q[i]);
			for (int j=0; j<out.length; j++) {
				System.out.print(out[j] + " ");
			}
			System.out.println();
			
			//         // brute force
			//			int[] out2 = playWithGCDBrute(balls[i], Q[i]);
			//			System.out.print(" ( ");
			//			for (int j=0; j<out2.length; j++) {
			//				System.out.print(out2[j] + " ");
			//			}
			//			System.out.print(")");
			System.out.println();
		}
	}
}
