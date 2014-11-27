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

/***
 * @author <a href="mailto:halo26812@yeah.net">XXX</a>
 * @version $Id: $
 * 
 *          "id" => false, "parent_id" => false, "position" => false, "left" =>
 *          false, "right" => false, "level" => false
 * 
 */
public class Node {

	// id
	private String id;
	// 父id
	private String parent_id;
	// 位置
	private String position;
	//
	private String left;
	//
	private String right;
	//
	private String level;

	/***
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/***
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/***
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}

	/***
	 * @param parent_id
	 *            the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	/***
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/***
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/***
	 * @return the left
	 */
	public String getLeft() {
		return left;
	}

	/***
	 * @param left
	 *            the left to set
	 */
	public void setLeft(String left) {
		this.left = left;
	}

	/***
	 * @return the right
	 */
	public String getRight() {
		return right;
	}

	/***
	 * @param right
	 *            the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/***
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/***
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
}