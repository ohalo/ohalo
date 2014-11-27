package org.ohalo.favorite.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohalo.base.action.BaseController;
import org.ohalo.base.data.Result;
import org.ohalo.base.utils.Guid;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.favorite.db.ZContentDb;
import org.ohalo.favorite.entity.ZContentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author halo
 * @since 2013-8-18 上午10:32:08
 */
@Controller
public class ZContentAction extends BaseController {

	@Autowired
	private ZContentDb contentManager;

	/**
	 * 添加标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addcontent")
	public String addcontent(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String cname,
			@RequestParam String remark, @RequestParam String linkInAddress,
			@RequestParam String tagCode) {
		ZContentEntity content = mergerParams(Guid.newDCGuid("OHALO_FAVORITE"),
				cname, remark, linkInAddress, tagCode);
		try {
			contentManager.save(content);
		} catch (Exception e) {
			LogUtils.errorMsg("ZContentAction", "addcontent", "标签保存失败!", e);
			returnDBError(content, response);
		}
		returnSuccess(content, "保存标签到数据库成功!", response);
		return null;
	}

	/**
	 * 合并参数信息
	 * 
	 * @param id
	 * @param cname
	 * @param remark
	 * @param linkInAddress
	 * @return
	 */
	private ZContentEntity mergerParams(String id, String cname, String remark,
			String linkInAddress, String tagCode) {
		ZContentEntity content = new ZContentEntity(cname, remark,
				linkInAddress, tagCode);
		content.setId(id);
		return content;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：合并参数信息
	 * 作者：赵辉亮
	 * 日期：2013-8-22
	 * </pre>
	 * 
	 * @param id
	 * @param cname
	 * @param remark
	 * @param linkInAddress
	 * @return
	 */
	private Map<String, Object> mergerParamsToMap(String id, String cname,
			String remark, String linkInAddress, String tagCode) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("cname", cname);
		params.put("remark", remark);
		params.put("linkInAddress", linkInAddress);
		params.put("tagCode", tagCode);

		return params;
	}

	/**
	 * 更新标签
	 * 
	 * @param content
	 * @param map
	 * @return
	 * @throws HaloDBException
	 */
	@RequestMapping(value = "/updatecontent")
	public String updatecontent(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id,
			@RequestParam String cname, @RequestParam String remark,
			@RequestParam String linkInAddress, @RequestParam String tagCode) {
		ZContentEntity content = mergerParams(id, cname, remark, linkInAddress,
				tagCode);
		try {
			contentManager
					.update(content.getId(),
							mergerParamsToMap(id, cname, remark, linkInAddress,
									tagCode));
		} catch (Exception e) {
			LogUtils.errorMsg("ZContentAction", "updatecontent", "更新标签失败!", e);
			returnDBError(content, response);
		}
		returnSuccess(content, "更新标签成功", response);
		return null;
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delcontent")
	public String delcontent(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id) {

		try {
			contentManager.delete(id);
		} catch (Exception e) {
			LogUtils.errorMsg("ZContentAction", "delcontent", "删除标签失败,删除id为："
					+ id, e);
			returnDBError(new Result(), response);
		}
		returnSuccess(new Result(), "删除标签成功，删除id为：" + id, response);
		return null;
	}

	/**
	 * 查询标签信息
	 * 
	 * @param content
	 *            查询标签参数信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/searchcontents")
	public String querycontent(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id,
			@RequestParam String cname, @RequestParam String remark,
			@RequestParam String linkInAddress, @RequestParam String tagCode) {

		ZContentEntity content = mergerParams(id, cname, remark, linkInAddress,
				tagCode);
		List<ZContentEntity> contentList = null;
		try {
			contentList = contentManager.queryByParams(mergerParamsToMap(id,
					cname, remark, linkInAddress, tagCode));
		} catch (Exception e) {
			LogUtils.errorMsg("ZContentAction", "querycontent", "查询标签失败!", e);
			returnDBError(content, response);
		}
		returnSuccess(contentList, "查询成功!", response);
		return null;
	}

}
