/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ohalo.base.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-9 下午2:28:09
 */
public class TreeNodeList extends TreeNode {

	/***
   * 
   */
	private static final long serialVersionUID = 7778357078140375500L;

	public TreeNodeList() {
		super();
		this.children = new ArrayList<TreeNode>();
	}

	public TreeNodeList(String id, String pid, Map<String, Object> data) {
		super(id, pid, data);
		this.children = new ArrayList<TreeNode>();
	}

	public TreeNodeList(String id, String pid, Map<String, Object> data,
			List<TreeNode> children) {
		super(id, pid, data);
		this.children = (children == null) ? (new ArrayList<TreeNode>())
				: children;
		if (children != null && children.size() != 0) {
			this.leaf = false;
		} else {
			this.leaf = true;
		}
	}

	/**
	 * 添加孩子节点
	 * 
	 * @param childrenNode
	 */
	public void addChildNode(TreeNode childrenNode) {
		if (childrenNode != null) {
			childrenNode.level = this.level + 1;
			this.leaf = false;
			this.children.add(childrenNode);
		}
	}

	/**
	 * 对Child进行排序
	 * 
	 * @param isAsc
	 *            true:升序;false:降序
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sortChild(boolean isAsc) {
		if (this.children != null && this.children.size() != 0) {
			Collections.sort((List) this.children,
					new TreeNodeComparator(isAsc));
			Iterator<TreeNode> iterator = this.children.iterator();
			while (iterator.hasNext()) {
				TreeNode node = iterator.next();
				node.sortChild(isAsc);
			}
		}
	}

	/**
	 * 查找指定ID的节点
	 * 
	 * @param id
	 * @return
	 */
	public TreeNode getTreeNodeById(String id) {
		if (this.id != null && this.id.equals(id)) {
			return this;
		}
		if (this.children != null && this.children.size() != 0) {
			Iterator<TreeNode> iterator = this.children.iterator();
			while (iterator.hasNext()) {
				TreeNode node = iterator.next();
				TreeNode subNode = node.getTreeNodeById(id);
				if (subNode == null) {
					continue;
				}
				return subNode;
			}
		}
		return null;
	}

	/**
	 * 移除所有孩子节点
	 */
	public void removeChildNode() {
		if (this.children != null && this.children.size() != 0) {
			this.children.clear();
		}
	}

	/**
	 * 移除指定ID的节点
	 * 
	 * @param id
	 * @return
	 */
	public void removeChildNodeById(String id) {
		if (this.children != null && this.children.size() != 0) {
			Iterator<TreeNode> iterator = this.children.iterator();
			while (iterator.hasNext()) {
				TreeNode node = iterator.next();
				if (node == null || node.getId() == null) {
					continue;
				}
				if (!node.getId().equals(id)) {
					node.removeChildNodeById(id);
				} else {
					iterator.remove();
				}
			}
		}
	}

}
