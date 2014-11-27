package org.ohalo.favorite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.ohalo.base.node.TreeNode;
import org.ohalo.base.node.TreeNodeList;
import org.ohalo.base.node.TreeNodeSet;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.base.utils.TimeUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.favorite.db.ZTagDb;
import org.ohalo.favorite.entity.ZTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author halo
 * 
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ZtagDBTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	public ZTagDb halodb;

	@SuppressWarnings("unused")
	@Test
	public void testFind() throws HaloDBException {
		ZTagEntity entity = new ZTagEntity("101", "标签1", "10", "用于添加标签信息!");
		entity.setId("101");
		entity.setCreateUser("系统管理员");
		entity.setCreateTime(TimeUtils.getDefaultDay());
		halodb.save(entity);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", "101");
		// System.out.println(halodb.queryByParams(paramsMap));
		List<ZTagEntity> sonList = halodb.queryByParams(paramsMap);
		// System.out.println(sonList.toString());
		// System.out.println(halodb.findById("101").toString());
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
	@Test
	public void testAllZtreeJson() {
		String ztreeJson = null;
		try {
			List<ZTagEntity> list = halodb.findAll();
			Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
			TreeNode root = new TreeNodeSet();
			root.setLeaf(false);
			root.setId("1");
			root.setName("我的收藏夹");
			root.setPid("0");
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
			LogUtils.errorMsg("ZtagDBTest", "testAllZtreeJson",
					"ztreeJson树转换异常!" + ztreeJson, e);
		}
	}
}
