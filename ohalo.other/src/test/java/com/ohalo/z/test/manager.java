package com.ohalo.z.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class manager extends JFrame {
	JLabel lblUsername, lblPassword;
	JComboBox comboUsername;
	JPasswordField txtPassword;
	JButton jbnButton1, jbnButton2;
	BufferedReader read1;
	PrintWriter write1;
	JLabel lblname, lblwelcome, lb_ServerName, lb_port;
	JTextField jt_servername, jt_port;
	String s32;
	@SuppressWarnings("rawtypes")
	TreeSet tr1;
	Socket clientSocket;
	BufferedReader sin;
	PrintWriter sout;
	liaotianshi lt1;
	String ServerIp = "localhost";
	int ServerPort = 10001;
	boolean loginSucc;
	String nickname;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public manager(String title) {
		super(title);
		tr1 = new TreeSet();
		String s = "";
		loginSucc = false;
		try {
			comboUsername = new JComboBox();
			if ((new File("manager.txt")).exists()) {
				read1 = new BufferedReader(new FileReader("manager.txt"));
				while ((s = read1.readLine()) != null) {
					comboUsername.addItem(s);
					comboUsername.setSelectedIndex(0);
					tr1.add(s);
				}
				read1.close();
			}

			lblname = new JLabel("好朋友聊天室");
			lblname.setFont(new Font("宋体", Font.PLAIN, 20));
			lblname.setForeground(Color.RED);
			lblwelcome = new JLabel("欢迎您!");

			lblUsername = new JLabel("用户名:");
			lblPassword = new JLabel("密  码:");
			txtPassword = new JPasswordField(10);
			lb_ServerName = new JLabel("服务器名称/IP");
			lb_port = new JLabel("端口号");
			jt_servername = new JTextField(15);
			jt_servername.setText("localhost");
			jt_port = new JTextField(15);
			jt_port.setText("10001");

			comboUsername.setEditable(true);
			jbnButton1 = new JButton("登录(Y)");
			jbnButton2 = new JButton("取消(X)");
			jbnButton1.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					try {
						String user, pass;
						ServerIp = jt_servername.getText();
						ServerPort = Integer.valueOf(jt_port.getText());
						user = (String) comboUsername.getSelectedItem();
						pass = txtPassword.getText();
						if (connectserver(user, pass) == 1) {
							// 将登陆成功的用户记入用户列表.
							tr1.add(user);
							write1 = new PrintWriter(new BufferedWriter(
									new FileWriter("manager.txt", false)));
							Iterator it1;
							it1 = tr1.iterator();
							while (it1.hasNext()) {
								write1.println(it1.next());
							}
							write1.close();
							// 打开聊天室界面并隐藏登陆界面.
							lt1 = new liaotianshi(jt_port.getText() + "聊天室",
									user, sin, sout, "Manager", ServerIp,
									ServerPort);
							lt1.pack();
							setVisible(false);
							lt1.setVisible(true);
							lt1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							loginSucc = true;
							System.out.println("登录成功!");
						} else {
							loginSucc = false;
						}
					} catch (IOException e2) {
						// System.out.println(e2.toString());
					}
				}

			});
			jbnButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 退出
					if (loginSucc) {
						sout.println("/logout");
						sout.flush();
						try {
							sout.close();
							clientSocket.close();

						} catch (IOException dfe) {

						}

					}
					System.exit(0);
				}

			});

			jbnButton1.setMnemonic(KeyEvent.VK_Y);
			jbnButton2.setMnemonic(KeyEvent.VK_X);
			// getContentPane().setLayout(new FlowLayout());
			Insets is = new Insets(5, 5, 5, 5);

			getContentPane().setLayout(new GridBagLayout());
			GridBagConstraints gBC = new GridBagConstraints();

			gBC.anchor = GridBagConstraints.CENTER;
			gBC.fill = GridBagConstraints.NONE;
			gBC.gridx = 1;
			gBC.gridy = 1;
			gBC.gridwidth = 2;
			gBC.gridheight = 1;
			gBC.insets = is;
			getContentPane().add(lblname, gBC);

			gBC.anchor = GridBagConstraints.CENTER;
			gBC.fill = GridBagConstraints.NONE;
			gBC.gridy = 2;
			getContentPane().add(lblwelcome, gBC);

			gBC.anchor = GridBagConstraints.WEST;
			gBC.fill = GridBagConstraints.NONE;
			gBC.gridx = 1;
			gBC.gridy = 3;
			gBC.gridwidth = 1;
			gBC.gridheight = 1;
			gBC.insets = is;
			getContentPane().add(lblUsername, gBC);

			gBC.gridx = 1;
			gBC.gridy = 4;
			gBC.gridwidth = 1;
			gBC.gridheight = 1;
			gBC.insets = is;
			getContentPane().add(lblPassword, gBC);

			gBC.gridx = 1;
			gBC.gridy = 5;
			getContentPane().add(lb_ServerName, gBC);

			gBC.gridx = 2;
			gBC.gridy = 5;
			getContentPane().add(jt_servername, gBC);

			gBC.gridx = 1;
			gBC.gridy = 6;
			getContentPane().add(lb_port, gBC);

			gBC.gridx = 2;
			gBC.gridy = 6;
			getContentPane().add(jt_port, gBC);

			gBC.anchor = GridBagConstraints.WEST;
			gBC.fill = GridBagConstraints.HORIZONTAL;
			gBC.gridx = 2;
			gBC.gridy = 3;
			gBC.gridwidth = 1;
			gBC.gridheight = 1;
			gBC.insets = is;
			getContentPane().add(comboUsername, gBC);

			gBC.gridx = 2;
			gBC.gridy = 4;
			gBC.gridwidth = 1;
			gBC.gridheight = 1;
			gBC.insets = is;
			getContentPane().add(txtPassword, gBC);

			JPanel jp1 = new JPanel();
			jp1.setLayout(new FlowLayout());
			jp1.add(jbnButton1, gBC);
			jp1.add(jbnButton2, gBC);
			gBC.gridx = 1;
			gBC.gridy = 7;
			gBC.gridwidth = 2;
			gBC.gridheight = 1;
			gBC.insets = new Insets(10, 10, 10, 10);

			getContentPane().add(jp1, gBC);

		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}

	public int connectserver(String username, String password) {

		String reply;
		try {
			clientSocket = new Socket(ServerIp, ServerPort);
			sin = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));
			sout.println("login");
			sout.flush();
			sout.println(username);
			sout.flush();
			sout.println(password);
			sout.

			flush();
			sout.println("Manager");
			sout.flush();
			reply = sin.readLine();
			if (reply.equals("login succ"))
				return 1;
			else if (reply.equals("same user login")) {
				JOptionPane.showMessageDialog(null, "登录失败(该用户名已经被占用)");
				return 0;
			} else {
				JOptionPane.showMessageDialog(null, "登录失败(原因:错误的用户名或密码)");
				return 0;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "无法连接到服务器!");
		}
		return 0;
	}

	public static void main(String[] args) {
		manager frm = new manager("登陆界面");
		frm.pack();
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}