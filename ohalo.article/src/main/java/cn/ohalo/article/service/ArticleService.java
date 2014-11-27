package cn.ohalo.article.service;

import cn.ohalo.article.db.ArticleDB;
import cn.ohalo.article.entity.Article;
import cn.ohalo.service.impl.BaseServiceImpl;

/**
 * 
 * @author zhaohl
 *
 */
public class ArticleService extends BaseServiceImpl<Article> {

	private ArticleDB articleDb;

	private static ArticleService service;

	public static ArticleService getInstance() {
		if (service == null) {
			service = new ArticleService();
		}
		return service;
	}

	private ArticleService() {
		articleDb = ArticleDB.getInstance();
		articleDb.setCollectionName("cn.ohalo.article");
		setBaseDb(articleDb);
	}

}
