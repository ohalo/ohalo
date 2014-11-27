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

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-9 下午2:26:24
 */
public abstract class TreeNode implements Serializable {

	/***
   * 
   */
	private static final long serialVersionUID = 1722848796855980564L;

	/** ID */
	protected String id;
	/** 名称 **/
	protected String name;

	/***
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/***
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 父ID */
	protected String pid;
	/** 排序 */
	protected int sortNo;
	/** 当前节点层级 */
	protected int level;
	/** 是否打开 **/
	private boolean open = true;
	/** 是否支持右键菜单 **/
	private boolean noR = false;
	/** 是否叶子节点 */
	protected boolean leaf;
	/** 数据 */
	protected Map<String, Object> data;
	/** 孩子节点 */
	protected Collection<TreeNode> children;

	protected TreeNode() {
		this.leaf = true;
		this.data = new HashMap<String, Object>();
	}

	protected TreeNode(String id, String pid, Map<String, Object> data) {
		this.id = id;
		this.pid = pid;
		this.leaf = true;
		this.data = (data == null) ? (new HashMap<String, Object>()) : data;
	}

	/**
	 * 添加孩子节点
	 * 
	 * @param childNode
	 */
	public abstract void addChildNode(TreeNode childNode);

	/**
	 * 对Child进行排序
	 * 
	 * @param isAsc
	 *            true:升序;false:降序
	 */
	public abstract void sortChild(boolean isAsc);

	/**
	 * 查找指定ID的节点
	 * 
	 * @param id
	 * @return
	 */
	public abstract TreeNode getTreeNodeById(String id);

	/**
	 * 移除所有孩子节点
	 */
	public abstract void removeChildNode();

	/**
	 * 移除指定ID的节点
	 * 
	 * @param id
	 * @return
	 */
	public abstract void removeChildNodeById(String id);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Collection<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Collection<TreeNode> children) {
		this.children = children;
	}

	/***
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/***
	 * @param open
	 *            the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/***
	 * @return the noR
	 */
	public boolean isNoR() {
		return noR;
	}

	/***
	 * @param noR
	 *            the noR to set
	 */
	public void setNoR(boolean noR) {
		this.noR = noR;
	}

	/**
	 * TreeNode比较类
	 * 
	 * @ClassName TreeNodeComparator
	 * @Description TreeNode比较类
	 * @author ry
	 * @date 2012-3-9 下午02:56:22
	 */
	class TreeNodeComparator implements Serializable, Comparator<TreeNode> {

		private static final long serialVersionUID = -6291371529241047911L;

		/** 是否升序 */
		private boolean isAsc;

		/**
		 * 默认升序构造方法
		 * 
		 * @Title TreeNodeComparator
		 */
		public TreeNodeComparator() {
			this.isAsc = true;
		}

		/**
		 * 指定排序构造方法
		 * 
		 * @Title TreeNodeComparator
		 * @param isAsc
		 */
		public TreeNodeComparator(boolean isAsc) {
			this.isAsc = isAsc;
		}

		public int compare(TreeNode o1, TreeNode o2) {
			if (o1 != null && o2 != null) {
				int result = o2.getSortNo() - o1.getSortNo();
				if (result == 0) {
					result = o2.getId().compareTo(o1.getId());
				}
				if (isAsc) {
					result = -result;
				}
				return result;
			}
			return 0;
		}

		public boolean isAsc() {
			return isAsc;
		}

		public void setAsc(boolean isAsc) {
			this.isAsc = isAsc;
		}
	}
}