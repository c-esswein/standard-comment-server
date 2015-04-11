import CommentScore.CommentScore;

public class Main {

	public static void main(String[] args) {
		String goodComment = "Das ist ein zufälliger Kommentar der richtig geschrieben ist und einen hohen Quality score haben sollte";
		String badComment = "dieser komment isch ein schlechta kommi scheiße dreck";

		CommentScore score = new CommentScore(100, 50, 80, 20);
		System.out.println(score.rateComment(badComment) + "%");

	}
}
