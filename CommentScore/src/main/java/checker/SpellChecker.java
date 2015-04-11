package checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellChecker {
	private File file;
	private Set<String> dict;

	public SpellChecker() {
		file = new File("src/main/resources/big.txt");
		dict = new HashSet<String>(1000000);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			dict.add(scanner.nextLine().toLowerCase());
		}
		scanner.close();

	}

	public float check(String string) {

		float matches = 0;
		String[] tokens = string.split("\\s+");

		for (String token : tokens) {
			if (dict.contains(token.toLowerCase())) {
				matches++;
			}
		}
			return ((matches / tokens.length) * 100);
	}

}
