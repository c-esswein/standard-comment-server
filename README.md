# standard-comment-server
Server for analyzing comments on news platforms

# DBConn
Object Relational Mapper for MySQL.

## Usage
Import `DBConn` as Eclipse project and run `Update Project`from the `Maven` menu. This will download all required dependencies. Then add `DBConn` project as requirement to your Java Build Path.

Create an article:
```java
ORM orm = ORM.getInstance();
Article a = new Article();
a.setTitle("Testartikel");
a.setText("Lorem Ipsum ...");
// a.set...
orm.save(a);      // save to db
```

Add a comment to an article: *(Article must be saved before adding a comment, otherwise it will fail because of foreign key constraints)*
```java
ORM orm = ORM.getInstance();
Comment c = new Comment();
c.setTitle("Kommentar 1");
c.setText("Kommentartext");
// c.set...
a.addComment(c);        // alternative: c.setArticle(a);
c.setUser("user 1");    // user will be fetched or created
orm.save(c);            // save to db
```

Create tree structure of comments: *(Parent comment must be saved before saving a child comment, otherwise it will fail because of foreign key constraints)*
```java
ORM orm = ORM.getInstance();
Comment c1 = new Comment();
Comment c2 = new Comment();
c1.addComment(c2);    // alternative: c2.setParent(c1);
orm.save(c1);         // save to db
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
