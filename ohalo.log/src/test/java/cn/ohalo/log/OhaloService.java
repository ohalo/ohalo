package cn.ohalo.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class OhaloService {

	public void test(String name) {
		System.out.println("你好：" + name);
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> queue = new LinkedBlockingDeque<String>();
		queue.put("a1");
		queue.add("a2");
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
	}
}
