package ieeextreme8;

public class Kabloom {
	
	// score between two cards
	private static int score(char a, char b) {
		
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
	
	public static int kabloom(String s1, String s2) {
		int n = s1.length();
		
		int[][] s = new int[n+1][n+1];
		byte[][] dir = new byte[n+1][n+1];
		
		final int DIAG = 1;
		final int UP = 2;
		final int LEFT = 3;
		
		for (int r=0; r<=n; r++) {
			s[r][0] = 0;
			dir[r][0] = 0;
			s[0][r] = 0;
			dir[0][r] = 0;
		}
		
		// rows
		for (int r=1; r<=n; r++) {
			char c1 = s1.charAt(r-1);
			// columns
			for (int c=1; c<=n; c++) {
				char c2 = s2.charAt(c-1);
				
				// check for match
				int bestScore = score(c1, c2);
				byte bestDir = DIAG;
				if (bestScore >= 0) {
					bestScore += s[r-1][c-1];  // add to score
				}
				
				// check for largest score between diag, left, up
				if (s[r-1][c] > bestScore) {
					bestDir = UP;
					bestScore = s[r-1][c];
				}
				if (s[r][c-1] > bestScore) {
					bestDir = LEFT;
					bestScore = s[r][c-1];
				}
				
				s[r][c] = bestScore;
			    dir[r][c] = bestDir;
			}
		}
		
		// recover play
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int r = n;
		int c = n;
		int pos = dir[r][c];
		while (pos != 0) {
			switch(pos) {
			case DIAG:
				sb1.append(s1.charAt(r-1));
				sb2.append(s2.charAt(c-1));
				r--;
				c--;
				break;
			case UP:
				r--;
				break;
			case LEFT:
				c--;
				break;
			}
			pos = dir[r][c];
		}
				
		// build sequences
		String subsequence1 = sb1.reverse().toString();
		String subsequence2 = sb2.reverse().toString();
		
		System.out.println("Hand: " + subsequence1 + ", " + subsequence2);
		System.out.println("Score: " + 2*s[n][n]);
		
		return 2*s[n][n];
	}
	
	public static void main(String[] args) {
		
		String[][] s = { 
				{"63742AKRT", "3547RAQKT"},
				{"RR54ATQ", "Q3TA888"},
				{"A23456789TJQK", "KQJT98765432A"},
				{"AAAAAA", "KQJT98"},
				{"A23456789TJQK", "A23456789TJQK"}
		};
		
		for (int i=0; i<s.length; i++) {
			kabloom(s[i][0], s[i][1]);
		}
	}
	
}
