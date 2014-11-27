package cn.ohalo.interview.entity;

import org.apache.commons.lang.StringUtils;

import cn.ohalo.company.entity.CompanyEntity;
import cn.ohalo.db.mongodb.MongoBaseEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 面试信息
 * 
 * @author halo
 * 
 */
public class InterViewInfo extends MongoBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2409527340274355221L;

	/**
	 * 面试信息唯一标识
	 */
	private String code;

	/**
	 * 所属公司
	 */
	private CompanyEntity company;

	/**
	 * 面试职位
	 */
	private String jobName;

	/**
	 * 面试描述
	 */
	private String desc;

	/**
	 * 面试题
	 */
	private String subject;

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public DBObject toDBObject() {
		DBObject dbobject = new BasicDBObject();
		if (StringUtils.isNotBlank(code)) {
			dbobject.put("code", code);
		}
		if (company != null) {
			dbobject.put("company", company.toDBObject());
		}
		if (StringUtils.isNotBlank(jobName)) {
			dbobject.put("jobName", jobName);
		}
		if (StringUtils.isNotBlank(desc)) {
			dbobject.put("desc", desc);
		}
		if (StringUtils.isNotBlank(subject)) {
			dbobject.put("subject", subject);
		}
		return dbobject;
	}

	@Override
	public String toString() {
		return "InterViewInfo [code=" + code + ", company=" + company
				+ ", jobName=" + jobName + ", desc=" + desc + ", subject="
				+ subject + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
