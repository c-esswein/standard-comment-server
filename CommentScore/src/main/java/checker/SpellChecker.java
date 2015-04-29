package checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellChecker {
	private File file;
	private Set<String> dict;
	private StringBuilder recognised;

	public SpellChecker() {
		recognised = new StringBuilder();
		file = new File("big.txt");
		dict = new HashSet<String>(1000000);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			dict.add(scanner.nextLine().toLowerCase().trim());
		}
		scanner.close();

	}

	public float check(String string) {
		recognised.setLength(0);
		float matches = 0;
		String[] tokens = string.split("\\s+|\\-");
		if (tokens.length != 0) {
			for (String token : tokens) {
				if (dict.contains(token.toLowerCase().trim())) {
					matches++;
					recognised.append(token);
					recognised.append(" ");
				}
			}
			float result = (matches / tokens.length) * 100;

			return result;
		} else
			return 0;
	}

	public String getRecognisedWords() {
		return recognised.toString();
	}

}
