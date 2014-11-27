package org.ohalo.resource.service.impl;

import java.util.List;

import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.dao.IAttachResourceDao;
import org.ohalo.resource.pojo.AttachResource;
import org.ohalo.resource.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceService implements IResourceService {

	@Autowired
	private IAttachResourceDao resourceDao;

	@Override
	public List<AttachResource> query(QueryData queryData, int start, int limit) {
		return resourceDao.query(queryData, start, limit);
	}

	@Override
	public AttachResource queryFile(String fileId) {
		return resourceDao.queryFile(fileId);
	}

	@Override
	public long count(QueryData queryData) {
		return resourceDao.count(queryData);
	}

}
