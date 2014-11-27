package org.ohalo.resource.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.dao.IAttachResourceDao;
import org.ohalo.resource.pojo.AttachResource;
import org.ohalo.resource.service.IAttachmentService;
import org.springframework.stereotype.Service;

@Service("attachService")
public class AttachService implements IAttachmentService {

	@Resource(name = "resourcedb")
	private IAttachResourceDao attachDao;

	@Override
	public void insertAttach(AttachResource attach) {
		attachDao.insert(attach);
	}

	@Override
	public List<String> queryAttachSizes(String attachRelID) {
		return null;
	}

	@Override
	public void updateAttach(String newAttachGuid, String oldAttachGuid) {

	}

	@Override
	public List<AttachResource> queryAttachResources(QueryData queryData) {
		return attachDao.query(queryData);
	}

	@Override
	public void deleteAttachById(String fileID) {
		attachDao.deleteById(fileID);
	}

	@Override
	public void deleteAttachByRelID(String attachRelID) {
		attachDao.deleteByGuid(attachRelID);
	}
}