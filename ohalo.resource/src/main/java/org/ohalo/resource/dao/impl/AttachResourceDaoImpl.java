package org.ohalo.resource.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ohalo.db.impl.MongoHaloDBImpl;
import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.dao.IAttachResourceDao;
import org.ohalo.resource.pojo.AttachResource;

public class AttachResourceDaoImpl extends MongoHaloDBImpl<AttachResource>
		implements IAttachResourceDao {

	@Override
	public void insert(AttachResource resource) {
		super.save(resource);
	}

	@Override
	public AttachResource queryFile(String fileId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", fileId);
		return this.findOne(toQueryMap(params));
	}

	@Override
	public List<AttachResource> query(QueryData queryData, int start, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", queryData.getFileId());
		params.put("attachguid", queryData.getAttachguid());

		List<AttachResource> resources = this.queryByParamsLimit(params, start,
				limit).getDatas();
		if (resources != null) {
			return resources;
		}

		return new ArrayList<AttachResource>();
	}

	@Override
	public List<AttachResource> query(QueryData queryData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", queryData.getFileId());
		params.put("attachguid", queryData.getAttachguid());

		List<AttachResource> resources = this.queryByParams(params);
		if (resources != null) {
			return resources;
		}

		return new ArrayList<AttachResource>();
	}

	@Override
	public long count(QueryData queryData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", queryData.getFileId());
		params.put("attachguid", queryData.getAttachguid());
		return this.mongoTemplate.count(toQueryMap(params),
				this.getEntityClass());
	}

	@Override
	public void deleteByGuid(String attachGuid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attachguid", attachGuid);
		this.delete(params);
	}

	@Override
	public void deleteById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fid", id);
		this.delete(params);
	}

	@Override
	protected Class<AttachResource> getEntityClass() {
		return AttachResource.class;
	}

}
