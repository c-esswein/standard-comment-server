package proglab.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.bean.User;
import proglab.dbconn.orm.ORM;

public class HtmlParser {

	private Document doc;
	private DownloadResponse downloadResponse;
	private long article_ext_id;

	public HtmlParser(DownloadResponse _response) {

		downloadResponse = _response;
		doc = Jsoup.parse(_response.getHtml());
		Charset s = Charset.forName("UTF-8");
		doc.outputSettings().charset(s);
	}

	public HtmlParser(DownloadResponse _response, long articleId) {

		downloadResponse = _response;
		doc = Jsoup.parse(_response.getHtml());
		Charset s = Charset.forName("UTF-8");
		doc.outputSettings().charset(s);
		this.article_ext_id = articleId;
	}

	public Article GetArticle() {
		String title = doc.getElementById("content-header").select("h1").text();
		Date date = convertDate(doc.getElementById("content-header")
				.select("span.date").text());

		String text = doc.getElementsByClass("copytext").toString();

		String imgurl = "";
		if (doc.getElementsByClass("zoom").size() > 0)
			imgurl = doc.getElementsByClass("zoom").first().attr("href");

		Element nav = doc.getElementById("navLine1");
		nav = nav.select("ul").first();
		nav = nav.getElementsByClass("active").first();
		String category = nav.select("a").first().text();
		nav = nav.select("ul").first();
		nav = nav.getElementsByClass("active").first();
		String subCategory = nav.select("a").first().text();

		Article a = new Article();

		a.setTitle(title);
		a.setDate(date);
		a.setParseDate(new Date());
		a.setUrl(downloadResponse.getUrl());
		a.setExtId(Long.toString(article_ext_id));
		a.setText(text);
		a.setCategory(category);
		a.setSubCategory(subCategory);

		if (!imgurl.equals(""))
			a.setImageUrl(imgurl);

		return a;
	}

	public int GetPagecount() {
		Element buttonLast = doc.select("button.std-button.last").first();

		if(doc.select("p.no-postings.context").size()>0)
			return 0;
		
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

	/**
	 * Gets all comments of an article (crawls all pages of paginator)
	 * 
	 * @param articleId
	 */
	public List<Comment> getAllComments(long articleId, int pageCount) {

		// url to comments. Needed to get the number of pages

		Map<Integer, Comment> comments = new HashMap<Integer, Comment>();

		try {
			int commentCount = 0;

			for (int i = 1; i <= pageCount; i++) {
				String url = Downloader.GenerateUrlToComments(articleId, i);
				HtmlParser parser = new HtmlParser(Downloader.Download(url),
						articleId);

				parser.AddComments(comments);

				Logging.Log("Page: " + i);
			}

		} catch (IOException e) {
			Logging.Log("Error while getting comments. Exception: "
					+ e.getMessage());
		}

		return new ArrayList<Comment>(comments.values());
	}

	/**
	 * Gets all comments from one page
	 * 
	 * @return
	 */
	private void AddComments(Map<Integer, Comment> comments) {

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

			// Create an object
			Comment c = new Comment();
			c.setExtId(Integer.toString(id));

			User u = new User();
			u.setExtId(Integer.toString(userid));
			u.setUsername(username);
			c.setUser(u);

			c.setDate(date);
			c.setUpVotes(green);
			c.setDownVotes(red);
			c.setTitle(title);
			c.setText(content);

			if (parentid != 0 && comments.containsKey(parentid)) {
				Comment parent = comments.get(parentid);
				c.setParent(parent);
			}

			comments.put(id, c);
		}
	}

	public List<String> getCategories() {
		List<String> list = new ArrayList<String>();
		List<String> blacklist = new ArrayList<String>();
		blacklist.add("/DepotPortfolio");
		blacklist.add("/Sporttabellen");
		blacklist.add("/Wetter");
		blacklist.add("/r2140/TV-Programm-Switchlist");
		blacklist.add("/r2000013759905/Medien-Blogs");
		blacklist.add("/Kino");
		blacklist.add("/r1330390480903/Reiseblogs");
		blacklist.add("/r1229777262316/Raetsel--Sudoku");
		blacklist.add("/r1330390480903/Reiseblogs");
		blacklist.add("/1342947485683/Schulferien-Oesterreich");

		Element nav = doc.getElementById("navLine1");
		nav = nav.select("ul").first();

		for (Element element : nav.children()) {
			Element tmp1 = element.select("ul").first();

			if(element.select("a.hidden").size()>0)
				continue;
			

			if (tmp1.getElementsByClass("nav_empty").size() > 0) {
				list.add(element.select("a").first().attr("href"));
			} else {
				for (Element tmp2 : tmp1.select("li")) {
					String link = tmp2.select("a").first().attr("href").trim();
					if (!link.contains(".aspx") && !blacklist.contains(link))
						list.add(link);
				}
			}

		}

		return list;
	}
	
	public List<Long> getArticles() {
		List<Long> list = new ArrayList<Long>();
		
		Element element = doc.getElementById("mainContent");
		element=element.select("ul.stories").first();
		
		for(Element ele : element.children())
		{
			list.add(Long.parseLong(ele.attr("id").replace("_", "")));
		}
		
		
		return list;		
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
			Logging.Log("Error while parsing date time. Exception: "
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
			Logging.Log("Error while parsing date time. Exception: "
					+ e.getMessage());
		}

		return date;
	}
}
