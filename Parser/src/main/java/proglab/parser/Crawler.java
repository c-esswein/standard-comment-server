package proglab.parser;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;

public class Crawler {

	private ORM orm;

	public Crawler() {
		orm = ORM.getInstance();
	}

	public void Crawl() {
		try {
			String url = Downloader.GenerateUrlToMain();
			HtmlParser parser = new HtmlParser(Downloader.Download(url));
			List<String> categories = parser.getCategories();

			for (String category : categories) {
				try {
					url = Downloader.GenerateUrlToCategory(category);
					parser = new HtmlParser(Downloader.Download(url));
					List<Long> articles = parser.getArticles();
					int i = 1;
					for (Long articleId : articles) {
						Logging.Log("Working on article " + (i++) + " of "
								+ articles.size() + " of category: " + category);
						try {
							storeArticle(articleId);
						} catch (Exception e) {
							Logging.Log("Error while parsing article "
									+ articleId + ": " + e.getMessage());
						}
					}
				} catch (Exception e) {
					Logging.Log("Error while parsing category: "
							+ e.getMessage());
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
			orm.save(a);

			for (Comment c : parser.getAllComments(articleId, pageCount)) {
				try {
					a.addComment(c);
					storeComment(c);
				} catch (Exception e) {
					Logging.Log("Failed to store comment: " + c.getExtId()+". Exception: " + e.getMessage());
				}
			}
			orm.save(a);

		} catch (Exception e) {
			Logging.Log(e.getMessage());
		}
	}

	/*
	 * Stores a comment (recursively, starting with parent)
	 */
	private void storeComment(Comment c) {
		if (c.getParent() != null)
			storeComment(c.getParent());

		orm.save(c);
	}
}
