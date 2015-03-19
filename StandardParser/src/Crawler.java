import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

	/**
	 * Gets all comments of an article (crawls all pages of paginator)
	 * 
	 * @param forumKeyId
	 */
	public void getAllComments(long forumKeyId) {

		// url to comments. Needed to get the number of pages
		String url = Downloader.GenerateUrl(forumKeyId);
		try {
			int pageCount = (new HtmlParser(Downloader.GetHtml(url)))
					.GetPagecount();
			int commentCount = 0;
			List<Comment> comments = new ArrayList<Comment>();

			for (int i = 1; i <= pageCount; i++) {
				url = Downloader.GenerateUrl(forumKeyId, i);
				HtmlParser parser = new HtmlParser(Downloader.GetHtml(url));
				comments.addAll(parser.GetComments());
				commentCount += comments.size();
				System.out.println("Page: " + i);
			}
			System.out.println("Got " + commentCount + " comments.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
