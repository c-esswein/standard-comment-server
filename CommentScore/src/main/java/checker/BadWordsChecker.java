package checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BadWordsChecker {
	private File file;
	private Set<String> dict;

	public BadWordsChecker() {
		file = new File("bad.txt");
		dict = new HashSet<String>(100000);
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

		float matches = 0;
		String[] tokens = string.split("\\s+|\\-");

		if (tokens.length != 0) {
			for (String token : tokens) {
				if (dict.contains(token.toLowerCase().trim())) {
					matches++;
				}
			}
			return (100 - ((matches / tokens.length) * 100));
		} else
			return 0.0001f;
	}

}
