package com.ohalo.z.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class chatmsg_manager extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2616360118106253771L;
	JTable jta;
	JLabel jt_1, jt_2, jt_3, jt_4, jt_5;
	JButton jb_delete, jb_save;
	JTextField jf_start_date, jf_end_date;
	JTextField jf_userid, jf_user_to_id, jf_keyword;

	String ServerIp;
	int ServerPort;
	u_talbemodel_user2 model;
	String ls_servername;
	String ls_portNumber;
	String ls_databaseName;
	String ls_userName;
	String ls_password;

	public chatmsg_manager(String as_ServerIp, int as_ServerPort) {
		super("聊天信息管理");
		ServerIp = as_ServerIp;
		ServerPort = as_ServerPort;
		jta = new JTable();
		jt_1 = new JLabel("开始日期");
		jt_2 = new JLabel("结束日期");
		jt_3 = new JLabel("用户ID");
		jt_4 = new JLabel("对象ID");
		jt_5 = new JLabel("消息关键字");

		model = new u_talbemodel_user2(1, 6);
		model.setEditable(false);
		jta.setModel(model);
		jta.setPreferredScrollableViewportSize(new Dimension(800, 500));
		model.setValueAt("消息ID", 0, 0);
		model.setValueAt("用户ID", 0, 1);
		model.setValueAt("发言时间", 0, 2);
		model.setValueAt("发言对象", 0, 3);
		model.setValueAt("消息", 0, 4);
		model.setValueAt("公/私", 0, 5);
		getContentPane().setLayout(new BorderLayout(5, 5));
		JScrollPane pane1 = new JScrollPane(jta);
		getContentPane().add(pane1, BorderLayout.NORTH);

		jb_delete = new JButton("删除(D)");
		jb_delete.setMnemonic(KeyEvent.VK_D);
		jb_save = new JButton("查询(S)");
		jb_save.setMnemonic(KeyEvent.VK_S);

		jf_start_date = new JTextField(10);
		jf_end_date = new JTextField(10);
		jf_userid = new JTextField(10);
		jf_user_to_id = new JTextField(10);
		jf_keyword = new JTextField(20);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		Insets is = new Insets(5, 5, 5, 5);
		gBC.anchor = GridBagConstraints.EAST;
		gBC.fill = GridBagConstraints.NONE;
		gBC.gridwidth = 1;
		gBC.gridheight = 1;
		gBC.insets = is;
		gBC.gridy = 1;
		gBC.gridx = 5;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jb_save, gBC);
		gBC.gridx = 6;
		jp1.add(jb_delete, gBC);
		getContentPane().add(jp1, BorderLayout.SOUTH);

		gBC.gridx = 1;
		gBC.gridy = 1;
		gBC.anchor = GridBagConstraints.EAST;
		jp1.add(jt_1, gBC);
		gBC.gridx = 2;
		jp1.add(jf_start_date, gBC);
		gBC.gridx = 3;
		jp1.add(jt_2, gBC);
		gBC.gridx = 4;
		gBC.anchor = GridBagConstraints.WEST;
		jp1.add(jf_end_date, gBC);
		gBC.gridy = 2;
		gBC.gridx = 1;
		jp1.add(jt_3, gBC);
		gBC.gridx = 2;
		jp1.add(jf_userid, gBC);
		gBC.gridx = 3;
		jp1.add(jt_4, gBC);
		gBC.gridx = 4;
		jp1.add(jf_user_to_id, gBC);
		gBC.gridx = 5;
		jp1.add(jt_5, gBC);
		gBC.gridx = 6;
		gBC.gridwidth = 2;
		jp1.add(jf_keyword, gBC);
		gBC.gridy = 2;
		gBC.gridx = 1;
		gBC.gridwidth = 1;

		getIni();
		loadData();
		jb_delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int li_1 = 0;
				if (JOptionPane.showConfirmDialog(null, "确定要删除选定的行?") != 0) {
					return;
				}
				for (int i = 0; i < jta.getSelectedRows().length; i++) {
					li_1 = jta.getSelectedRows()[i];
					if (li_1 == 0)
						continue;
					deleteData((Integer) model.getValueAt(li_1, 0));
				}
				loadData();
				jta.clearSelection();
			}
		});
		jb_save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				QueryData();
				// loadData();
				jta.clearSelection();
			}
		});

		setSize(800, 620);
		setVisible(true);

	}

	public void deleteData(Integer ls_msgid) {
		boolean b1;
		chatmsg_db chatmsg_db1;
		if (ls_msgid == null)
			return;
		try {
			chatmsg_db1 = new chatmsg_db();
			b1 = chatmsg_db1.con_db(ls_servername, ls_portNumber,
					ls_databaseName, ls_userName, ls_password);
			if (b1) {
				if (chatmsg_db1.delele_msg(ls_msgid)) {
					return;
				} else {
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "服务器数据库连接无效!");
			}
			chatmsg_db1.close_db();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "服务器数据库连接错误!");
		}

	}

	public boolean getIni() {
		Socket clientSocket = null;
		BufferedReader sin = null;
		PrintWriter sout = null;
		try {
			clientSocket = new Socket(ServerIp, ServerPort);
			sin = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));
			sout.println("/request_ini");
			sout.flush();
			ls_servername = sin.readLine();
			ls_portNumber = sin.readLine();
			ls_databaseName = sin.readLine();
			ls_userName = sin.readLine();
			ls_password = sin.readLine();
			sout.close();
			sin.close();
			clientSocket.close();
		} catch (IOException e) {
			try {
				sout.close();
				sin.close();
				clientSocket.close();
			} catch (IOException e2) {
				System.out.println("严重错误!系统将关闭");
				System.exit(0);
			}
			JOptionPane.showMessageDialog(null, "服务器连接无效!");
			return false;
		}
		return true;
	}

	public void QueryData() {
		int ls_i = 1;
		int li_rowcount = 0;
		boolean b1;
		chatmsg_db chatmsg_db1;
		ResultSet rs;
		chatmsg_db1 = new chatmsg_db();
		b1 = chatmsg_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
				ls_userName, ls_password);
		if (b1) {
			li_rowcount = chatmsg_db1.query_msg_count(jf_userid.getText(),
					jf_user_to_id.getText(), jf_keyword.getText(),
					jf_start_date.getText(), jf_end_date.getText());
			rs = chatmsg_db1.query_msg(jf_userid.getText(),
					jf_user_to_id.getText(), jf_keyword.getText(),
					jf_start_date.getText(), jf_end_date.getText());

			if (rs != null) {
				try {
					model.setRowCount(li_rowcount + 1);
					model.setValueAt("消息ID", 0, 0);
					model.setValueAt("用户ID", 0, 1);
					model.setValueAt("发言时间", 0, 2);
					model.setValueAt("发言对象", 0, 3);
					model.setValueAt("消息", 0, 4);
					model.setValueAt("公/私", 0, 5);
					while (rs.next()) {
						model.setValueAt(rs.getInt(1), ls_i, 0);
						model.setValueAt(rs.getString(2), ls_i, 1);
						model.setValueAt(rs.getDate(3), ls_i, 2);
						model.setValueAt(rs.getString(4), ls_i, 3);
						model.setValueAt(rs.getString(5), ls_i, 4);
						model.setValueAt(rs.getString(6), ls_i, 5);
						ls_i++;
					}
				} catch (Exception s) {
					s.printStackTrace();
				}
			} else {
				model.setRowCount(1);
			}
			chatmsg_db1.close_db();
		}

	}

	public void loadData() {
		int ls_i = 1;
		int ls_count;
		boolean b1;
		chatmsg_db chatmsg_db1;
		ResultSet rs;
		chatmsg_db1 = new chatmsg_db();
		b1 = chatmsg_db1.con_db(ls_servername, ls_portNumber, ls_databaseName,
				ls_userName, ls_password);
		if (b1) {
			rs = chatmsg_db1.query_msg(0);
			ls_count = chatmsg_db1.get_row_count();
			if (ls_count < 1) {
				chatmsg_db1.close_db();
				return;
			}
			if (rs != null) {
				try {

					model.setRowCount(ls_count + 1);
					while (rs.next()) {
						model.setValueAt(rs.getInt(1), ls_i, 0);
						model.setValueAt(rs.getString(2), ls_i, 1);
						model.setValueAt(rs.getDate(3), ls_i, 2);
						model.setValueAt(rs.getString(4), ls_i, 3);
						model.setValueAt(rs.getString(5), ls_i, 4);
						model.setValueAt(rs.getString(6), ls_i, 5);
						ls_i++;
					}
				} catch (Exception s) {
					s.printStackTrace();
				}
			} else {
				System.out.println("参数错误");
			}
			chatmsg_db1.close_db();
		}

	}
}

class u_talbemodel_user2 extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4848458767722038781L;
	boolean editable;

	public u_talbemodel_user2(int row, int column) {
		super(row, column);
		editable = false;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editable;
	}

	public boolean setEditable(boolean editable) {
		this.editable = editable;
		return true;
	}
}
