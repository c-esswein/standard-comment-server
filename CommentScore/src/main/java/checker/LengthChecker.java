package checker;


public class LengthChecker {
	float recommendedLength;

	public LengthChecker(int length) {
		recommendedLength = length;
	}

	public float check(String string) {
		String[] tokens = string.split("\\s+");

		if (tokens.length > recommendedLength)
			return 100;
		else
			return (tokens.length / recommendedLength) * 100;
	}
}