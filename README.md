# standard-comment-server
Server for analyzing comments on news platforms

# DBConn
Object Relational Mapper for MySQL.

## Usage
Import `DBConn` as Eclipse project and run `Update Project`from the `Maven` menu. This will download all required dependencies. Then add `DBConn` project as requirement to your Java Build Path.

Complete example:
```java
ORM orm = ORM.getInstance();

// create or retrieve bean with given external id
Article a1 = orm.createArticle("a1");
Comment c1 = orm.createComment("c1");	
Comment c2 = orm.createComment("c2");

// add comments to article
a1.addComment(c1);
a1.addComment(c2);
	
// set c1 as parent of c2
c2.setParent(c1);
	
// set users
c1.setUser("test3");
c2.setUser("test2");
		
//c1.set...();
//c2.set...();
		
// save article before saving comments
orm.save(a1);

// save parent comment before child comments
orm.save(c1);
orm.save(c2);
```

Retrieve comments without sentiment: get 100 comments repeatedly as long as no one without sentiment is left
```java
ORM orm = ORM.getInstance();
List<Comment> commentsWithoutSentiment;
do {
	commentsWithoutSentiment = orm.getCommentsWithoutSentiment(100);
	for (Comment c : commentsWithoutSentiment) {
		// sentiment analysis
		// c.setSentiment(...);
		orm.save(c);
	}
} while (commentsWithoutSentiment.size() > 0);
```

Do the same with `orm.getCommentsWithoutQualityScore(rows)` to get comments without quality score.
