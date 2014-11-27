package cn.ohalo.test;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * 
 * <pre>
 * 功能：FunctionUtilsTest 
 * 作者：赵辉亮
 * 日期：2013-5-31下午2:15:52
 * </pre>
 */
public class FunctionUtilsTest extends TestCase {

	@SuppressWarnings("unused")
	public void importExcel() throws IOException {

		OPCPackage openPackage = XSSFWorkbook
				.openPackage("d:\\桌面\\报账系统功能菜单角色授权表_2.xlsx");

		// 返回校验结果
		String res = "";
		// 创建一个Excel book
		XSSFWorkbook wb = new XSSFWorkbook(openPackage);
		// 获取第一个sheet页
		XSSFSheet sheet = wb.getSheetAt(1);

		System.out.println(sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);

			XSSFCell cell0 = row.getCell(0);
			XSSFCell cell1 = row.getCell(1);
			XSSFCell cell2 = row.getCell(2);
			XSSFCell cell3 = row.getCell(3);
			XSSFCell cell4 = row.getCell(4);
			XSSFCell cell5 = row.getCell(5);
			XSSFCell cell6 = row.getCell(6);

			System.out.println(cell0.getStringCellValue());
		}
	}
	
	@Test
	public void testa() throws Exception {
		System.out.println("testa");
	}

	public void aimportExcel() {
		try {
			importExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
