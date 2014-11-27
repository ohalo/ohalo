package com.ohalo.test;


public class TestThread extends Thread {

	public TestThread() {
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread:sleep" + 1000);
	}

	public static void main(String[] args) {
		TestThread test = new TestThread();
		test.setDaemon(true);
		test.start();
//		try {
//			test.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("isDaemon:sleep" + 1000);
	}
}
