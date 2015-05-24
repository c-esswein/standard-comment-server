package proglab.dbconn.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="user_stats")
public class UserStat {

	@Id 
	@Column(name="user_id")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name="num_comments")
	private int numComments;
	
	@Column(name="avg_quality_score")
	private float avgQualityScore;
	
	@Column(name="avg_sentiment_score")
	private float avgSentimentScore;
	
	@Column(name="avg_rating")
	private float avgRating;
	
	@Column(name="num_replies")
	private int numReplies;

	
	public int getId() {
		return this.id;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getNumComments() {
		return this.numComments;
	}

	public void setNumComments(final int numComments) {
		this.numComments = numComments;
	}

	public float getAvgQualityScore() {
		return this.avgQualityScore;
	}

	public void setAvgQualityScore(final float avgQualityScore) {
		this.avgQualityScore = avgQualityScore;
	}

	public float getAvgSentimentScore() {
		return this.avgSentimentScore;
	}

	public void setAvgSentimentScore(final float avgSentimentScore) {
		this.avgSentimentScore = avgSentimentScore;
	}

	public float getAvgRating() {
		return this.avgRating;
	}

	public void setAvgRating(final float avgRating) {
		this.avgRating = avgRating;
	}

	public int getNumReplies() {
		return this.numReplies;
	}

	public void setNumReplies(final int numReplies) {
		this.numReplies = numReplies;
	}
	
	public void addNumReplies(final int numReplies) {
		this.numReplies += numReplies;
	}	
	
}
