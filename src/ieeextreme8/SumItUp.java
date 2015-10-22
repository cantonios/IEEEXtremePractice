package ieeextreme8;

public class SumItUp {

	private static final int MODULO = 1000000000+7;
	
	private static int sumitup(int[] balls, int Q) {
		
		int s = 0;
		// sum of balls
		for (int i=0; i<balls.length; i++) {
			s = (s + balls[i]) % MODULO;
		}
		
		// double every query
		for (int j = 0; j<Q; j++) {
			s = (s + s) % MODULO;
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		int[][] balls = {{1, 2, 3, 4, 5},{1,2,3,4,5}};
		int[] Q = {2, 0};
		
		for (int i=0; i<balls.length; i++) {
			int siu = sumitup(balls[i], Q[i]);
			System.out.println("SumItUp: " + siu);
		}
	}
	
}
