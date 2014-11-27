package com.ohalo.z.test;

import java.net.*;
public class server_user_ob {
	Socket user_socket;
	String user_name;
	String nick_name;
	ClientThread client_thread;
	public server_user_ob(Socket ast_socket, String as_username,
			String as_nickname, ClientThread act) {
		user_socket = ast_socket;
		user_name = as_username;
		nick_name = as_nickname;
		client_thread = act;
	}
	public String toString() {
		return user_name;
	}
	public String getUsername() {
		return user_name;
	}
	public String getNickname() {
		return nick_name;
	}
	public Socket getSocket() {
		return user_socket;
	}
	public void setUsername(String as_username) {
		user_name = as_username;
	}
	public void setNickname(String as_nickname) {
		nick_name = as_nickname;
	}
	public ClientThread getClientThread() {
		return client_thread;
	}
}