create table article (
  article_id                integer auto_increment not null,
  article_ext_id            varchar(255),
  title                     varchar(255),
  text                      text,
  date                      datetime,
  category                  varchar(255),
  sub_category              varchar(255),
  image_url                 varchar(255),
  url                       varchar(255),
  parse_date                datetime,
  constraint pk_article primary key (article_id))
;

create table comment (
  comment_id                integer auto_increment not null,
  comment_ext_id            varchar(255),
  article_id                integer,
  comment_title             varchar(255),
  comment_text              text,
  comment_date              datetime,
  up_votes                  integer,
  down_votes                integer,
  sentiment                 integer,
  quality_score             float,
  parent_id                 integer,
  user_id                   integer,
  constraint ck_comment_sentiment check (sentiment in (0,1,2)),
  constraint pk_comment primary key (comment_id))
;

create table user (
  user_id                   integer auto_increment not null,
  user_ext_id               varchar(255),
  username                  varchar(255),
  constraint pk_user primary key (user_id))
;

create table user_stats (
  user_id                   integer auto_increment not null,
  num_comments              integer,
  avg_quality_score         float,
  avg_sentiment_score       float,
  avg_rating                float,
  num_replies               integer,
  constraint uq_user_stats_user_id unique (user_id),
  constraint pk_user_stats primary key (user_id))
;

alter table comment add constraint fk_comment_article_1 foreign key (article_id) references article (article_id) on delete restrict on update restrict;
create index ix_comment_article_1 on comment (article_id);
alter table comment add constraint fk_comment_parent_2 foreign key (parent_id) references comment (comment_id) on delete restrict on update restrict;
create index ix_comment_parent_2 on comment (parent_id);
alter table comment add constraint fk_comment_user_3 foreign key (user_id) references user (user_id) on delete restrict on update restrict;
create index ix_comment_user_3 on comment (user_id);
alter table user_stats add constraint fk_user_stats_user_4 foreign key (user_id) references user (user_id) on delete restrict on update restrict;
create index ix_user_stats_user_4 on user_stats (user_id);


