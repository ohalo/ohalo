package com.ohalo.cn.file;

/**
 * 文件遍历界面
 * 遍历文件,并将文件显示在JTextArea上
 * bug:就是当内容超过JTextArea的大小时，JScrollPane的滚动条开始往下滑动，
 * 	        但时此时页面上的数据会卡住，一直到文件遍历完，即JTextArea的内容显示完，数据恢复正常。
 * @author well
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FileListFrame extends JFrame implements ActionListener,
		ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7189072788623954136L;
	/* 界面控件的定义 */
	private JPanel panel; // 放置容器的面板
	private JPanel panelNorth; // 放在北边的面板
	private JProgressBar progressBar; // 进度条
	private JLabel lbl; // 显示文件和相关信息
	private JFileChooser fileChooser; // 文件选择器
	private JScrollPane scrollPane; // 存放JTextArea
	private JTextArea textArea; // 显示内容的控件
	private JButton btn; // 确定按钮
	private JButton btnStart; // 开始遍历按钮

	/* Timer类 */
	private Timer timer;

	/* 显示遍历文件的个数 */
	private JLabel label;

	private Font font = new Font("Dialog", Font.PLAIN, 16);

	/**
	 * 界面的初始化
	 */
	public void init() {
		/* 界面控件的初始化 */
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);
		panelNorth = new JPanel();
		panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(panelNorth, BorderLayout.NORTH);
		btn = new JButton("选择文件");
		panelNorth.add(btn);
		lbl = new JLabel("没有选择文件");
		panelNorth.add(lbl);
		panelNorth.add(lbl);
		btnStart = new JButton("开始遍历");
		panelNorth.add(btnStart);
		textArea = new JTextArea(); // 中间的大的文本框
		textArea.setEditable(false);
		textArea.setFont(font);
		textArea.setCaretPosition(textArea.getText().length());
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollPane);
		progressBar = new JProgressBar(1, 100);
		// progressBar.setIndeterminate(true); // 让滚动条来回滚动
		progressBar.setValue(1); // 设置JProgressBar的初始值
		panelNorth.add(progressBar);
		timer = new Timer(100, this);
		label = new JLabel();
		/**
		 * 注册监听事件
		 */

		/* 选择文件按钮的点击事件 */
		btn.addActionListener(this);

		/* 开始遍历按钮的点击事件 */
		btnStart.addActionListener(this);

		/* 该界面的相关属性 */
		this.setTitle("文件遍历界面");
		this.setBounds(100, 10, 900, 650);
		this.setResizable(false);
		this.setLocationRelativeTo(FileListFrame.this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FileListFrame().init();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/* 点击选择文件按钮，选择一个目录 */
		if (e.getSource() == btn) {
			fileChooser = new MyFileChooser().getFileChooser();
			lbl.setText(fileChooser.getSelectedFile() == null ? "没有选择文件"
					: fileChooser.getSelectedFile().getName());
		}

		/* 开始遍历 */
		if (e.getSource() == btnStart) {
			if (fileChooser.getSelectedFile() == null || fileChooser == null) {
				JOptionPane.showMessageDialog(FileListFrame.this, "没有选择目录");
				return;
			}
			File dir = fileChooser.getSelectedFile();
			new FileList().fileList(dir, textArea, scrollPane);
			// timer.start(); // TODO:希望能实现进度条的功能
		}

		if (e.getSource() == timer) {
			int value = progressBar.getValue();
			if (value < 100)
				progressBar.setValue(++value);
			else {
				timer.stop();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int value = progressBar.getValue();
		if (e.getSource() == progressBar) {
			label.setText("已经遍历" + Integer.toString(value) + "个文件");
			label.setForeground(Color.blue);
		}
	}

}
