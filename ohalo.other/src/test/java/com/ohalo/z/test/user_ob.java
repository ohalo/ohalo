package com.ohalo.z.test;

public class user_ob {
	String username;
	String nickname;
	public user_ob(String as_username, String as_nickname) {
		username = as_username;
		nickname = as_nickname;
	}
	public String toString() {
		return nickname;
	}
	public String getnickname() {
		return nickname;
	}
	public String getUsername() {
		return username;
	}
}
