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

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/***
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-9 下午2:34:28
 */
public class Test implements Runnable {
	private int idx;
	private Map<String, TreeNode> map;

	public Test(int idx, Map<String, TreeNode> map) {
		this.idx = idx;
		this.map = map;
	}

	@SuppressWarnings("static-access")
	public void printNode() {
		TreeNode root = new TreeNodeSet();
		for (Map.Entry<String, TreeNode> entry : map.entrySet()) {
			TreeNode node = entry.getValue();
			String pid = node.getPid();
			TreeNode pNode = map.get(pid);
			if (pNode == null) {
				root.addChildNode(node);
			} else {
				pNode.addChildNode(node);
			}
		}
		root.setSortNo(idx);
		// root.removeChildNodeById("1-2-1");
		// root.removeChildNodeById("1-1-1");
		System.out.println(this.idx + "="
				+ new JSONObject().toJSON(root).toString());
	}

	public void run() {
		printNode();
	}
}
