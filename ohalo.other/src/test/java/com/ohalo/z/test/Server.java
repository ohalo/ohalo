package com.ohalo.z.test;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8416601136038837292L;
	@SuppressWarnings("rawtypes")
	static Vector srv_userlist = new Vector();
	static String logFile = "log.txt";
	ServerSocket serverSocket;
	PrintWriter log;
	JButton jb_start, jb_stop;
	JLabel message, jl_port;
	JTextField jf_port;
	ServerThread srv_thread;
	static String serverName;
	static String portNumber;
	static String databaseName;
	static String userName;
	static String password;

	// public static void initAuthFile() throws Exception {
	// String ups[] = new String[] {"hackgun", "1234", "Wakao",
	// "1234","lala0","1234"};
	// DataOutputStream dos = new DataOutputStream(new
	// FileOutputStream(authFile));
	// for (int i = 0; i < ups.length; i++) {
	// dos.writeUTF(ups[i]);
	// }
	// dos.close();
	// }
	public Server() {
		super("聊天室服务");
		if (read_ini() == false) {
			JOptionPane.showMessageDialog(null, "ini文件读入错误,请检查!");
			System.exit(0);
		}
		try {
			log = new PrintWriter(new FileWriter(new File(logFile), true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		jf_port = new JTextField("10001");
		jb_start = new JButton("开启服务");
		jb_stop = new JButton("停止服务");
		message = new JLabel("聊天室服务                        ");
		jl_port = new JLabel("端口号 ");

		GridBagConstraints gBC = new GridBagConstraints();
		Insets is = new Insets(5, 5, 5, 5);

		Container thisCP;
		thisCP = getContentPane();
		thisCP.setLayout(new GridBagLayout());

		gBC.anchor = GridBagConstraints.CENTER;
		gBC.fill = GridBagConstraints.NONE;
		gBC.gridwidth = 2;
		gBC.gridheight = 1;
		gBC.insets = is;
		gBC.gridy = 1;
		gBC.gridx = 1;
		thisCP.add(message, gBC);
		gBC.gridy = 2;
		gBC.gridx = 1;
		gBC.gridwidth = 1;

		gBC.anchor = GridBagConstraints.EAST;
		thisCP.add(jl_port, gBC);
		gBC.anchor = GridBagConstraints.WEST;
		gBC.gridx = 2;
		thisCP.add(jf_port, gBC);
		gBC.gridy = 3;
		gBC.gridx = 1;
		thisCP.add(jb_start, gBC);
		gBC.gridx = 2;
		thisCP.add(jb_stop, gBC);
		this.setSize(200, 150);
		this.setVisible(true);
		jb_stop.setEnabled(false);

		jb_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				startServer();
			}
		});
		jb_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				stopServer();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					stopServer();
					log.println((new Date()).toString() + " server close");
					log.flush();
					log.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.exit(0);
			}
		});
		startServer();

	}

	public void startServer() {
		srv_thread = new ServerThread(this);
	}

	@SuppressWarnings("deprecation")
	public void stopServer() {
		try {
			PrintWriter sout_2;
			Socket socket_2;
			ClientThread ct;
			for (int i = 0; i < Server.srv_userlist.size(); i++) {
				socket_2 = ((server_user_ob) Server.srv_userlist.elementAt(i))
						.getSocket();
				sout_2 = new PrintWriter(new OutputStreamWriter(
						socket_2.getOutputStream()));
				ct = ((server_user_ob) Server.srv_userlist.elementAt(i))
						.getClientThread();
				sout_2.println("/TALK");
				sout_2.println("系统\n对\n所有人\n说:\n服务器因故停止服务...");
				sout_2.println("TALK/");
				sout_2.println("STOP_SERVER/");
				sout_2.flush();
				sout_2.close();
				socket_2.close();
				ct.stop();
			}
			srv_userlist.clear();
			serverSocket.close();
			message.setText("聊天室终止服务");
			jb_start.setEnabled(true);
			jb_stop.setEnabled(false);
			log.println((new Date()).toString() + " server close");
		} catch (IOException eE) {
			System.out.println("出错了");
		}

	}

	@SuppressWarnings("resource")
	public boolean read_ini() {
		BufferedReader read;
		String s;
		try {
			read = new BufferedReader(new FileReader("db_ini.txt"));
			if ((s = read.readLine()) != null) {
				serverName = s;
			} else
				return false;
			if ((s = read.readLine()) != null) {
				portNumber = s;
			} else
				return false;
			if ((s = read.readLine()) != null) {
				databaseName = s;
			} else
				return false;
			if ((s = read.readLine()) != null) {
				userName = s;
			} else
				return false;
			if ((s = read.readLine()) != null) {
				password = s;
			} else
				return false;
			read.close();
		} catch (IOException e) {
			//
		}
		return true;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Server sr = new Server();
	}
}

class ServerThread extends Thread {
	Server Srv;
	boolean starserver = false;

	public ServerThread(Server asrv_main) {
		Srv = asrv_main;
		this.start();
	}

	@SuppressWarnings("unused")
	public void run() {
		try {
			Srv.serverSocket = new ServerSocket(Integer.valueOf(Srv.jf_port
					.getText()));
			starserver = true;
			Srv.message.setText("聊天室服务开启中...");
			Srv.jb_start.setEnabled(false);
			Srv.jb_stop.setEnabled(true);
			Srv.log.println((new Date()).toString() + " server start");
			Srv.log.flush();
			while (true) {
				Socket clientSocket = Srv.serverSocket.accept();
				ClientThread ct = new ClientThread(clientSocket, Srv.log);
			}
		} catch (Exception e) {
			if (starserver == false) {
				JOptionPane.showMessageDialog(null, "端口号已经被占用");
			} else {
				Srv.log.println((new Date()).toString()
						+ " server close unexpectly");
				Srv.log.flush();
			}

			starserver = false;
			// e.printStackTrace();
		}
	}

}