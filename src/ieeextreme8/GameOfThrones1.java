package ieeextreme8;

public class GameOfThrones1 {

	
	public static boolean anagramOfPalindrome(String a) {
		
		// count letters, at most one can have an odd number
		int[] counts = new int[26];
		for (int i=0; i<a.length(); i++) {
			char c = a.charAt(i);
			counts[c-'a']++;
		}
		
		int nodd = 0;
		for (int i=0; i<counts.length; i++) {
			if (counts[i] % 2 == 1) {
				nodd++;
				if (nodd > 1) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		String[] s = {"aaabbbb", "cdefghmnopqrstuvw", "cdcdcdcdeeeef"};
		
		for (int i=0; i<s.length; i++) {
			if (anagramOfPalindrome(s[i])) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}
}
