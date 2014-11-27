package cn.ohalo.view.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.*;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private int width = 400;
	private int height = 300;
	private static Toolkit kit = Toolkit.getDefaultToolkit();
	JTabbedPane jpan = new JTabbedPane();
	int pnum = 4;
	JPanel[] pans = new JPanel[pnum];
	String[] titles = { "时间管理", "课程提醒", "定时关机", "帮助" };

	public MyFrame() {
		this.defaultInit();
	}

	public MyFrame(String title) {
		super(title);
		this.defaultInit();
	}

	public MyFrame(String title, int width, int height) {
		this(title);
		this.width = width;
		this.height = height;
		this.defaultInit();
	}

	private void defaultInit() {
		for (int i = 0; i < pnum; i++) {
			pans[i] = new JPanel();
			jpan.addTab(titles[i], pans[i]);
		}
		Dimension d = kit.getScreenSize();
		this.setLayout(new BorderLayout());
		this.add(jpan, BorderLayout.CENTER);
		this.setSize(width, height);
		this.setLocation((int) (d.getWidth() - width) / 2,
				(int) (d.getHeight() - height) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MyFrame();
	}

	public static Toolkit getKit() {
		return kit;
	}

}