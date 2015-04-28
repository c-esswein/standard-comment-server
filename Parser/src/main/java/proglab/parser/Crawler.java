package proglab.parser;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;

public class Crawler {

	private ORM orm;
	private String startCategory = null;
	private Boolean startCategoryFound = true;

	public Crawler() {
		orm = ORM.getInstance();
	}

	public Crawler(String _startCategory) {
		orm = ORM.getInstance();
		startCategory = _startCategory;
		startCategoryFound = false;
	}

	public void Crawl() {
		try {
			String url = Downloader.GenerateUrlToMain();
			HtmlParser parser = new HtmlParser(Downloader.Download(url));
			List<String> categories = parser.getCategories();

			for (String category : categories) {
				try {
					
					if(category.equals(startCategory))
						startCategoryFound=true;
					
					if(!startCategoryFound)
						continue;
					
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

	List<Comment> toStore = new ArrayList<Comment>();

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

			toStore = parser.getAllComments(articleId, pageCount);
			List<Comment> tmpComments = new ArrayList<Comment>(toStore);

			Logging.Log("Article stored. Storing comments.");
			for (Comment c : tmpComments) {
				try {
					a.addComment(c);
					storeComment(c);
				} catch (Exception e) {
					Logging.Log("Failed to store comment: " + c.getExtId()
							+ ". Exception: " + e.getMessage()+"\n\n\n"+
							c.toString()
							);
				}
			}
			

		} catch (Exception e) {
			Logging.Log("Error while storing article. Exception: " + e.getMessage());
		}
	}

	/*
	 * Stores a comment (recursively, starting with parent)
	 */
	private void storeComment(Comment c) {
		if (c.getParent() != null)
			storeComment(c.getParent());

		if (toStore.contains(c)) {
			toStore.remove(c);
			orm.save(c);
		}
	}
}
