package ieeextreme8;

public class BackToSquare1 {

	private static double backToSquare1(double[] p) {
		
		// based on formula:
		// C = 1 + (1 + p_0 + p_0*p_1 + p_0*p_1*p_2 + ... + p_0*p_1*...*p_{n-1} )/p_0*p_1*p_2*...*p_n
		int n = p.length;
		double prod = 1;
		double s = 1;
		
		if (n > 0) {
			for (int i=0; i<n-1; i++) {
				prod = prod*p[i];
				s += prod;
			}
			prod = prod*p[n-1];
			s = 1 + s/prod;
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		double[][] p = {
				{0.5, 0.25},
				{0.5},
				{0.3, 0.2, 0.1}
		};
		
		for (int i=0; i<p.length; i++) {
			double num = backToSquare1(p[i]);
			int expected = (int)Math.round(num);
			System.out.println("Expected # turns: " + expected);
		}
	}
	
}
