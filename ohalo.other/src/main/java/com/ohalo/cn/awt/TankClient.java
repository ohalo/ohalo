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
package com.ohalo.cn.awt;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TankClient extends JFrame { // 此处改为Frame就可以重画
	/***
   * 
   */
	private static final long serialVersionUID = 3461765227608045016L;

	private int x = 50;

	private int y = 50;

	public TankClient() {
		super("Tank War");
		this.setSize(400, 400);
		this.setLocation(200, 200);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		new Thread(new PaintTank()).start();
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, 30, 30);
		x += 25;
		y += 25;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		TankClient tc = new TankClient();
	}

	private class PaintTank implements Runnable {
		public void run() {
			while (true) {
				repaint(); // 时不时此处的repaint因为是属PaintTank的缘故？
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}