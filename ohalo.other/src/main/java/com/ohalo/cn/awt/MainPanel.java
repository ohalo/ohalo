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

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/***
 * <pre>
 * mainpanel (600 * 400) 
 * 
 * ------------------------------------
 * |                ||                |
 * |                ||                |
 * |                ||                |
 * |   leftpanel    || rightTextArea  |
 * |                ||                |
 * |                ||                |
 * |                ||                |
 * ------------------------------------
 * 
 * </pre>
 * 
 * @description leftPanel 为放图片使用, rightPanel为放文字使用
 * @author <a href="mailto:halo26812@yeah.net">z.halo</a>
 * @version 2013-9-30 下午2:01:30
 */
@SuppressWarnings("unused")
public class MainPanel extends JFrame {

	/***
   * 
   */
	private static final long serialVersionUID = -4678472586661281180L;

	/**
	 * 左侧面板
	 */
	public JPanel leftPanel;

	/**
	 * 右侧的文本框显示
	 */
	public JTextArea rtextField;

	private static int count = 0;

	private static String[] imagePath = {
			"F:\\halo_cloudFile\\desketop\\halo_photo\\8bfceeb7jw1e914xvj3jcj20aw0cy0ta.jpg",
			"F:\\halo_cloudFile\\desketop\\halo_photo\\8bfceeb7jw1e916sjfbp2j20go0p0taa.jpg",
			"F:\\halo_cloudFile\\desketop\\halo_photo\\8bfceeb7jw1e9172vf1rnj20cs1b4tcr.jpg",
			"F:\\halo_cloudFile\\desketop\\halo_photo\\8bfceeb7jw1e8v0yqztgkj20dw0emwfl.jpg" };

	private static String[] imageText = {
			"lz平胸妹纸一枚，昨日和一大胸\r\n室友一起睡觉，手放\r\n她胸上说：传染给我点，\r\n她悠悠的来句，你换\r\n胸来摸啊，不然明天手该肿了。\r\n",
			"跟媳妇通话，正嗨皮，突然一护\r\n士妹爬耳边娇滴滴叫\r\n道：小哥哥，欢迎下次再\r\n来哈……我和小伙伴当时\r\n就震精了……老婆，我错了，真的\r\n错了！！！……我艹，又赶出来了",
			"美图秀秀的各种特效就是这样了\r\n：清凉，紫色幻想，\r\n黑白，古铜色，哥特风，\r\n粉红佳人，这些特效里\r\n，我最喜欢粉红佳人，\r\n你们呢？",
			"一个杰森斯坦森能捏死10个权志\r\n龙，搓死5个张根硕\r\n，团灭EXO吗？-当然不能\r\n。-为什么？-因为真正的\r\n男人从来不打女人。" };

	private String ftext = "";

	private Color[] colors = new Color[] { Color.red, Color.cyan, Color.blue };

	public MainPanel() {
		leftPanel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8862070394095340950L;

			@Override
			public void paint(Graphics g) {
				if (count > 3) {
					count = 0;
				}
				ImageIcon icon = new ImageIcon(imagePath[count]);
				g.drawImage(icon.getImage(), 0, 0, this.getSize().width,
						this.getSize().height, this);
				rtextField.setText(imageText[count]);
			}
		};
		rtextField = new JTextArea() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2802309351849107568L;
		};
		initPanel();
	}

	// public void paint(Graphics g) {
	//
	// if (count > 3) {
	// count = 0;
	// }
	// ImageIcon icon = new ImageIcon(imagePath[count]);
	// g.drawImage(icon.getImage(), 0, 0, this.leftPanel.getSize().width,
	// this.leftPanel.getSize().height, this.leftPanel);
	//
	// for (int i = 0; i < imageText[count].length(); i++) {
	// this.rtextField.setText(String.valueOf(imageText[count].charAt(i)));
	// this.rtextField.repaint();
	// }
	//
	// this.leftPanel.setBackground(colors[count]);
	// }

	/**
	 * 
	 * <pre>
	 * 方法体说明：初始化panel面板
	 * 作者：赵辉亮
	 * 日期：2013-9-30
	 * </pre>
	 */
	public void initPanel() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭框架的同时结束程序
		this.setSize(800, 550);// 设置框架大小为长300,宽200
		this.setResizable(false);// 设置框架不可以改变大小
		this.setTitle("HALO-->ZHL");// 设置框架标题
		this.setLayout(null);// 设置面板布局管理
		this.leftPanel.setBackground(Color.white);
		this.leftPanel.setBounds(0, 0, 400, 550);
		this.rtextField.setBounds(400, 0, 400, 550);
		this.add(leftPanel);
		this.add(rtextField);
		new Thread(new PaintTank()).start();
		this.setVisible(true);// 设置框架可显
	}

	private class PaintTank implements Runnable {
		public void run() {
			while (true) {
				leftPanel.repaint(); // 时不时此处的repaint因为是属PaintTank的缘故？
				try {
					Thread.sleep(5000);
					count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new MainPanel();
	}

}
