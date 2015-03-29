package proglab.dbconn.orm;

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

	public static void delete(final Object bean) {
		// TODO: delete bean with respect to foreign key constraints
	}
}
