package cn.ohalo.interview.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.interview.db.InterViewDB;
import cn.ohalo.interview.entity.InterViewInfo;
import cn.ohalo.service.impl.BaseServiceImpl;
import cn.ohalo.utils.Guid;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 面试信息
 * 
 * @author halo
 * 
 */
public class InterViewService extends BaseServiceImpl<InterViewInfo> {

	private static InterViewService service;

	private InterViewDB interViewDb;

	public static InterViewService getInstance() {
		if (service == null) {
			service = new InterViewService();
		}
		return service;
	}

	private InterViewService() {
		interViewDb = InterViewDB.getInstance();
		interViewDb.setCollectionName("cn.ohalo.interView");
		setBaseDb(interViewDb);
	}

	public boolean insert(InterViewInfo info) {
		if (StringUtils.isBlank(info.getCode())) {
			info.setCode(Guid.newDCGuid("iterview"));
		}
		info.setCreateDate(new Date());
		return interViewDb.insert(info.toDBObject());
	}

	public boolean saveOrUpdate(InterViewInfo info) {
		if (StringUtils.isNotBlank(info.getCode())) {
			return update(info);
		} else {
			return insert(info);
		}
	}

	public boolean update(InterViewInfo info) {
		DBObject queryParams = new BasicDBObject("code", info.getCode());
		return interViewDb.update(queryParams, info.toDBObject());
	}

}
