package ieeextreme8;

public class MagicSquare {

	public static void magicsquare(int[][] s) {
		
		int n = s.length;
		int numNonMagic = 0;
		int[] nonMagic = new int[2*n+1];
		
		int dsum = 0;
		for (int i=0; i<n; i++) {
			dsum += s[i][i];
		}
		
		// columns, backwards
		for (int i=0; i<n; i++) {
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
		
		System.out.print(numNonMagic + " ");
		for (int i=0; i<numNonMagic; i++) {
			System.out.print(nonMagic[i] + " ");
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		int[][][] s = {
				{   {8, 1, 6}, 
					{3, 5, 7}, 
					{4, 9, 2}	},
				{ {16, 3, 2, 13}, 
				  {5, 10, 11, 8}, 
				  {6, 9, 7, 12}, 
				  {4, 15, 14, 1} }
			};
		
		for (int i=0; i<s.length; i++) {
			magicsquare(s[i]);
		}
	}
}
