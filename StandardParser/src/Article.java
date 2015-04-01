import java.util.Date;


public class Article {

	private  String text;
	private long article_ext_id;
	private String title;
	private Date date;
	private String category;
	private String sub_category;
	private String url;
	private Date parse_date;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getArticle_ext_id() {
		return article_ext_id;
	}
	public void setArticle_ext_id(long article_ext_id) {
		this.article_ext_id = article_ext_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSub_category() {
		return sub_category;
	}
	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getParse_date() {
		return parse_date;
	}
	public void setParse_date(Date parse_date) {
		this.parse_date = parse_date;
	}
}
