package proglab.parser;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;

public class HtmlParser {

	private Document doc;
	private DownloadResponse downloadResponse;
	private long article_ext_id;

	public HtmlParser(DownloadResponse _response, long articleId) {

		downloadResponse = _response;
		doc = Jsoup.parse(_response.getHtml());
		Charset s = Charset.forName("UTF-8");
		doc.outputSettings().charset(s);
		this.article_ext_id=articleId;
	}

	public Article GetArticle() {
		String title = doc.getElementById("content-header").select("h1").text();
		Date date = convertDate(doc.getElementById("content-header")
				.select("span.date").text());

		Article a = new Article();
		
		a.setTitle(title);
		a.setDate(date);
		a.setParseDate(new Date());
		a.setUrl(downloadResponse.getUrl());
		a.setExtId(Long.toString(article_ext_id));
		
		/*ToDo:
		 * Category
		 * Subcategory
		 * Text
		 * Image URL
		 * */
		
		ORM.save(a);
		
		return a;
	}

	public int GetPagecount() {
		Element buttonLast = doc.select("button.std-button.last").first();

		if (buttonLast == null)
			throw new IllegalArgumentException(
					"Unable to get pagecount. HTML element 'std-button last' not fount in HTML.");

		return Integer.parseInt(buttonLast.attr("data-pagenumber"));
	}

	public int GetCommentcount() {
		Elements elements = doc.getElementsByClass("posting");

		if (elements == null)
			throw new IllegalArgumentException(
					"Unable to get Comment Count. HTML element 'posting-container' not fount in HTML.");

		return elements.size();
	}

	public List<Comment> GetComments() {
		List<Comment> comments = new ArrayList<Comment>();

		Elements elements = doc.getElementsByClass("posting");
		for (Element ele : elements) {
			int id;
			int parentid = 0;
			String username;
			int userid;
			Date date;
			String content;
			String title;
			int red;
			int green;

			id = Integer.parseInt(ele.attr("data-postingid"));
			username = ele.attr("data-communityname");
			userid = Integer.parseInt(ele.attr("data-communityidentityid"));

			if (ele.attr("data-parentpostingid") != null
					&& !ele.attr("data-parentpostingid").equals(""))
				parentid = Integer.parseInt(ele.attr("data-parentpostingid"));

			// Unix timestamp:
			date = new Date((long) Long.parseLong(ele
					.getElementsByClass("timestamp").first()
					.attr("data-livestamp")) * 1000);

			red = Integer.parseInt(ele.select("span.counter.n").text());
			green = Integer.parseInt(ele.select("span.counter.p").text());

			title = ele.getElementsByClass("posting-content").first()
					.select("h4").text();

			content = ele.getElementsByClass("posting-content").first()
					.select("p").text();

			Comment c = new Comment();
			/*c.setId(id);
			c.setUsername(username);
			c.setUserid(userid);
			c.setParentid(parentid);
			c.setDate(date);
			c.setRed(red);
			c.setGreen(green);
			c.setTitle(title);
			c.setContent(content);*/

			comments.add(c);

		}

		return comments;
	}

	/**
	 * Converts the string format of the dateTime into a Date object. Not needed
	 * anymore, as i found the unix timestamp in the source code.
	 * 
	 * @param str
	 * @return
	 */
	private static Date convertDateSeconds(String str) {
		Date date = null;

		// Example: 19. März 2015, 14:27:05

		DateFormat format = new SimpleDateFormat("dd. MMMM yyyy, HH:mm:ss",
				Locale.GERMAN);
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			System.out.println("Error while parsing date time. Exception: "
					+ e.getMessage());
		}

		return date;
	}

	private static Date convertDate(String str) {
		Date date = null;

		// Example: 19. März 2015, 14:27:05

		DateFormat format = new SimpleDateFormat("dd. MMMM yyyy, HH:mm",
				Locale.GERMAN);
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			System.out.println("Error while parsing date time. Exception: "
					+ e.getMessage());
		}

		return date;
	}
}
