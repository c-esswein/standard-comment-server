package proglab.dbconn.orm;

import java.util.List;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.Polarity;
import proglab.dbconn.bean.User;
import proglab.dbconn.bean.UserStat;
import proglab.dbconn.pager.Pager;

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
		mysql.setUsername("proglab-user");
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
		config.addClass(UserStat.class);

		EbeanServerFactory.create(config);
	}

	public Article createArticle(final String extId) {
		Article a = Ebean.find(Article.class).where()
				.eq("article_ext_id", extId).findUnique();

		if (a != null)
			return a;

		a = new Article();
		a.setExtId(extId);
		return a;
	}

	public Comment createComment(final String extId) {
		Comment c = Ebean.find(Comment.class).where()
				.eq("comment_ext_id", extId).findUnique();

		if (c != null)
			return c;

		c = new Comment();
		c.setExtId(extId);
		return c;
	}

	public void save(final Article article) {
		Ebean.save(article);
	}

	public void save(final Comment comment) {
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

		Ebean.save(comment);
	}

	public void save(final UserStat userStat) {
		Ebean.save(userStat);
	}

	public List<Comment> getCommentsWithoutSentiment(final int rows) {
		return Ebean.find(Comment.class).where().eq("sentiment", null)
				.setMaxRows(rows).findList();
	}

	public List<Comment> getCommentsWithoutQualityScore(final int rows) {
		return Ebean.find(Comment.class).where().eq("quality_score", 0)
				.setMaxRows(rows).findList();
	}

	public Pager<Article> getAllArticles() {
		return new Pager<Article>(Article.class, 1000);
	}

	public Pager<Comment> getAllComments() {
		return new Pager<Comment>(Comment.class, 1000);
	}

	public Pager<User> getAllUsers() {
		return new Pager<User>(User.class, 1000);
	}

	public static ORM getInstance() {
		return instance;
	}

}
