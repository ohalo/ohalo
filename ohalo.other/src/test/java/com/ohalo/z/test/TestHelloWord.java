package com.ohalo.z.test;

import java.io.IOException;

public class TestHelloWord {

	public static void main(String[] args) {
		System.out.println("helloWorld");
		
		try {
			Runtime.getRuntime().exec("cmd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
