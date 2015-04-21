package CommentScore;

import checker.BadWordsChecker;
import checker.LengthChecker;
import checker.LexicalDiversity;
import checker.SpellChecker;

public class CommentScore {
	private float spellWeight;
	private float badWeight;
	private float lexWeight;
	private float lenWeight;

	private SpellChecker spellChecker;
	private BadWordsChecker badChecker;
	private LexicalDiversity lexChecker;
	private LengthChecker lenChecker;

	public CommentScore(float spellWeight, float badWeight, float lexWeight,
			float lenWeight) {
		this.spellWeight = spellWeight;
		this.badWeight = badWeight;
		this.lexWeight = lexWeight;
		this.lenWeight = lenWeight;

		spellChecker = new SpellChecker();
		badChecker = new BadWordsChecker();
		lexChecker = new LexicalDiversity();
		lenChecker = new LengthChecker(70);
	}

	public float rateComment(String comment) {
		System.out.println("Spell: " + spellChecker.check(comment) + "%");
		System.out.println("Bad: " + badChecker.check(comment) + "%");
		System.out.println("Lex: " + lexChecker.check(comment) + "%");
		System.out.println("Len: " + lenChecker.check(comment) + "%");

		return calcAVG(spellChecker.check(comment), badChecker.check(comment),
				lexChecker.check(comment), lenChecker.check(comment));
	}

	private float calcAVG(float spellScore, float badScore, float lexScore,
			float lenScore) {

		float avg = (((spellScore * spellWeight) + (badScore * badWeight)
				+ (lexScore * lexWeight) + (lenScore * lenWeight)) / (spellWeight
				+ badWeight + lexWeight + lenWeight));

		return avg;
	}
}
