package CommentScore;

import checker.BadWordsChecker;
import checker.LengthChecker;
import checker.LexicalDiversity;
import checker.SpellChecker;

public class CommentScore {
	private SpellChecker spellChecker;
	private BadWordsChecker badChecker;
	private LexicalDiversity lexChecker;
	private LengthChecker lenChecker;

	public CommentScore() {
		spellChecker = new SpellChecker();
		badChecker = new BadWordsChecker();
		lexChecker = new LexicalDiversity();
		lenChecker = new LengthChecker(70);
	}

	public float rateComment(String comment) {
		String formattedComment = comment.replaceAll("[^a-zA-ZäöüÄÜÖß\\- ]",
				" ");
		float spellRes = spellChecker.check(formattedComment);
		String recognisedWords = spellChecker.getRecognisedWords();
		float badRes = badChecker.check(recognisedWords);
		float lexRes = lexChecker.check(formattedComment);
		float lenRes = lenChecker.check(formattedComment);

		float avg = calcAVG(spellRes, badRes, lexRes, lenRes);
		if (avg != 0)
			return avg;
		else
			return 0.0001f;
	}

	private float calcAVG(float spellScore, float badScore, float lexScore,
			float lenScore) {

		double lenWeight = 100;
		double badWeight = ((Math.log10(lenScore + 1) * 50) * 0.7 + spellScore * 0.3) / 2;
		double lexWeight = lenScore;
		double spellWeight = Math.log10(lenScore + 1) * 50;

		float avg = (float) ((((spellScore * spellWeight)
				+ (badScore * badWeight) + (lexScore * lexWeight) + (lenScore * lenWeight)) / (lenWeight
				+ badWeight + lexWeight + spellWeight)));

		System.out.println("Spelling: " + spellScore + " - " + spellWeight);
		System.out.println("Bad: " + badScore + " - " + badWeight);
		System.out.println("Lex: " + lexScore + " - " + lexWeight);
		System.out.println("Len: " + lenScore + " - " + lenWeight);

		return avg;
	}
}
