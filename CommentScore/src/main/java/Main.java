import java.util.List;

import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;
import CommentScore.CommentScore;

public class Main {

	public static void main(String[] args) {

		CommentScore score = new CommentScore();
		ORM orm = ORM.getInstance();

		List<Comment> commentsWithoutQualityScore;
		do {
			commentsWithoutQualityScore = orm
					.getCommentsWithoutQualityScore(100);
			for (Comment c : commentsWithoutQualityScore) {
				String comment = (c.getTitle() + " " + c.getText());
				System.out.println(comment);
				c.setQualityScore(score.rateComment(comment));
				orm.save(c);
			}
		} while (commentsWithoutQualityScore.size() > 0);
//		 System.out.println(score.rateComment("apple"));

	}
}
