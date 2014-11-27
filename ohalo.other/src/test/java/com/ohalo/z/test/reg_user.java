package com.ohalo.z.test;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class reg_user extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 823354315740119541L;
	JLabel jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8;
	JTextField jf1, jf3, jf4, jf6, jf7;
	JTextArea jta8;
	JComboBox jcb5;
	JPasswordField jpf2;
	JButton jb1, jb2, jb3;
	String ServerIp;
	int ServerPort;

	public reg_user(String as_ServerIp, int as_ServerPort) {
		super("注册新用户");
		ServerIp = as_ServerIp;
		ServerPort = as_ServerPort;
		jl1 = new JLabel("用户ID:");
		jl2 = new JLabel("密码:");
		jl3 = new JLabel("姓名:");
		jl4 = new JLabel("呢称:");
		jl5 = new JLabel("性别:");
		jl6 = new JLabel("个人主页:");
		jl7 = new JLabel("电子邮箱:");
		jl8 = new JLabel("个人简介:");

		jf1 = new JTextField(15);
		jpf2 = new JPasswordField(15);
		jf3 = new JTextField(15);
		jf4 = new JTextField(15);
		jcb5 = new JComboBox();
		jcb5.addItem("男");
		jcb5.addItem("女");
		jcb5.setSelectedIndex(0);
		// jcb5.setEditable(true);
		jf6 = new JTextField(25);
		jf7 = new JTextField(25);
		jta8 = new JTextArea(1, 25);

		jb1 = new JButton("提交(T)");
		jb2 = new JButton("清空(Q)");
		jb3 = new JButton("验证是否重名(Y)");
		jb1.setMnemonic(KeyEvent.VK_T);
		jb2.setMnemonic(KeyEvent.VK_Q);
		jb3.setMnemonic(KeyEvent.VK_Y);
		Insets is = new Insets(5, 5, 5, 5);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.anchor = GridBagConstraints.EAST;
		gBC.fill = GridBagConstraints.NONE;
		gBC.gridwidth = 1;
		gBC.gridheight = 1;
		gBC.insets = is;
		Container thisCP;
		thisCP = getContentPane();
		JScrollPane scrollPane_2 = new JScrollPane(jta8,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_2.setPreferredSize(new Dimension(400, 150));
		// 第一列：标签列
		gBC.gridy = 1;
		gBC.gridx = 1;
		thisCP.add(jl1, gBC);
		gBC.gridy = 2;
		thisCP.add(jl2, gBC);
		gBC.gridy = 3;
		thisCP.add(jl3, gBC);
		gBC.gridy = 4;
		thisCP.add(jl4, gBC);
		gBC.gridy = 5;
		thisCP.add(jl5, gBC);
		gBC.gridy = 6;
		thisCP.add(jl6, gBC);
		gBC.gridy = 7;
		thisCP.add(jl7, gBC);
		gBC.gridy = 8;
		thisCP.add(jl8, gBC);
		// 第二列：控件列
		gBC.anchor = GridBagConstraints.WEST;

		gBC.gridx = 2;
		gBC.gridy = 1;
		thisCP.add(jf1, gBC);
		gBC.gridwidth = 2;
		gBC.gridy = 2;
		thisCP.add(jpf2, gBC);
		gBC.gridy = 3;
		thisCP.add(jf3, gBC);
		gBC.gridy = 4;
		thisCP.add(jf4, gBC);
		gBC.gridy = 5;
		thisCP.add(jcb5, gBC);
		gBC.gridy = 6;
		thisCP.add(jf6, gBC);
		gBC.gridy = 7;
		thisCP.add(jf7, gBC);
		gBC.gridy = 8;
		thisCP.add(scrollPane_2, gBC);
		gBC.gridwidth = 1;

		gBC.gridx = 2;
		gBC.gridy = 9;
		thisCP.add(jb1, gBC);
		gBC.gridx = 3;
		thisCP.add(jb2, gBC);
		gBC.gridx = 3;
		gBC.gridy = 1;
		thisCP.add(jb3, gBC);
		this.pack();
		this.setVisible(true);
		// 清空表单.
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_form();
			}
		});
		// 注册用户信息到数据库.
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send_reg_info();
			}
		});
		// 测试用户ID是否同名.
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check_same_userid();
			}
		});
	}

	public void clear_form() {
		jf1.setText("");
		jpf2.setText("");
		jf3.setText("");
		jf4.setText("");
		jcb5.setSelectedItem("男");
		jf6.setText("");
		jf7.setText("");
		jta8.setText("");
	}

	public void check_same_userid() {
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		String reply;
		if (jf1.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "用户ID不能为空!");
			return;
		}
		try {
			clientSocket = new Socket(ServerIp, ServerPort);
			sin = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));
			sout.println("checksameuserid");
			sout.println(jf1.getText());
			sout.flush();
			reply = sin.readLine();
			if (reply.equals("same_username")) {
				JOptionPane.showMessageDialog(null, "该用户ID已被占用!");
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			} else if (reply.equals("ok_to_regedit")) {
				JOptionPane.showMessageDialog(null, "该用户ID可以注册,抓紧时间哦!");
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			} else if (reply.equals("db_connect_fail")) {
				JOptionPane.showMessageDialog(null, "数据库连接错误!");
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			}
		} catch (IOException e) {
			try {
				clear_form();
				sout.close();
				sin.close();
				clientSocket.close();
			} catch (IOException e2) {
				System.out.println("严重错误!系统将关闭");
				System.exit(0);
			}
			JOptionPane.showMessageDialog(null, "服务器连接无效!");
			return;
		}
		return;
	}

	@SuppressWarnings("deprecation")
	public boolean check_form() {
		if (jf1.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "用户ID不能为空!");
			jf1.grabFocus();
			return false;
		}
		if (jpf2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			jpf2.grabFocus();
			return false;
		}
		if (jf3.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "姓名不能为空");
			jf3.grabFocus();
			return false;
		}
		if (jf4.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "呢称不能为空");
			jf4.grabFocus();
			return false;
		}
		if (jf7.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "电子邮箱不能为空");
			jf7.grabFocus();
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public void send_reg_info() {
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		String reply;
		if (check_form() == false) {
			return;
		}
		try {
			clientSocket = new Socket(ServerIp, ServerPort);
			sin = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));
			sout.println("regedit");
			sout.println(jf1.getText());
			sout.println(jpf2.getText());
			sout.println(jf3.getText());
			sout.println(jf4.getText());
			sout.println((String) jcb5.getSelectedItem());
			sout.println(jf6.getText());
			sout.println(jf7.getText());
			sout.println(jta8.getText());
			sout.flush();
			reply = sin.readLine();
			if (reply.equals("same_username")) {
				JOptionPane.showMessageDialog(null, "该用户ID已被占用!");
				clear_form();
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			} else if (reply.equals("reg_fail")) {
				JOptionPane.showMessageDialog(null, "用户注册失败!");
				clear_form();
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			} else if (reply.equals("reg_succeed")) {
				JOptionPane.showMessageDialog(null, "用户注册成功!");
				clear_form();
				sout.close();
				sin.close();
				clientSocket.close();
				return;
			}

		} catch (IOException e) {
			try {
				clear_form();
				sout.close();
				sin.close();
				clientSocket.close();
			} catch (IOException e2) {
				System.out.println("严重错误!系统将关闭");
				System.exit(0);
			}
			JOptionPane.showMessageDialog(null, "服务器连接无效!");
			return;
		}
		return;
	}
}