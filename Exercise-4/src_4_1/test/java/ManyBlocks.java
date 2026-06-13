
public class ManyBlocks {

	static int main(int n) {
		int i = 0;
		int result = 1;
		outer: while (i != n) {
			if ((n + i) < 100) {
				result += 1;
				if (i > 5) {
					result += 1;
				} else {
					result += 2;
				}
				result += 3;

			} else if (i < 100) {
				for (int j = 90; j < i; j++) {
					result *= 2;
					if ((n + result) % 32 == 0) {
						i++;
						continue outer;
					}
				}
				result *= -1;
			}
			i++;
		}
		result *= 2;
		return result;
	}

}