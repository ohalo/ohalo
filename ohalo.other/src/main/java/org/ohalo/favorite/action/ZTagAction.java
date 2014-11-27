package org.ohalo.favorite.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohalo.base.action.BaseController;
import org.ohalo.base.data.Result;
import org.ohalo.base.node.TreeNode;
import org.ohalo.base.node.TreeNodeList;
import org.ohalo.base.node.TreeNodeSet;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.db.IHaloDB;
import org.ohalo.favorite.db.ZTagDb;
import org.ohalo.favorite.entity.ZTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 创建标签action 类
 * 
 * @author halo
 * 
 */
@Controller
public class ZTagAction extends BaseController {

	@Autowired
	private ZTagDb tagManager;

	/**
	 * <pre>
	 * 添加标签到数据库中 
	 * 	标签是收藏夹的简单分类命名 
	 * 	根标签下有自标签，子标签下，
	 *  有子标签 标签下面存放有收藏地址，和一些描述信息
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param tagCode
	 * @param tagName
	 * @param tagParentCode
	 * @param tagDesc
	 * @return
	 * 
	 * @author halo
	 * @see IHaloDB#save(Object)
	 */
	@RequestMapping(value = "/addTag")
	public String addTag(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String tagCode,
			@RequestParam String tagName, @RequestParam String tagParentCode,
			@RequestParam String tagDesc) {
		ZTagEntity tag = mergerParams(tagCode, tagName, tagParentCode, tagDesc);
		try {
			tagManager.save(tag);
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "addTag", "标签保存失败!", e);
			returnDBError(tag, response);
		}
		returnSuccess(tag, "保存标签到数据库成功!", response);
		return null;
	}

	/**
	 * 合并参数
	 * 
	 * @param tagCode
	 *            标签编码
	 * @param tagName
	 *            标签名称
	 * @param tagParentCode
	 *            父标签编码
	 * @param tagDesc
	 *            标签描述信息
	 * @return
	 */
	private ZTagEntity mergerParams(String tagCode, String tagName,
			String tagParentCode, String tagDesc) {
		ZTagEntity tag = new ZTagEntity(tagCode, tagName, tagParentCode,
				tagDesc);
		tag.setId(tagCode);
		LogUtils.infoMsg(this.getClass().getName(), "mergerParams", "拼接参数内容为："
				+ tag.toString());
		return tag;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：封装属性到map中
	 * 作者：赵辉亮
	 * 日期：2013-8-22
	 * </pre>
	 * 
	 * @param id
	 * @param tagCode
	 * @param tagName
	 * @param tagParentCode
	 * @param tagDesc
	 * @return
	 */
	private Map<String, Object> mergerParamsToMap(String tagCode,
			String tagName, String tagParentCode, String tagDesc) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("id", tagCode);
		params.put("tagCode", tagCode);
		params.put("tagName", tagName);
		params.put("tagParentCode", tagParentCode);
		params.put("tagDesc", tagDesc);

		return params;

	}

	/**
	 * 跳转到添加标签页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTagPage")
	public String jumpAddTagPage() {
		LogUtils.infoMsg(ZTagAction.class.getName(), "jumpAddTagPage",
				"添加标签页面!");
		return "addTag";
	}

	/**
	 * 跳转到添加标签页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String jumpIndex() {
		LogUtils.infoMsg(ZTagAction.class.getName(), "index", "跳转到index页面!");
		return "index";
	}

	/**
	 * 更新标签
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param tagCode
	 * @param tagName
	 * @param tagParentCode
	 * @param tagDesc
	 * @return
	 */
	@RequestMapping(value = "/updateTag")
	public String updateTag(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String tagCode,
			@RequestParam String tagName, @RequestParam String tagParentCode,
			@RequestParam String tagDesc) {
		ZTagEntity tag = mergerParams(tagCode, tagName, tagParentCode, tagDesc);
		try {
			tagManager
					.update(tagCode,
							mergerParamsToMap(tagCode, tagName, tagParentCode,
									tagDesc));
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "updateTag", "更新标签失败!", e);
			returnDBError(tag, response);
		}
		returnSuccess(tag, "更新标签成功", response);
		return null;
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delTag")
	public String delTag(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String tagCode) {
		try {
			tagManager.delete(tagCode);
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "delTag",
					"删除标签失败,删除id为：" + tagCode, e);
			returnDBError(new Result(), response);
		}
		returnSuccess(new Result(), "删除标签成功，删除id为：" + tagCode, response);
		return null;
	}

	/**
	 * 
	 * 查询标签信息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param tagCode
	 * @param tagName
	 * @param tagParentCode
	 * @param tagDesc
	 * @return
	 */
	@RequestMapping(value = "/searchTags")
	public String queryTag(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id,
			@RequestParam String tagCode, @RequestParam String tagName,
			@RequestParam String tagParentCode, @RequestParam String tagDesc) {
		ZTagEntity tag = mergerParams(tagCode, tagName, tagParentCode, tagDesc);
		List<ZTagEntity> tagList = null;
		try {
			tagList = tagManager.queryByParams(mergerParamsToMap(tagCode,
					tagName, tagParentCode, tagDesc));
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "queryTag", "查询标签失败!", e);
			returnDBError(tag, response);
		}
		returnSuccess(tagList, "查询成功!", response);
		return null;
	}

	@RequestMapping(value = "/queryJsonTreeTags")
	public String queryJsonTree(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String tagParentCode) {
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(getZtreeJson(tagParentCode));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/queryAllJsonTreeTags")
	public String queryJsonTree(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(getAllZtreeJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-9-9
	 * </pre>
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	private String getAllZtreeJson() {
		String ztreeJson = null;
		try {
			Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
			TreeNode root = new TreeNodeSet();
			root.setLeaf(false);
			root.setId("1");
			root.setName("我的收藏夹");
			root.setPid("0");
			List<ZTagEntity> list = tagManager.findAll();
			for (ZTagEntity zTagEntity : list) {
				TreeNode node = new TreeNodeList();
				node.setId(zTagEntity.getTagCode());
				node.setPid(zTagEntity.getTagParentCode());
				node.setName(zTagEntity.getTagName());
				nodeMap.put(zTagEntity.getId(), node);
			}

			for (Map.Entry<String, TreeNode> entry : nodeMap.entrySet()) {
				TreeNode node = entry.getValue();
				String pid = node.getPid();
				TreeNode pNode = nodeMap.get(pid);
				if (pNode == null) {
					root.addChildNode(node);
				} else {
					pNode.setLeaf(false);
					pNode.addChildNode(node);
				}
			}

			ztreeJson = new JSONObject().toJSON(root).toString();
			LogUtils.infoMsg(this.getClass().getName(), "getAllZtreeJson()",
					"ztreeJson格式为:" + ztreeJson);
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "getAllZtreeJson",
					"ztreeJson树转换异常!" + ztreeJson, e);
		}

		return ztreeJson;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：获取ztree's json ,根据parentId
	 * 作者：赵辉亮
	 * 日期：2013-9-9
	 * </pre>
	 * 
	 * @param parentId
	 * @return
	 */
	private String getZtreeJson(String parentId) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("tagParentCode", parentId);

		String tempRes = "[";

		List<ZTagEntity> list = null;

		try {
			TreeNode rootNode = new TreeNodeList();
			rootNode.setId("1");
			rootNode.setLeaf(false);
			rootNode.setLevel(0);
			rootNode.setPid("0");
			rootNode.setSortNo(0);
			// 把顶层的查出来
			list = tagManager.queryByParams(params);
			//
			for (int i = 0; i < list.size(); i++) {
				//
				ZTagEntity model = list.get(i);
				//
				tempRes += "{\"id\":\"" + model.getTagCode() + "\",\"pid\":\""
						+ model.getTagParentCode() + "\",\"name\":\""
						+ model.getTagName() + "\"";

				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("tagParentCode", model.getTagCode());

				// 遍历它的子节点
				List<ZTagEntity> sonList = tagManager.queryByParams(params1);

				if (sonList == null || sonList.isEmpty()) {
					tempRes += "},";
					continue;
				} else {
					tempRes += ",\"open\":true },";
				}

				for (ZTagEntity zTagEntity : sonList) {
					tempRes += "{\"id\":\"" + zTagEntity.getTagCode()
							+ "\",\"pid\":\"" + zTagEntity.getTagParentCode()
							+ "\",\"name\":\"" + zTagEntity.getTagName()
							+ "\" },";
				}
			}

			tempRes = tempRes.substring(0, tempRes.length() - 1);
			tempRes += "]";
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "getZtreeJson", "查询收藏夹标签出错!", e);
		}

		return tempRes;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
	 * 作者：赵辉亮
	 * 日期：2013-8-20
	 * </pre>
	 * 
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getJson(String parentId) {
		String tempRes = "[";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagParentCode", parentId);
		List<ZTagEntity> list = null;
		try {
			// 把顶层的查出来
			list = tagManager.queryByParams(params);
			for (int i = 0; i < list.size(); i++) {
				ZTagEntity model = list.get(i);
				// 有子节点
				tempRes += "{\"attr\":{\"id\":\"" + model.getTagCode()
						+ "\"},\"state\":\"open\",\"data\":\""
						+ model.getTagName() + "\" ,";
				tempRes += "\"children\":[";
				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("tagParentCode", model.getTagCode());
				// 遍历它的子节点
				List<ZTagEntity> sonList = tagManager.queryByParams(params1);
				// 遍历它的子节点
				for (int j = 0; j < sonList.size(); j++) {
					ZTagEntity mo = sonList.get(j);
					tempRes += "{\"attr\":{\"id\":\"" + mo.getTagCode()
							+ "\"},\"state\":\"open\",\"data\":\""
							+ mo.getTagName() + "\" " + "}";
					if (j < sonList.size() - 1) {
						tempRes += ",";
					}
				}
				tempRes += "]";
				tempRes += "}";
				if (i < list.size() - 1) {
					tempRes += ",";
				}
			}
			tempRes += "]";
		} catch (Exception e) {
			LogUtils.errorMsg("ZTagAction", "getJson", "查询标签失败!", e);
		}
		return tempRes;
	}
}
