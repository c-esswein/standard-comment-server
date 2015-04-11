package Checkers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LexicalDiversity {
	private Map<String, Integer> occurences;

	public LexicalDiversity() {
		occurences = new HashMap<String, Integer>();
	}

	public float check(String string) {
		String[] tokens = string.split("\\s+");
		occurences.clear();

		for (String token : tokens) {
			token = token.toLowerCase();
			if (occurences.containsKey(token)) {
				occurences.put(token, occurences.get(token) + 1);
			} else {
				occurences.put(token, 1);
			}
		}

		return (checkForSingleOcc(occurences) / tokens.length) * 100;
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
