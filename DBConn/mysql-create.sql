CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_ext_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `sub_category` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parse_date` datetime DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE utf8_unicode_ci;

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_ext_id` varchar(255) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  `comment_title` varchar(255) DEFAULT NULL,
  `comment_text` text DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  `up_votes` int(11) DEFAULT NULL,
  `down_votes` int(11) DEFAULT NULL,
  `sentiment_score` float DEFAULT NULL,
  `quality_score` float DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `ix_comment_article_1` (`article_id`),
  KEY `ix_comment_parent_2` (`parent_id`),
  KEY `ix_comment_user_3` (`user_id`),
  CONSTRAINT `fk_comment_article_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON UPDATE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_parent_2` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`comment_id`) ON UPDATE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_user_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE utf8_unicode_ci;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_ext_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE utf8_unicode_ci;
