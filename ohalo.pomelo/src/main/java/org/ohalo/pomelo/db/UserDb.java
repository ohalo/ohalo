package org.ohalo.pomelo.db;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.db.page.Pagination;
import org.ohalo.pomelo.entity.UserInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 用户数据采集
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class UserDb extends MongoHaloDBImpl<UserInfo> {
	@Override
	protected Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}

	@Override
	public void update(UserInfo user) {
		this.update(Query.query(Criteria.where("uid").is(user.getUid())),
				toUpdateMap(user.toMap()));
	}

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public UserInfo findById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", id);
		return this.findOne(toQueryMap(params));
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo findByuAccount(String uAccount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uAccount", uAccount);
		return this.findOne(toQueryMap(params));
	}

	/**
	 * 根据用户名和密码查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public UserInfo findByuAccountAuPwd(String uAccount, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uAccount", uAccount);
		params.put("uPassword", password);
		return this.findOne(toQueryMap(params));
	}

	public Pagination<UserInfo> queryAllUserByLimit(UserInfo userInfo , int start, int limit) {
		Sort sort = new Sort(Direction.ASC, "createTime");
		return this.getPage(start, limit, toQueryMap(userInfo.toMap()).with(sort));
	}
	
}