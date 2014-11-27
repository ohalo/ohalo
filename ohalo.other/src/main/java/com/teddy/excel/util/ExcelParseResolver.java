package com.teddy.excel.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.translate.ui.SwingPrintStream;

public class ExcelParseResolver {

	private IStrategy strategy = null;	
	
	private boolean isWriteable = false;
	
	private boolean isExcel2007 = true;	
	
	private String sourceFilePath = null;
	
	private String targetFilePath = null;

	private ProxySetting setting;
	
	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}

	public String getTargetFilePath() {
		return targetFilePath;
	}

	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
	}

	public ProxySetting getSetting() {
		return setting;
	}

	public void setSetting(ProxySetting setting) {
		this.setting = setting;
	}

	public boolean isExcel2007() {
		return isExcel2007;
	}

	public void setExcel2007(boolean isExcel2007) {
		this.isExcel2007 = isExcel2007;
	}

	public IStrategy getStrategy() {
		return strategy;                       
	}

	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public boolean isWriteable() {
		return isWriteable;
	}

	public void setWriteable(boolean isWriteable) {
		this.isWriteable = isWriteable;
	}
	
	public void processExcel(String sourceName){
		loadExcel(sourceName);		
	}
		
	public void processExcel(SwingPrintStream ps){
		if(ps != null){
			System.setOut(ps);
			System.setErr(ps);
		}	
		
		Workbook workBook = loadExcel(sourceFilePath);
		if(isWriteable){
			write(targetFilePath, workBook);
		}
	}
	
	private void write(String fileName, Workbook workBook){
		FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private Workbook loadExcel(String sourceFileName) {		
		Workbook workBook = null;
		try {	
			if(isExcel2007){
				workBook = new XSSFWorkbook(new FileInputStream(sourceFileName));
				System.out.println("[Load Excel 2007:] " + sourceFileName);
			}else{
				workBook = new HSSFWorkbook(new FileInputStream(sourceFileName));
				System.out.println("[Load Excel 2003:] " + sourceFileName);
			}
			readAllRows(workBook);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workBook;
	}

	private ArrayList<String[]> readAllRows(Workbook workbook) {
		ArrayList<String[]> rowList = new ArrayList<String[]>();
		try {
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;

			System.out.println("[Sum of excel sheets:] "
					+ workbook.getNumberOfSheets());
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				System.out.println("[Sheets At:] " + numSheet);
				sheet = workbook.getSheetAt(numSheet);
				int rowNum = 0;
				for (Iterator<?> rows = sheet.iterator(); rows.hasNext(); rowNum++) {
					row = (Row)rows.next();
					int col = 0;
					int lastCellNum = (int) row.getLastCellNum();
					String[] aCells = new String[lastCellNum];

					while (col < lastCellNum) {
						try {
							cell = row.getCell(col);
							aCells[col] = readXSSFAllCell(cell);
							System.out.println("[Cell at row " + rowNum
									+ " at col " + col + ":] " + aCells[col]);							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						col++;
					}
					boolean notBlankLine = false;
					for (int k = 0; k < aCells.length; k++) {
						if (aCells[k] != null && aCells[k].length() > 0) {
							notBlankLine = true;
							break;
						}
					}
					if (notBlankLine) {
						rowList.add(aCells);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rowList;
	}

	private String readXSSFAllCell(Cell xCell) throws Exception {
		String content = null;
		
		if(xCell == null){
			return "";
		}		
		
		if (xCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			content = Boolean.toString(xCell.getBooleanCellValue());			
		} else if (xCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			content = Double.toString(xCell.getNumericCellValue());			
		} else {
			content = xCell.getStringCellValue();			
		}
		
		if (strategy != null) {
			content = strategy.process(content, setting);
		}
		if(isWriteable){
			xCell.setCellValue(content);
		}
		return content;
	}
}
