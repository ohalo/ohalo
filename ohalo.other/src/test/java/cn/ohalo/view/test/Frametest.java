package cn.ohalo.view.test;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frametest extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 771600187594997393L;

	public Frametest() {
		try {
			this.setSize(400, 300);
			this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		getContentPane().setLayout(null);
		jButton1.setBounds(new Rectangle(134, 126, 135, 55));
		jButton1.setText("jButton1");
		jButton1.addActionListener(new Frametest_jButton1_actionAdapter(this));
		this.getContentPane().add(jButton1);
	}

	public static void main(String[] args) {
		Frametest frametest = new Frametest();
	}

	JButton jButton1 = new JButton();

	public void jButton1_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "成功");
	}
}

class Frametest_jButton1_actionAdapter implements ActionListener {
	private Frametest adaptee;

	Frametest_jButton1_actionAdapter(Frametest adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}