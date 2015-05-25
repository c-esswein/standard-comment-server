import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.User;
import proglab.dbconn.orm.ORM;
import proglab.dbconn.pager.Pager;

public class Main {
	public void main(String[] args) {
		ORM orm = ORM.getInstance();
		Pager<Article> pager = orm.getAllArticles();
		Map<User, Map<User, Integer>> userRelations = new HashMap<User, Map<User, Integer>>();

		while (pager.hasNext()) {
			List<Article> articles = pager.next();
			for (Article article : articles) {
				List<Comment> comments = article.getComments();
				for (Comment comment : comments) {
					User user = comment.getUser();
					List<Comment> subComments = comment.getComments();
					for (Comment subComment : subComments) {
						User subUser = subComment.getUser();
						if (userRelations.containsKey(user)) {
							if (userRelations.get(user).containsKey(subUser)) {
								int previous = userRelations.get(user)
										.get(subUser).intValue();
								userRelations.get(user)
										.put(subUser, previous++);
							} else {
								userRelations.get(user).put(subUser, 1);
							}
						} else {
							userRelations.put(user,
									new HashMap<User, Integer>());
						}
					}
				}
			}
		}
	}
}
