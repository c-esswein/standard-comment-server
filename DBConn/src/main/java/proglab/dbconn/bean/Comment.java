package proglab.dbconn.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private int id;

	@Column(name="comment_ext_id")
	private String extId;
	
	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;

	@Column(name = "comment_title")
	private String title;

	@Column(name = "comment_text", columnDefinition = "text")
	private String text;

	@Column(name = "comment_date")
	private Date date;

	@Column(name = "up_votes")
	private int upVotes;

	@Column(name = "down_votes")
	private int downVotes;

	@Column(name = "sentiment")
	private Polarity sentiment;

	@Column(name = "quality_score")
	private float qualityScore;

	@ManyToOne 
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@OneToMany(mappedBy = "parent")
	private List<Comment> comments;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	public Comment() {
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(final Article article) {
		article.addComment(this, false);
		this.article = article;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public int getUpVotes() {
		return this.upVotes;
	}

	public void setUpVotes(final int upVotes) {
		this.upVotes = upVotes;
	}

	public int getDownVotes() {
		return this.downVotes;
	}

	public void setDownVotes(final int downVotes) {
		this.downVotes = downVotes;
	}

	public Polarity getSentiment() {
		return this.sentiment;
	}

	public void setSentiment(final Polarity sentiment) {
		this.sentiment = sentiment;
	}

	public float getQualityScore() {
		return this.qualityScore;
	}

	public void setQualityScore(final float qualityScore) {
		this.qualityScore = qualityScore;
	}

	public void setParent(final Comment comment) {
		this.parent = comment;
		comment.addComment(comment, false);
	}
	
	public Comment getParent() {
		return this.parent;
	}

	public void addComment(final Comment comment) {
		comment.setParent(this);
		this.comments.add(comment);
	}
	
	public void addComment(final Comment comment, final boolean setParent) {
		this.comments.add(comment);
	}
	
	public List<Comment> getComments() {
		return this.comments;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		user.addComment(this, false);
		this.user = user;
	}
	
	public void setUser(final String username) {
		this.user = new User();
		user.setUsername(username);
	}
	
	public String toString()
	{
		String res ="";
		if(parent!=null)
			res+=parent.toString();
		
		res+=">"+extId;
		
		return res;
	}
	
}
