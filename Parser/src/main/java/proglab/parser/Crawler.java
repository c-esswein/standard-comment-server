package proglab.parser;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;

public class Crawler {

	public void Crawl() {
		try {
			String url = Downloader.GenerateUrlToMain();
			HtmlParser parser = new HtmlParser(Downloader.Download(url));
			List<String> categories = parser.getCategories();

			for (String category : categories) {
				url = Downloader.GenerateUrlToCategory(category);
				parser = new HtmlParser(Downloader.Download(url));
				List<Long> articles = parser.getArticles();
				int i = 1;
				for (Long articleId : articles) {
					Logging.Log("Working on article " + (i++) + " of "
							+ articles.size() + " of category: " + category);
					
					storeArticle(articleId);
				}
			}
		} catch (Exception e) {
			Logging.Log("Something happened: " + e.getMessage());
		}
	}

	/**
	 * Gets all information for an article, adds comments and stores it in the
	 * database
	 * 
	 * @param articleId
	 */
	public void storeArticle(long articleId) {

		try {
			String url = Downloader.GenerateUrlToArticle(articleId);
			HtmlParser parser = new HtmlParser(Downloader.Download(url),
					articleId);
			url = Downloader.GenerateUrlToComments(articleId);
			int pageCount = (new HtmlParser(Downloader.Download(url), articleId))
					.GetPagecount();

			Article a = parser.GetArticle();
			ORM.save(a);

			for (Comment c : parser.getAllComments(articleId, pageCount)) {
				a.addComment(c);
				storeComment(c);
			}
			ORM.save(a);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Stores a comment (recursively, starting with parent)
	 */
	private void storeComment(Comment c) {
		if (c.getParent() != null)
			storeComment(c.getParent());

		ORM.save(c);
	}
}
