package com.ohalo.cn.awt;

import com.ohalo.cn.result.BaiduTrans;
import com.ohalo.cn.result.TransResult;
import com.ohalo.cn.utils.BaiduTranUtil;
import com.ohalo.cn.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: z.halo Date: 13-9-27 Time: 下午1:44 To change
 * this template use File | Settings | File Templates.
 */
public class OhaloDict extends JFrame {

	/***
   * 
   */
	private static final long serialVersionUID = -7463037821212918157L;

	/**
	 * 字典panel
	 */
	public JPanel dictPanel;

	/**
	 * 字典窗口
	 */
	public JWindow dictWindow;

	/**
	 * 输入框
	 */
	public JTextField inputTextField;

	/**
	 * 汉英翻译，英汉翻译，等等
	 */
	public JComboBox tranCombox;

	/**
	 * 查询按钮
	 */
	public JButton searchDictButton;

	/**
	 * 显示信息面板
	 */
	public JTextArea showInfoPanel;

	public HashMap<String, String> tranMap;

	public OhaloDict() {
		dictPanel = new JPanel();
		inputTextField = new JTextField();
		SwingUtils.enterPressesWhenFocused(inputTextField,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						commonSearchDictEvent(e);
					}
				});
		searchDictButton = new JButton();
		showInfoPanel = new JTextArea();
		tranCombox = new JComboBox();
		tranMap = new HashMap<String, String>();
		tranMap.put("英", "en");
		tranMap.put("汉", "zh");
		tranMap.put("自动识别", "auto");
		tranMap.put("日", "jp");
		initPanel();
	}

	public void initPanel() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭框架的同时结束程序
		this.setSize(600, 400);// 设置框架大小为长300,宽200
		this.setResizable(false);// 设置框架不可以改变大小
		this.setTitle("HALO-->词典查询");// 设置框架标题
		this.dictPanel.setLayout(null);// 设置面板布局管理
		this.dictPanel.setBackground(Color.white);// 设置面板背景颜色
		this.tranCombox.addItem("自动识别");
		this.tranCombox.addItem("英 --> 汉");
		this.tranCombox.addItem("汉 --> 英");
		this.tranCombox.addItem("汉 --> 日");
		this.tranCombox.addItem("日 --> 汉");
		this.tranCombox.setBounds(40, 20, 100, 24);
		this.inputTextField.setBounds(150, 20, 300, 24);
		this.searchDictButton.setText("查询");
		this.searchDictButton.setBounds(460, 20, 60, 24);
		this.searchDictButton.addActionListener(new ActionListener()// 匿名类实现ActionListener接口
				{
					public void actionPerformed(ActionEvent e) {
						commonSearchDictEvent(e);
					}
				});

		this.showInfoPanel.setBounds(20, 54, 550, 300);

		this.dictPanel.add(tranCombox);
		this.dictPanel.add(inputTextField);// 加载标签到面板
		this.dictPanel.add(searchDictButton);
		this.dictPanel.add(showInfoPanel);
		this.add(dictPanel);// 加载面板到框架
		this.setVisible(true);// 设置框架可显
	}

	public void commonSearchDictEvent(ActionEvent e) {
		String text = inputTextField.getText();
		if (text == null || ("").equals(text.trim())) {
			return;
		}

		Object obj = tranCombox.getSelectedItem();
		String tranType = "英 --> 汉";
		if (obj != null && !obj.toString().trim().equals("")) {
			tranType = obj.toString();
		}
		System.out.println("查询单词：" + text + ",如何翻译：" + tranType);
		searchDictAction(text, tranType);
	}

	/**
	 * @param inputText
	 *            输入字符 如：hello ,名称
	 * @param tranType
	 *            翻译类型 如：汉英翻译，英汉翻译 tranType的格式为 汉 -->
	 *            英，所以在词典中需要对翻译类型做处理，提取出其中的汉字，然后转化成代码中可以识别的文字
	 */
	public void searchDictAction(String inputText, String tranType) {
		String fromTran = "auto";
		String toTran = "auto";

		if (tranType.indexOf("-->") > -1) {
			String[] tranTypes = tranType.split("-->");

			fromTran = tranTypes[0].trim();
			toTran = tranTypes[1].trim();

			fromTran = tranMap.get(fromTran);
			toTran = tranMap.get(toTran);
		}

		BaiduTrans bdtrans = null;
		System.out.println("翻译文字：" + inputText + "转义类型： from-->" + fromTran
				+ ",to-->" + toTran);
		StringBuffer sb = new StringBuffer(
				"***************************************************************************************************************\r\n");
		sb.append("翻译结果：\r\n");

		try {
			bdtrans = BaiduTranUtil.toBaiduTran(inputText, fromTran, toTran);
			List<TransResult> trs = bdtrans.getTrans_result();
			for (TransResult result : trs) {
				sb.append("   " + result.getDst() + "\r\n");
			}
		} catch (IOException e) {
			sb.append("你查询的单词错误,请重新输入!\r\n");
		}

		this.showInfoPanel.setText(sb.toString());
	}

	public static void main(String[] args) {
		new OhaloDict();
	}

}
