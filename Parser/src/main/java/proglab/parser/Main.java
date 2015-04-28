package proglab.parser;

import proglab.dbconn.bean.Article;
import proglab.dbconn.bean.Comment;
import proglab.dbconn.orm.ORM;

public class Main {

	public static void main(String[] args) {

		ORM orm = ORM.getInstance();
		
		Article a = new Article();
		a.setExtId("123");
		
		orm.save(a);
		
		Comment c = new Comment();
		
		
		
		/*
		Logging.Log("Crawler started.");
		try {
			Crawler c;
			
			if (args.length == 0)
				c = new Crawler();
			else
				c = new Crawler(args[0]);
			
			c.Crawl();
		} catch (Exception e) {
			Logging.Log("General Exception: " + e.getMessage());
		}
		Logging.Log("Crawler finished.");*/
	}
}
