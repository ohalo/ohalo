package com.ohalo.z.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class liaotianshi extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -856246243836909641L;
	JList ListUserName;
	JTextArea Liaotianxinxi;
	JTextArea Fayan;
	JButton Fasong;
	JLabel lblname;
	String username3;
	BufferedReader r3;
	PrintWriter w3;
	String SpeakUser;
	String SpeakNick;
	JSplitPane jsp1;
	JCheckBox siliao;
	String bl_manager;
	String ServerIp;
	int ServerPort;
	manager manager2;
	// 管理员专用按钮:
	JButton M_user, M_msg;

	@SuppressWarnings("unused")
	public liaotianshi(String title, String username2, BufferedReader r2,
			PrintWriter w2, String Manager, String as_serverip, int as_severport) {
		super(title);
		bl_manager = Manager;
		ServerIp = as_serverip;
		ServerPort = as_severport;
		username3 = username2;
		r3 = r2;
		w3 = w2;
		// 处理聊天室界面
		siliao = new JCheckBox("悄悄话");
		lblname = new JLabel("好朋友聊天室 ");
		lblname.setFont(new Font("宋体", Font.PLAIN, 20));
		Liaotianxinxi = new JTextArea(15, 1);
		Liaotianxinxi.setEditable(false);
		String text = "           好朋友聊天室欢迎你!            \n";
		Liaotianxinxi.append(text);
		Fayan = new JTextArea("");
		Fasong = new JButton("发送信息");
		DefaultListModel lm = new DefaultListModel();
		ListUserName = new JList(lm);
		lm.add(0, new user_ob("所有人", "所有人"));
		JScrollPane scrollPane_3 = new JScrollPane(ListUserName,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setPreferredSize(new Dimension(100, 400));

		ListUserName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListUserName.setSelectedIndex(0);

		Fasong.setMnemonic(KeyEvent.VK_Y);
		JScrollPane scrollPane_1 = new JScrollPane(Fayan,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scrollPane_2 = new JScrollPane(Liaotianxinxi,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setPreferredSize(new Dimension(500, 100));
		scrollPane_2.setPreferredSize(new Dimension(400, 400));

		Insets is = new Insets(5, 5, 5, 5);
		jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jsp1.setLeftComponent(scrollPane_2);
		jsp1.setRightComponent(scrollPane_3);
		jsp1.setAutoscrolls(true);
		jsp1.setDividerSize(5);
		getContentPane().setLayout(new BorderLayout(5, 5));
		JPanel jp4 = new JPanel();
		jp4.setLayout(new BorderLayout());
		jp4.add(lblname, BorderLayout.CENTER);

		getContentPane().add(jp4, BorderLayout.PAGE_START);
		getContentPane().add(jsp1, BorderLayout.CENTER);
		jsp1.setDividerLocation(0.8);
		JPanel jp1 = new JPanel();
		jp1.setLayout(new BorderLayout(5, 5));
		jp1.add(scrollPane_1, BorderLayout.CENTER);
		JPanel jp3 = new JPanel();
		jp3.setLayout(new BorderLayout(5, 5));
		jp3.add(Fasong, BorderLayout.NORTH);
		jp3.add(siliao, BorderLayout.CENTER);
		jp1.add(jp3, BorderLayout.EAST);
		jp1.setVisible(true);
		JPanel jp2 = new JPanel();
		getContentPane().add(jp1, BorderLayout.PAGE_END);
		// 增加监听.
		Fasong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String speak1;
				getspeakuser();
				if (SpeakUser.equals(username3)) {
					JOptionPane.showMessageDialog(null, "无法自言自语");
					return;
				}
				if (siliao.isSelected()) {
					if (SpeakUser == "所有人") {
						JOptionPane.showMessageDialog(null, "无法对所有人使用私聊");
						return;
					}
					speak(username3, r3, w3, Fayan.getText(), "/SILIAO");
				} else {
					speak(username3, r3, w3, Fayan.getText(), "/TALK");
				}
				Fayan.setText("");
			}
		});
		addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent e) {
				// TODO 自动生成方法存根
			}

			public void componentMoved(ComponentEvent e) {
				// TODO 自动生成方法存根
			}

			public void componentResized(ComponentEvent e) {
				// TODO 自动生成方法存根
				jsp1.setDividerLocation(0.8);
			}

			public void componentShown(ComponentEvent e) {
				// TODO 自动生成方法存根
			}
		});
		Fayan.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					String speak1;
					getspeakuser();
					if (SpeakUser.equals(username3)) {
						JOptionPane.showMessageDialog(null, "无法自言自语");
						return;
					}
					if (siliao.isSelected()) {
						if (SpeakUser == "所有人") {
							JOptionPane.showMessageDialog(null, "无法对所有人使用私聊");
							return;
						}
						speak(username3, r3, w3, Fayan.getText(), "/SILIAO");
					} else {
						speak(username3, r3, w3, Fayan.getText(), "/TALK");
					}
					Fayan.setText("");
				}
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {

			}

		});
		// 管理员按钮:
		if (bl_manager.equals("Manager")) {
			M_user = new JButton("用户管理");
			M_msg = new JButton("聊天信息管理");
			JPanel jp5 = new JPanel();
			jp5.setLayout(new FlowLayout());
			jp5.add(M_user);
			jp5.add(M_msg);

			jp4.add(jp5, BorderLayout.EAST);
			M_user.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					user_mager t1;
					t1 = new user_mager(ServerIp, ServerPort);
				}
			});
			M_msg.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					chatmsg_manager chat1;
					chat1 = new chatmsg_manager(ServerIp, ServerPort);
				}
			});

		}

		// 创建接受线程.
		ReceiveThread rt1 = new ReceiveThread(this, r2, Liaotianxinxi,
				username3);
	}

	public void getspeakuser() {
		if (ListUserName.getSelectedValue() == null) {
			ListUserName.setSelectedIndex(0);
		}
		SpeakUser = ((user_ob) ListUserName.getSelectedValue()).getUsername();
	}

	public void speak(String username, BufferedReader r1, PrintWriter w1,
			String word, String msg) {
		try {
			if (word.equals("")) {
				return;
			}
			if (msg.equals("/TALK")) {
				w1.println("/TALK");
				w1.println(username3 + "\n对\n" + SpeakUser + "\n说:\n" + word);
				w1.println("TALK/");
				w1.flush();
			}
			if (msg.equals("/SILIAO")) {
				w1.println("/SILIAO");
				w1.println(SpeakUser);
				w1.println(username3 + "\n悄悄地对\n" + SpeakUser + "\n说:\n" + word);
				w1.println("TALK/");
				w1.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ReceiveThread extends Thread {
	BufferedReader r5;
	JTextArea liaotianxinxi2;
	String Username;
	liaotianshi lt;
	DefaultListModel lm;

	public ReceiveThread(liaotianshi as_lt, BufferedReader r4,
			JTextArea liaotianxinxi, String username) {
		lt = as_lt;
		Username = username;
		r5 = r4;
		liaotianxinxi2 = liaotianxinxi;
		this.start();
	}

	public void run() {
		String line = "";
		String name, action, who, say;
		try {
			while (line.equals("STOP_SERVER/") == false) {
				line = r5.readLine();
				if (line.equals("/TALK")) {

					// w1.println(username3+"\n对\n"+SpeakUser+"\n说:\n"+word);
					name = r5.readLine();
					action = r5.readLine();
					who = r5.readLine();
					say = r5.readLine();
					if (name.equals(Username)) {
						name = "你";
					} else if (name.equals("系统")) {
					} else {
						name = getNickName(name);
					}
					if (who.equals(Username)) {
						who = "你";
					} else {
						who = getNickName(who);
					}
					liaotianxinxi2.append(name + action + who + say + "\n");
					liaotianxinxi2.setCaretPosition(liaotianxinxi2.getText()
							.length());
					line = r5.readLine();
					while (line.equals("TALK/") == false) {
						liaotianxinxi2.append(line + "\n");
						liaotianxinxi2.setCaretPosition(liaotianxinxi2
								.getText().length());
						line = r5.readLine();
					}
				}
				if (line.equals("/RefreshUserList")) {
					int i = 0;
					lm = new DefaultListModel();
					lm.add(i, new user_ob("所有人", "所有人"));
					line = r5.readLine();
					while (line.equals("SendUserList/") == false) {
						i++;
						lm.add(i, (new user_ob(line, r5.readLine())));
						line = r5.readLine();
					}
					lt.ListUserName.setModel(lm);
				}
				if (line.equals("/adduser")) {
					line = r5.readLine();
					if (line.equals(Username)) {
						r5.readLine();
						liaotianxinxi2.append("你微笑地走入聊天室.\n");
					} else {
						liaotianxinxi2.append(r5.readLine() + " 微笑地走入聊天室.\n");
					}
					liaotianxinxi2.setCaretPosition(liaotianxinxi2.getText()
							.length());
				}
				if (line.equals("/lessuser")) {
					line = r5.readLine();
					liaotianxinxi2.append(line + " 匆匆忙忙地离开聊天室.\n");
					liaotianxinxi2.setCaretPosition(liaotianxinxi2.getText()
							.length());
				}

				if (line.equals("/QUIT"))
					break;
			}
			JOptionPane.showMessageDialog(null, "失去服务器连接!");
			return;
		} catch (IOException e) {
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "失去服务器连接!");
			return;
		}
	}

	public String getNickName(String as_UserName) {
		for (int i = 0; i < lm.getSize(); i++) {
			if (as_UserName.equals(((user_ob) lm.elementAt(i)).getUsername())) {
				return ((user_ob) lm.elementAt(i)).getnickname();
			}
		}
		return "";
	}

}