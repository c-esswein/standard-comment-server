package proglab.dbconn.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="article")
public class Article {

	@Id
	@GeneratedValue
    @Column(name="article_id")
	private int id;
	
	@Column(name="article_ext_id")
	private String extId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="text", columnDefinition="text")
	private String text;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="category")
	private String category;
	
	@Column(name="sub_category")
	private String subCategory;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="url")
	private String url;
	
	@Column(name="parse_date")
	private Date parseDate;
	
	@OneToMany (mappedBy="article")
	private List<Comment> comments;
	
	
	public Article() {
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

	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}
	
	public String getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(final String subCategory) {
		this.subCategory = subCategory;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Date getParseDate() {
		return this.parseDate;
	}

	public void setParseDate(final Date parseDate) {
		this.parseDate = parseDate;
	}
	
	public List<Comment> getAllComments() {
		return this.comments;
	}
	
	public List<Comment> getComments() {
		List<Comment> comments = new ArrayList<Comment>();
		for (final Comment c : this.comments) {
			if (c.getParent() == null)
				comments.add(c);
		}
		return comments;
	}
	
	public void addComment(final Comment comment) {
		comment.setArticle(this);
		this.comments.add(comment);
	}
	
	public void addComment(final Comment comment, final boolean setArticle) {
		this.comments.add(comment);
	}
	
}
