package cn.ohalo.article.db;

import cn.ohalo.article.entity.Article;
import cn.ohalo.db.mongodb.BaseDb;

public class ArticleDB extends BaseDb<Article> {

	private static ArticleDB articleDb;

	public static ArticleDB getInstance() {
		if (articleDb == null) {
			articleDb = new ArticleDB();
		}

		return articleDb;
	}
}
