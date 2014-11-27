package cn.ohalo.job.search.fiveone.entity;

import java.io.Serializable;

/**
 * <pre>
 * -----------------------------------------------------------------------------------------------------------------
 *   职位名称    |   公司名称     |   工作地点      |   更新日期     |  {隐藏字段:jobUrlAddress(工作连接地址),jobCompanyUrlAddress(公司连接地址)}
 * -----------------------------------------------------------------------------------------------------------------
 * 
 * 
 * 所以我们需要创建一个对象叫：
 * FiveOneJobSearchResult{jobCode,jobName,companyName,jobAddress,jobUpdateDate,jobUrlAddress,jobCompanyUrlAddress}
 * </pre>
 * 
 * @author halo
 * @since 2013-10-5 下午6:30:47
 */
public class FiveOneJobSearchResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4276052107177780676L;

	/**
	 * 工作编码
	 */
	private String jobCode;

	/**
	 * 工作名称
	 */
	private String jobName;

	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 工作地址（只是大概的如：上海，上海-徐汇）
	 */
	private String jobAddress;

	/**
	 * 工作更新时间
	 */
	private String jobUpdateDate;
	/**
	 * 在51job中的url连接地址
	 */
	private String jobUrlAddress;

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobAddress() {
		return jobAddress;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}

	public String getJobUpdateDate() {
		return jobUpdateDate;
	}

	public void setJobUpdateDate(String jobUpdateDate) {
		this.jobUpdateDate = jobUpdateDate;
	}

	public String getJobUrlAddress() {
		return jobUrlAddress;
	}

	public void setJobUrlAddress(String jobUrlAddress) {
		this.jobUrlAddress = jobUrlAddress;
	}

	public String getJobCompanyUrlAddress() {
		return jobCompanyUrlAddress;
	}

	public void setJobCompanyUrlAddress(String jobCompanyUrlAddress) {
		this.jobCompanyUrlAddress = jobCompanyUrlAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FiveOneJobSearchResult [jobCode=" + jobCode + ", jobName="
				+ jobName + ", companyName=" + companyName + ", jobAddress="
				+ jobAddress + ", jobUpdateDate=" + jobUpdateDate
				+ ", jobUrlAddress=" + jobUrlAddress
				+ ", jobCompanyUrlAddress=" + jobCompanyUrlAddress + "]";
	}

	/**
	 * 在51job 中的公司的连接地址
	 */
	private String jobCompanyUrlAddress;

}
