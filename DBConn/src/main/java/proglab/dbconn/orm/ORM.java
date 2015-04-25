package proglab.dbconn.orm;

import java.util.List;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.Polarity;
import proglab.dbconn.bean.User;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

public final class ORM {

	private final static ORM instance = new ORM();
	
	private ORM() {
		ServerConfig config = new ServerConfig();
		config.setName("mysql");

		DataSourceConfig mysql = new DataSourceConfig();
		mysql.setDriver("com.mysql.jdbc.Driver");
		mysql.setUsername("root");
		mysql.setPassword("");
		mysql.setUrl("jdbc:mysql://localhost:3306/derstandard?characterEncoding=UTF-8");

		config.setDataSourceConfig(mysql);

		config.setDdlGenerate(false);
		config.setDdlRun(false);

		config.setRegister(true);
		config.setDefaultServer(true);

		config.addClass(Article.class);
		config.addClass(Comment.class);
		config.addClass(User.class);
		config.addClass(Polarity.class);

		EbeanServerFactory.create(config);
	}

	public void save(final Object bean) {
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
			if (comment.getId() == 0 && comment.getExtId() != null) {
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

			if (article.getId() == 0 && article.getExtId() != null) {
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

	public List<Comment> getCommentsWithoutSentiment(final int rows) {
		return Ebean.find(Comment.class).where().eq("sentiment", null)
				.setMaxRows(rows).findList();
	}

	public List<Comment> getCommentsWithoutQualityScore(final int rows) {
		return Ebean.find(Comment.class).where().eq("quality_score", 0)
				.setMaxRows(rows).findList();
	}
	
	public static ORM getInstance() {
		return instance;
	}

}
