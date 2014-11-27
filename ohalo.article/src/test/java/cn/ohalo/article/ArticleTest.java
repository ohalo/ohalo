package cn.ohalo.article;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import cn.ohalo.article.entity.Article;
import cn.ohalo.article.entity.ArticleCategory;
import cn.ohalo.article.entity.Tag;
import cn.ohalo.article.service.ArticleService;

public class ArticleTest extends TestCase {

	private ArticleService service;

	@Override
	protected void setUp() throws Exception {
		service = ArticleService.getInstance();
	}

	public void testAdd() {

		Article article = new Article();

		ArticleCategory ac = new ArticleCategory();

		ac.setCode("news");
		ac.setName("新闻动态");

		ac.setParentCode("top");

		article.setArticleCategory(ac);
		article.setAuthor("张三");
		article.setContent("你好，你是第一个人");
		article.setTitle("一个人");

		List<Tag> tags = new ArrayList<Tag>();
		Tag tag = new Tag();
		tag.setMemo("你好");
		tag.setName("你好");

		tags.add(tag);

		article.setTags(tags);

		System.out.println(article.get_id());

		service.removeAll();

		service.saveOrUpdate(article);

		System.out.println(service.queryById(article.get_id()).getContent());
	}

}
