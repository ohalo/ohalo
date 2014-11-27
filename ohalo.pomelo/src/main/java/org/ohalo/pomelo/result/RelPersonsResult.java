package org.ohalo.pomelo.result;

import java.util.List;

/**
 * 关联人员信息
 * 
 * @author halo
 * @since 2013年11月1日 上午9:11:52
 */
public class RelPersonsResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1287370635925438075L;

	/**
	 * 关联用户账户名称
	 */
	private String uAccount;

	/**
	 * 用户类型：1.我是柚子。2.我是父母
	 */
	private String uziType;

	/**
	 * 关联人员信息
	 */
	private List<Person> relpersons;

	public RelPersonsResult(String uAccount, String uziType,
			List<Person> relpersons) {
		super();
		this.uAccount = uAccount;
		this.uziType = uziType;
		this.relpersons = relpersons;
	}

	public String getuAccount() {
		return uAccount;
	}

	public void setuAccount(String uAccount) {
		this.uAccount = uAccount;
	}

	public List<Person> getRelpersons() {
		return relpersons;
	}

	public void setRelpersons(List<Person> relpersons) {
		this.relpersons = relpersons;
	}

	@Override
	public String toString() {
		return "RelPersonsResult [uAccount=" + uAccount + ", uziType="
				+ uziType + ", relpersons=" + relpersons + "]";
	}

	public String getUziType() {
		return uziType;
	}

	public void setUziType(String uziType) {
		this.uziType = uziType;
	}
}
