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

public class HtmlParser {

	private Document doc;

	public HtmlParser(String html) {
		doc = Jsoup.parse(html);
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

			System.out.println("" + id + "[Parent: " + parentid + "]__(" + red
					+ ":" + green + ")__" + username + "_" + userid + "_"
					+ date + "___" + title + "_______" + content);
		}

		return comments;
	}

	private static Date convertDate(String str) {
		Date date = null;

		// Example: 19. März 2015, 14:27:05

		DateFormat format = new SimpleDateFormat("dd. MMMM yyyy, HH:mm:ss");
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			System.out.println("Error while parsing date time. Exception: "
					+ e.getMessage());
		}

		return date;

	}

}
