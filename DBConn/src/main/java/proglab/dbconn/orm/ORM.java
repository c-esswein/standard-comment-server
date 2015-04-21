package proglab.dbconn.orm;

import java.util.List;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.User;

import com.avaje.ebean.Ebean;

public final class ORM {

	public static void save(final Object bean) {
		if (bean.getClass() == Comment.class) {
			Comment comment = (Comment) bean;
			User user = comment.getUser();

			// check if user exists
			if (user != null && user.getId() == 0 && user.getUsername() != "") {
				User u = Ebean.find(User.class).where()
						.eq("username", user.getUsername()).findUnique();

				if (u != null) {
					comment.setUser(u);
				} else {
					Ebean.save(user);
				}
			}

			// check if comment exists and update votes
			if (comment.getExtId() != null) {
				Comment c = Ebean.find(Comment.class).where()
						.eq("comment_ext_id", comment.getExtId()).findUnique();

				if (c != null) {
					// update votes
					c.setDownVotes(comment.getDownVotes());
					c.setUpVotes(comment.getUpVotes());
					
					// update user if there is one specified
					if (comment.getUser() != null)
						c.setUser(comment.getUser());
					
					// update article id
					if (comment.getArticle() != null)
						c.setArticle(comment.getArticle());
					
					Ebean.save(c);
					return;
				}
			}
			Ebean.save(comment);
		}

		else if (bean.getClass() == Article.class) {
			Article article = (Article) bean;

			if (article.getExtId() != null) {
				Article a = Ebean.find(Article.class).where()
						.eq("article_ext_id", article.getExtId()).findUnique();

				// article already exists
				if (a != null) {
					article.setId(a.getId());
					return;
				}
			}
			Ebean.save(article);
		}

		else {
			Ebean.save(bean);
		}
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
