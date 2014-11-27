package com.ohalo.z.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

class ClientThread extends Thread {

	@SuppressWarnings("rawtypes")
	Vector sline = new Vector();
	boolean LoginSucc = false;
	String user;
	// Socket socket;
	server_user_ob srv_user_ob;
	BufferedReader sin;
	PrintWriter sout;
	PrintWriter log;

	public ClientThread(Socket client, PrintWriter logs) {
		try {
			sin = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			sout = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream()));

			log = logs;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("connection break");
			log.println((new Date()).toString() + "connection break");
			log.flush();
			close();
			return;
		}
		srv_user_ob = new server_user_ob(client, "", "", this);
		this.start();
	}

	public ClientThread getClientThread() {
		return null;
	}

	public void close() {
		try {
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			sout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (LoginSucc) {
				Server.srv_userlist.removeElement(srv_user_ob);
				sendmsgtoall("/RefreshUserList", "所有人");
				sendmsgtoall("/lessuser", "所有人");
				log.println((new Date()).toString() + " "
						+ srv_user_ob.getUsername() + " connection break");
				log.flush();
			}
			srv_user_ob.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized void sendmsgtoall(String msg, String who) {
		PrintWriter sout_2;
		Socket socket_2;
		try {
			if (msg.equals("/RefreshUserList")) {
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					socket_2 = ((server_user_ob) Server.srv_userlist
							.elementAt(i)).getSocket();
					sout_2 = new PrintWriter(new OutputStreamWriter(
							socket_2.getOutputStream()));
					sout_2.println("/RefreshUserList");
					for (int i2 = 0; i2 < Server.srv_userlist.size(); i2++) {
						sout_2.println(((server_user_ob) Server.srv_userlist
								.elementAt(i2)).getUsername());
						sout_2.println(((server_user_ob) Server.srv_userlist
								.elementAt(i2)).getNickname());
					}
					sout_2.println("SendUserList/");
					sout_2.flush();
				}
			} else if (msg.equals("/TALK")) {
				insertmsg(1);
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					socket_2 = ((server_user_ob) Server.srv_userlist
							.elementAt(i)).getSocket();
					sout_2 = new PrintWriter(new OutputStreamWriter(
							socket_2.getOutputStream()));
					sout_2.println("/TALK");
					for (int i2 = 0; i2 < sline.size(); i2++) {
						sout_2.println((String) sline.elementAt(i2));
					}
					sout_2.flush();
				}
			} else if (msg.equals("/SILIAO")) {
				insertmsg(2);
				socket_2 = srv_user_ob.getSocket();
				sout_2 = new PrintWriter(new OutputStreamWriter(
						socket_2.getOutputStream()));
				sout_2.println("/TALK");
				// 对说话者发送私聊的消息.
				for (int i2 = 0; i2 < sline.size(); i2++) {
					sout_2.println((String) sline.elementAt(i2));
				}
				sout_2.flush();
				// 对说话的对象发送私聊的消息
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					if (who.equals(((server_user_ob) Server.srv_userlist
							.elementAt(i)).getUsername())) {
						socket_2 = ((server_user_ob) Server.srv_userlist
								.elementAt(i)).getSocket();
						sout_2 = new PrintWriter(new OutputStreamWriter(
								socket_2.getOutputStream()));
						sout_2.println("/TALK");
						for (int i2 = 0; i2 < sline.size(); i2++) {
							sout_2.println((String) sline.elementAt(i2));
						}
						sout_2.flush();
						break;
					}
				}
			} else if (msg.equals("/lessuser")) {
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					socket_2 = ((server_user_ob) Server.srv_userlist
							.elementAt(i)).getSocket();
					sout_2 = new PrintWriter(new OutputStreamWriter(
							socket_2.getOutputStream()));
					sout_2.println("/lessuser");
					sout_2.println(srv_user_ob.getNickname());
					sout_2.flush();
				}
			} else if (msg.equals("/adduser")) {
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					socket_2 = ((server_user_ob) Server.srv_userlist
							.elementAt(i)).getSocket();
					sout_2 = new PrintWriter(new OutputStreamWriter(
							socket_2.getOutputStream()));
					sout_2.println("/adduser");
					sout_2.println(srv_user_ob.getUsername());
					sout_2.println(srv_user_ob.getNickname());
					sout_2.flush();
				}
			}

		} catch (IOException e) {
			// e.printStackTrace();
			log.println((new Date()).toString() + " "
					+ srv_user_ob.getUsername() + " connection break");
			log.flush();

			close();
			return;
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void run() {
		String line, pass, manager;
		PrintWriter sout_2;
		Socket socket_2;
		String ls_user;
		String ls_dowhat;
		// login auth
		try {
			ls_dowhat = sin.readLine();
			if (ls_dowhat.equals("login")) {
				ls_user = sin.readLine();
				pass = sin.readLine();
				manager = sin.readLine();
				checkLogin(ls_user, pass, manager);
			} else if (ls_dowhat.equals("regedit")) {
				reguser();
				return;
			} else if (ls_dowhat.equals("checksameuserid")) {
				check_same_userid();
				return;
			} else if (ls_dowhat.equals("/request_ini")) {
				sout.println(Server.serverName);
				sout.println(Server.portNumber);
				sout.println(Server.databaseName);
				sout.println(Server.userName);
				sout.println(Server.password);
				sout.flush();
				close();
				return;
			}
		} catch (IOException e) {
			// e.printStackTrace();
			sout.println("auth fail");
			sout.flush();
			System.out.println("connection break");
			close();
			return;
		} catch (AuthException e) {
			// e.printStackTrace();
			if (e.toString().equals("same user login")) {
				sout.println("same user login");
				sout.flush();
			} else if (e.toString().equals("user waiting confirm")) {
				sout.println("user waiting confirm");
				sout.flush();
			} else {

				sout.println("auth fail");
				sout.flush();

			}
			System.out.println("auth fail");
			close();
			return;
		}
		sout.println("login succ");
		sout.flush();
		Server.srv_userlist.addElement(srv_user_ob);
		sendmsgtoall("/RefreshUserList", "所有人");
		sendmsgtoall("/adduser", "所有人");
		try {
			while (true) {
				line = sin.readLine();
				if (line.equals("/logout"))
					break;
				if (line.equals("/TALK")) {
					//
					while (line.equals("TALK/") == false) {
						line = sin.readLine();
						sline.addElement(line);
					}
					sendmsgtoall("/TALK", "所有人");
					sline.clear();

				}
				if (line.equals("/SILIAO")) {
					String towho;
					towho = sin.readLine();
					while (line.equals("TALK/") == false) {
						line = sin.readLine();
						sline.addElement(line);
					}
					sendmsgtoall("/SILIAO", towho);
					sline.clear();
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
			close();
			return;
		}
	}

	public void checkLogin(String user, String pass, String manager)
			throws AuthException {
		boolean b1;
		String nickname;
		user_db user_db1;
		String username;
		user_db1 = new user_db();
		b1 = user_db1.con_db();
		if (b1) {
			if (user_db1.Check_user_pass(user, pass, manager)) {
				// 登录成功
				nickname = user_db1.get_user_nickname(user);
				srv_user_ob.setUsername(user);
				srv_user_ob.setNickname(nickname);
				for (int i = 0; i < Server.srv_userlist.size(); i++) {
					username = ((server_user_ob) Server.srv_userlist
							.elementAt(i)).getUsername();
					if (username.equals(user)) {
						LoginSucc = false;
						throw new AuthException("same user login");
					}
				}
				if (user_db1.check_user_regsuc(user) == false)
					throw new AuthException("user waiting confirm");
				log.println((new Date()).toString() + " " + user
						+ " login succ");
				log.flush();
				LoginSucc = true;
				return;
			} else {
				// 登录失败
				LoginSucc = false;
				log.println((new Date()).toString() + " " + user
						+ " login fail");
				log.flush();
				throw new AuthException("login fail");
			}
		} else {
			log.println((new Date()).toString() + " " + user
					+ " login fail(DB CONNECT FAILE)");
			log.flush();
			throw new AuthException("login fail");
		}
	}

	public void check_same_userid() {
		boolean b1;
		String ls_username;
		user_db user_db1;
		try {
			ls_username = sin.readLine();
			user_db1 = new user_db();
			b1 = user_db1.con_db();
			if (b1) {
				if (user_db1.Check_same_userid(ls_username)) {
					sout.println("same_username");
					sout.flush();
					close();
					return;
				} else {
					sout.println("ok_to_regedit");
					sout.flush();
					close();
					return;
				}
			} else {
				sout.println("db_connect_fail");
				sout.flush();
				log.println("check same userid fail because db connect fail.");
				close();
				return;
			}
		} catch (Exception e) {
			close();
			return;
		}

	}

	public void insertmsg(int ai_1) {
		String ls_userid;
		String ls_chataction;
		String ls_touserid;
		String ls_chatmsg;
		boolean b1;
		boolean b2;
		ls_userid = (String) sline.elementAt(0);
		if (ai_1 == 1) {
			ls_chataction = "公";
		} else {
			ls_chataction = "私";
		}

		ls_touserid = (String) sline.elementAt(2);
		ls_chatmsg = (String) sline.elementAt(4);
		for (int i = 5; i < sline.size() - 1; i++) {
			ls_chatmsg = ls_chatmsg + "\n" + (String) sline.elementAt(i);
		}
		chatmsg_db msg_db1 = new chatmsg_db();
		b1 = msg_db1.con_db();
		if (b1) {
			b2 = msg_db1.insert_msg(ls_userid, ls_touserid, ls_chatmsg,
					ls_chataction);
			System.out.println(b2);
			if (b2 == false) {
				log.println("insert chatmsg fail");
			}
		} else {
			log.println("insert chatmsg fail because db connect fail.");
		}
	}

	public void reguser() {
		String ls_username;
		String ls_password;
		String ls_name;
		String ls_nickname;
		String ls_sex;
		String ls_web;
		String ls_email;
		String ls_person_des;
		boolean b1;
		try {
			ls_username = sin.readLine();
			ls_password = sin.readLine();
			ls_name = sin.readLine();
			ls_nickname = sin.readLine();
			ls_sex = sin.readLine();
			;
			ls_web = sin.readLine();
			;
			ls_email = sin.readLine();
			;
			ls_person_des = sin.readLine();
			;

			user_db user_db1 = new user_db();
			b1 = user_db1.con_db();
			if (b1) {
				if (user_db1.Check_same_userid(ls_username)) {
					sout.println("same_username");
					sout.flush();
					close();
					return;
				} else {
					if (user_db1.insert_user(ls_username, ls_password, ls_name,
							ls_nickname, ls_sex, ls_web, ls_email,
							ls_person_des)) {
						sout.println("reg_succeed");
						sout.flush();
						close();
						return;
					} else {
						sout.println("reg_fail");
						sout.flush();
						close();
						return;
					}
				}
			} else {
				sout.println("db_connect_fail");
				sout.flush();
				log.println("reg fail because db connect fail.");
				close();
				return;
			}
		} catch (IOException e) {
			// e.printStackTrace();
			close();
			return;
		}
	}

}

class AuthException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7195337356609178864L;
	String mess;

	public AuthException(String mess) {
		this.mess = mess;
	}

	public String toString() {
		return mess;
	}

	public String getMessage() {
		return mess;
	}
}
