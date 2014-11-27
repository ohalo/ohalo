package org.ohalo.pomelo;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testStr() {
		String str = "1,0,10,20131101&#$%&2,0,10,201311011&#$%&3,0,10,201311011&#$%&4,0,10,20131101";

		String[] strs = str.split("\\&\\#\\$\\%\\&");
		System.out.println(Arrays.toString(strs));
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		String candidate = "18657133593";
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(candidate);
		String val = null;
		System.out.println("INPUT: " + candidate);
		System.out.println("REGEX: " + regex + "\r\n");
		while (m.find()) {
			val = m.group();
			System.out.println("MATCH: " + val);
		}
		if (val == null) {
			System.out.println("NO MATCHES: ");
		}
	}
}
