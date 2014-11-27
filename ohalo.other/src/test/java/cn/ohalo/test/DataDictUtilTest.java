package cn.ohalo.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDictUtilTest extends TestCase {

	private static Set<String> idSet = new HashSet<String>();

	public void importExcel(String path) throws IOException {

		OPCPackage openPackage = XSSFWorkbook.openPackage(path);

		// 返回校验结果
		// String res = "";
		// 创建一个Excel book
		XSSFWorkbook wb = new XSSFWorkbook(openPackage);

		// try {
		// XSSFSheet sheet1 = wb.getSheetAt(1);
		// importFirstSheet(sheet1);
		//
		// } catch (Exception e) {
		// }

		try {
			XSSFSheet sheet2 = wb.getSheetAt(0);
			importSecondSheet(sheet2);
		} catch (Exception e) {
		}

	}

	private void importSecondSheet(XSSFSheet sheet) {
		// 获取第一个sheet页
		System.out.println(sheet.getLastRowNum());

		StringBuffer sbs = new StringBuffer();

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell19 = row.getCell(0);
			sbs.append("'").append(cell19.toString()).append("',");
		}
		System.out.println(sbs.toString());
	}

	@SuppressWarnings("unused")
	public void importFirstSheet(XSSFSheet sheet) {
		// 获取第一个sheet页
		System.out.println(sheet.getLastRowNum());

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);

			// XSSFCell cell0 = row.getCell(0);
			// XSSFCell cell1 = row.getCell(1);
			// XSSFCell cell2 = row.getCell(2);

			XSSFCell cell3 = row.getCell(3);
			XSSFCell cell4 = row.getCell(4);
			XSSFCell cell5 = row.getCell(5);

			Object Id = getObjectValue(cell3);
			Object itemCode = getObjectValue(cell4);
			Object itemName = getObjectValue(cell5);

			if (Id == null || itemCode == null) {
				continue;
			}

			String idstr = Id.toString();

			if (idSet.contains(idstr)) {
				continue;
			}

			idSet.add(idstr);
		}
	}

	public Object getObjectValue(XSSFCell cell) {

		int cellType = cell.getCellType();

		switch (cellType) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			break;
		}
		return null;
	}

	public void testBoolean() {
		Boolean boo = Boolean.valueOf("1");
		System.out.println(boo);
	}

	public void aExcel() {
		try {
			importExcel("d:\\桌面\\工作流状态不一致统计—网二.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void aText() throws IOException {
		String ss = FileUtils
				.readFileToString(new File(
						"E:\\halo_source\\halo_source\\halo_code\\ohalo\\ohalo.other\\src\\test\\java\\sql\\存在问题的sql语句.txt"));
		System.out.println(ss.replaceAll("\r\n", "','"));
	}

	public void aTQClaimBillNO() {
		String xmlInfo = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<eisEvaluationResponse xmlns=\"http://www.deppon.com/fssc/remote/eis/domain/type\">"
				+ "<claimBillNumber>FSSC107131012001639</claimBillNumber>"
				+ "<successMark>true</successMark>"
				+ "<failtureReason></failtureReason>"
				+ "<evaluationType>NORMAL_THROUGH</evaluationType>"
				+ "</eisEvaluationResponse>";

		if (xmlInfo.indexOf("eisEvaluationResponse") > -1
				&& xmlInfo.indexOf("NORMAL_THROUGH") > -1) {
			xmlInfo = xmlInfo.substring(xmlInfo.indexOf("<claimBillNumber>")
					+ "<claimBillNumber>".length(),
					xmlInfo.indexOf("</claimBillNumber>"));
			System.out.println(xmlInfo);
		}

	}

	@SuppressWarnings("unused")
	public void testUnKnowNo() {
		String claimNo = "FSSC101131008002918,FSSC101131008001686,FSSC106131012000020,FSSC107131010001882,FSSC101131011000198,FSSC101130927001444,FSSC107131010001634,FSSC107131009000670";
	}
}
