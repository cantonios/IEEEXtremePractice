package ieeextreme8;

public class LCS {

	private static final byte UP = 1;
	private static final byte LEFT = 2;
	private static final byte DIAG = 4;
	
	// basic longest-common-subsequence implementation
	// with sequence recovery
	public static String LCS_str(String s1, String s2) {
		
		int n1 = s1.length();
		int n2 = s2.length();
		long[][] s = new long[n1+1][n2+1];
		byte[][] dir = new byte[n1+1][n2+1];
		
		for (int r=0; r<=n1; r++) {
			s[r][0] = 0;
			dir[r][0] = 0;
		}
		
		for (int c=0; c<n2; c++) {
			s[0][c] = 0;
			dir[0][c] = 0;
		}
		
		// rows
		for (int r=1; r<=n1; r++) {
			char c1 = s1.charAt(r-1);
			// columns
			for (int c=1; c<=n2; c++) {
				char c2 = s2.charAt(c-1);
				if (c1 == c2) {
					s[r][c] = s[r-1][c-1] + 1; // increase length by one
					dir[r][c] = DIAG;
				} else {
					if (s[r-1][c] > s[r][c-1]) {
						s[r][c] = s[r-1][c];
						dir[r][c] = UP;
					} else {
						s[r][c] = s[r][c-1];
						dir[r][c] = LEFT;
					}
				}
			}
		}
		
		// reconstruct longest subsequence:
		StringBuilder sb = new StringBuilder();
		int r = n1;
		int c = n2;
		int pos = dir[r][c];
		while (pos != 0) {
			switch(pos) {
			case DIAG:
				sb.append(s1.charAt(r-1));
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
		
		String subsequence = sb.reverse().toString();
		return subsequence;
	}
	
	public static void main(String[] args) {
		String s1 = "asklk;ljalksasdfjklhljkhlkeajhwl";
		String s2 = "ljkhlkjhl;ajknhklhlajehklhljkhae";
		
		String lcs = LCS_str(s1, s2);
		System.out.println("The longest common subsequence of");
		System.out.println("    " + s1);
		System.out.println("    " + s2);
		System.out.println("is: " + lcs + "(" + lcs.length() + ")");
		
	}
	
}
