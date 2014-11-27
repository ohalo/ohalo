package com.deppon.crm.module.common.file.util;

public interface IExcelReader {

	public boolean hasNextRow();

	public Object[] getNextRow();

	public String[] getHeader();

}
