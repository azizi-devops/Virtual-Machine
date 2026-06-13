public class Prime {

	// calculates whether the given value is a prime number
	static boolean main(short n) {
		if ((n & 1) == 0)
			return false;
		else {
			int i = 3;
			while (i != n) {
				if (n % i == 0)
					return false;
				i++;
			}
		}
		return true;
	}
}