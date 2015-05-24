package proglab.dbconn.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
    @Column(name="user_id")
	private int id;
	
	@Column(name="user_ext_id")
	private String extId;
	
	@Column(name="username")
	private String username;
	
	@OneToMany (mappedBy="user")
	private List<Comment> comments;

	@OneToOne(mappedBy = "user")
	private UserStat userStat;
	
	public User() {
		this.comments = new ArrayList<Comment>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getExtId() {
		return this.extId;
	}
	
	public void setExtId(final String extId) {
		this.extId = extId;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public List<Comment> getComments() {
		return this.comments;
	}
	
	public void addComment(final Comment comment) {
		comment.setUser(this);
		this.comments.add(comment);
	}
	
	public void addComment(final Comment comment, final boolean setUser) {
		this.comments.add(comment);
	}
	
	public UserStat getUserStat() {
		return this.userStat;
	}
	
	public void setUserStat(final UserStat userStat) {
		this.userStat = userStat;
	}
	
}
