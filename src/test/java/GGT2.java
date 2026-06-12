

public class GGT2 {

	public static void main(int a, int b) {
		while (a != b) {
			if (a > b) {
				a = a - b;
			} else {
				b = b - a;
			}
		}
		int ergebnis = a;
	}

}
