package checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class LexicalDiversity {
	private Map<String, Integer> occurences;
	private File file;
	private Set<String> dict;

	public LexicalDiversity() {
		occurences = new HashMap<String, Integer>();
		file = new File("src/main/resources/stopwords.txt");
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
		String[] split = string.split("\\s+");
		List<String> tokens = new ArrayList<String>();
		
		for (String token : split) {
			tokens.add(token);
		}
		
		tokens.removeAll(dict);
		occurences.clear();

		for (String token : tokens) {
			token = token.toLowerCase();
			if (occurences.containsKey(token)) {
				occurences.put(token, occurences.get(token) + 1);
			} else {
				occurences.put(token, 1);
			}
		}

		return (checkForSingleOcc(occurences) / tokens.size()) * 100;
	}

	private float checkForSingleOcc(Map<String, Integer> occurences) {
		float singleWords = 0;
		for (Entry<String, Integer> e : occurences.entrySet()) {
			if (e.getValue() == 1) {
				singleWords++;
			}
		}
		return singleWords;

	}

}
