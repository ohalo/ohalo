package cn.ohalo.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import junit.framework.TestCase;

public class TestMessage extends TestCase {

	public void sMessage() {

		String abc = MessageFormat.format("xyz{0} ,xasd{1}", "asd", "asdas");
		System.out.println(abc);

	}

	@Test
	public void testa() throws Exception {
		System.out.println("testa");
	}

	public void aReplaceStr() throws IOException {
		String path = "G:\\halo_work\\pomelo[柚子]\\接口相关\\other_file\\t_auth_function.sql";
		String opath = "G:\\halo_work\\pomelo[柚子]\\接口相关\\other_file\\t_auth_function__1.sql";
		File f = new File(path);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(opath))));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = replaceStrFormatDate(line);
			bw.write(line + "\r\n");
		}
		bw.flush();
		br.close();
		bw.close();
	}

	public String replaceStrFormatDate(String line) {
		if (line.indexOf("DATE_FORMAT") > -1) {
			String dateFormat = "[0-9]{1,2}[月|\\-][0-9]{1,2}[年|\\-][0-9]{4}";
			Pattern pattern = Pattern.compile(dateFormat);

			Matcher matcher = pattern.matcher(line);

			while (matcher.find()) {
				line = line.replaceAll(matcher.group(), "2013-04-06");
			}
		}

		return line;
	}

	public void aReplaceStr1() {
		String str = "insert into T_AUTH_FUNCTION (ID, FUNCTIONCODE, FUNCTIONNAME, URI, FUNCTIONLEVEL, PARENTCODE, VALIDFLAG, INVALIDDATE, VALIDDATE, DISPLAYORDER, ISCHECK, FUNCTIONTYPE, ISLEAF, ICON, CLS, FUNCTIONDESC, FUNCTIONSEQ, CREATEUSER, CREATEDATE, MODIFYUSER, MODIFYDATE)"
				+ "values (18, '02001010', '任务分配设置', '#!accounting/taskPoolIndex.action', 4, '02001', 1, null, DATE_FORMAT('04-06-2013 09:56:34', '%d-%m-%Y %H:%i:%s'), 10, 1, 3, 1, null, null, '任务分配设置', '0/02/02001/02001010', 0, DATE_FORMAT('2013-04-06 18:46:30', '%d-%m-%Y %H:%i:%s'), 1, DATE_FORMAT('2013-04-06 09:56:34', '%d-%m-%Y %H:%i:%s'));";

		String dateFormat = "DATE_FORMAT('[0-9]{1,2}[月|\\-][0-9]{1,2}[年|\\-][0-9]{4}";

		Pattern pattern = Pattern.compile(dateFormat);

		Matcher matcher = pattern.matcher(str);

		while (matcher.find()) {
			System.out.println(matcher.group());

			System.out.println(str.replaceAll(matcher.group(), "2013-04-06"));

		}
	}
}
