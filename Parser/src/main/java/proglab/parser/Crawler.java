package proglab.parser;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

	/**
	 * Gets all information of the article
	 * 
	 * @param articleId
	 */
	public void getArticle(long articleId) {
		String url = Downloader.GenerateUrlToArticle(articleId);
		try {
			HtmlParser parser = new HtmlParser(Downloader.Download(url),
					articleId);
			
			Article a = parser.GetArticle();
			a=a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets all comments of an article (crawls all pages of paginator)
	 * 
	 * @param articleId
	 */
	public void getAllComments(long articleId) {

		// url to comments. Needed to get the number of pages
		String url = Downloader.GenerateUrlToComments(articleId);
		try {
			int pageCount = (new HtmlParser(Downloader.Download(url), articleId))
					.GetPagecount();
			int commentCount = 0;
			List<Comment> comments = new ArrayList<Comment>();

			for (int i = 1; i <= pageCount; i++) {
				url = Downloader.GenerateUrlToComments(articleId, i);
				HtmlParser parser = new HtmlParser(Downloader.Download(url),
						articleId);

				List<Comment> tmp = parser.GetComments();
				comments.addAll(tmp);
				commentCount += tmp.size();
				System.out.println("Page: " + i);
			}
			System.out.println("Got " + commentCount + " comments.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
