package org.ohalo.resource.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohalo.base.action.BaseController;
import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;
import org.ohalo.resource.service.IResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
@RequestMapping(value = "/rattach")
@Controller("hresourceAction")
public class ResourceAction extends BaseController {

	/**
	 * @Fields attachmentService : 附件服务service层接口
	 */
	@Resource(name = "resourceService")
	private IResourceService resourceService;
	
	public String toDoSearchView(){
		return null;
	}

	/**
	 * 
	 * 分页查询附件信息
	 * 
	 * @param request
	 * @param response
	 * @param relUid
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryAttachPage")
	public String queryAttachPage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "relUid", required = false) String relUid,
			@RequestParam(value = "start", required = true) Integer start,
			@RequestParam(value = "limit", required = true) Integer limit) {

		QueryData data = new QueryData();
		data.setAttachguid(relUid);

		// 根据起始参数和结束参数查询附件资源信息
		List<AttachResource> resourceList = resourceService.query(data, start,
				limit);
		// 设置资源查询total
		// Long totalCount = resourceService.count(data);
		returnSuccess(resourceList, "查询成功!", response);
		return null;
	}
}