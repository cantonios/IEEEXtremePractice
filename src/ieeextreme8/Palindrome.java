package ieeextreme8;

public class Palindrome {
	
	public static String findMaxPalindromeLCS(String s) {
		
		int n = s.length();
		int[][] len = new int[n+1][];
		byte[][] dir = new byte[n+1][];
		
		final byte UP = 1;
		final byte LEFT = 2;
		final byte DIAG = 3;
		
		// initialize
		for (int r=0; r<=n; r++) {
			len[r] = new int[n+1-r];
			dir[r] = new byte[n+1-r];
			len[r][0] = 0;
			len[0][r] = 0;
			dir[r][0] = 0;
			dir[0][r] = 0;
		}
		
		// rows
		for (int r=1; r<=n; r++) {
			char c1 = s.charAt(r-1);
			// columns
			for (int c=1; c<(n-r+1); c++) {
				char c2 = s.charAt(n-c);
				if (c1 == c2) {
					len[r][c] = len[r-1][c-1] + 1; // increase length by one
					dir[r][c] = DIAG;
				} else {
					if (len[r-1][c] > len[r][c-1]) {
						len[r][c] = len[r-1][c];
						dir[r][c] = UP;
					} else {
						len[r][c] = len[r][c-1];
						dir[r][c] = LEFT;
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
		
		// reconstruct longest subsequence:
		StringBuilder sb = new StringBuilder();
		String mid = "";
		
		int r = maxr;
		int c = n-maxr;
		int pos = dir[r][c];
		if (pos != DIAG) {
			// append a middle character to grow by 1
			mid = "" + s.charAt(r);
		}
		
		while (pos != 0) {
			switch(pos) {
			case DIAG:
				sb.append(s.charAt(r-1));
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
		
		String half = sb.toString();
		String subsequence = sb.reverse().toString() + mid + half;
		return subsequence;
	}
	
	public static void main(String[] args) {
		String[] s = {"lukeiamyourfather", 
				"anynontrivialpropertyofrecursivelyenumerablelanguagesisundecidable",
				"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestib"};

		for (int i=0; i<s.length; i++) {
			String maxPalindrome = findMaxPalindromeLCS(s[i]);
			System.out.println("Palindrome: " + maxPalindrome + "(" + maxPalindrome.length() + ")");
		}
		
	}
}