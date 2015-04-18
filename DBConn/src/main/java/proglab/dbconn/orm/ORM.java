package proglab.dbconn.orm;

import java.util.List;

import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.User;

import com.avaje.ebean.Ebean;

public final class ORM {

	public static void save(final Object bean) {
		if (bean.getClass() == Comment.class) {
			Comment comment = (Comment) bean;
			User user = comment.getUser();

			if (user != null && user.getId() == 0 && user.getUsername() != "") {
				User u = Ebean.find(User.class).where()
						.eq("username", user.getUsername()).findUnique();

				if (u != null) {
					comment.setUser(u);
				} else {
					Ebean.save(user);
				}
			}
		}

		Ebean.save(bean);
	}

	public static List<Comment> getCommentsWithoutSentiment(final int rows) {
		return Ebean.find(Comment.class).where().eq("sentiment", null)
				.setMaxRows(rows).findList();
	}
	
	public static List<Comment> getCommentsWithoutQualityScore(final int rows) {
		return Ebean.find(Comment.class).where().eq("quality_score", 0)
				.setMaxRows(rows).findList();
	}

}
