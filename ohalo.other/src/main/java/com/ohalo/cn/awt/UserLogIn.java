package com.ohalo.cn.awt;

import javax.swing.JFrame;//框架
import javax.swing.JPanel;//面板
import javax.swing.JButton;//按钮
import javax.swing.JLabel;//标签
import javax.swing.JTextField;//文本框
import java.awt.Font;//字体
import java.awt.Color;//颜色
import javax.swing.JPasswordField;//密码框
import java.awt.event.ActionListener;//事件监听
import java.awt.event.ActionEvent;//事件处理
import javax.swing.JOptionPane;//消息窗口

public class UserLogIn extends JFrame {
	/***
   * 
   */
	private static final long serialVersionUID = 7668305419405026588L;
	public JPanel pnluser;
	public JLabel lbluserLogIn;
	public JLabel lbluserName;
	public JLabel lbluserPWD;
	public JTextField txtName;
	public JPasswordField pwdPwd;
	public JButton btnSub;
	public JButton btnReset;

	public UserLogIn() {
		pnluser = new JPanel();
		lbluserLogIn = new JLabel();
		lbluserName = new JLabel();
		lbluserPWD = new JLabel();
		txtName = new JTextField();
		pwdPwd = new JPasswordField();
		btnSub = new JButton();
		btnReset = new JButton();
		userInit();
	}

	public void userInit() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭框架的同时结束程序
		this.setSize(300, 200);// 设置框架大小为长300,宽200
		this.setResizable(false);// 设置框架不可以改变大小
		this.setTitle("用户登录");// 设置框架标题
		this.pnluser.setLayout(null);// 设置面板布局管理
		this.pnluser.setBackground(Color.cyan);// 设置面板背景颜色
		this.lbluserLogIn.setText("用户登录");// 设置标签标题
		this.lbluserLogIn.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 14));// 设置标签字体
		this.lbluserLogIn.setForeground(Color.RED);// 设置标签字体颜色
		this.lbluserName.setText("用户名:");
		this.lbluserPWD.setText("密    码:");
		this.btnSub.setText("登录");
		this.btnReset.setText("重置");
		this.lbluserLogIn.setBounds(120, 15, 60, 20);// 设置标签x坐标120,y坐标15,长60,宽20
		this.lbluserName.setBounds(50, 55, 60, 20);
		this.lbluserPWD.setBounds(50, 85, 60, 25);
		this.txtName.setBounds(110, 55, 120, 20);
		this.pwdPwd.setBounds(110, 85, 120, 20);
		this.btnSub.setBounds(85, 120, 60, 20);
		this.btnSub.addActionListener(new ActionListener()// 匿名类实现ActionListener接口
				{
					public void actionPerformed(ActionEvent e) {
						btnsub_ActionEvent(e);
					}
				});
		this.btnReset.setBounds(155, 120, 60, 20);
		this.btnReset.addActionListener(new ActionListener()// 匿名类实现ActionListener接口
				{
					public void actionPerformed(ActionEvent e) {
						btnreset_ActionEvent(e);
					}
				});
		this.pnluser.add(lbluserLogIn);// 加载标签到面板
		this.pnluser.add(lbluserName);
		this.pnluser.add(lbluserPWD);
		this.pnluser.add(txtName);
		this.pnluser.add(pwdPwd);
		this.pnluser.add(btnSub);
		this.pnluser.add(btnReset);
		this.add(pnluser);// 加载面板到框架
		this.setVisible(true);// 设置框架可显
	}

	@SuppressWarnings("unused")
	public void btnsub_ActionEvent(ActionEvent e) {
		String name = txtName.getText();
		String pwd = String.valueOf(pwdPwd.getPassword());
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "账号不能为空", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (pwd.equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (true) {
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "账号或密码错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void btnreset_ActionEvent(ActionEvent e) {
		txtName.setText("");
		pwdPwd.setText("");
	}

	public static void main(String[] args) {
		new UserLogIn();
	}
}
