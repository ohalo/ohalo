package org.ohalo.pomelo.result;

import java.util.List;

import org.ohalo.pomelo.result.Result;

public class UserResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5103162572995190156L;

	private String uAccount;

	private String uPassword;

	private Person person;

	private List<Person> relPersons;

	/**
	 * @return the uAccount
	 */
	public String getuAccount() {
		return uAccount;
	}

	/**
	 * @param uAccount
	 *            the uAccount to set
	 */
	public void setuAccount(String uAccount) {
		this.uAccount = uAccount;
	}

	/**
	 * @return the uPassword
	 */
	public String getuPassword() {
		return uPassword;
	}

	/**
	 * @param uPassword
	 *            the uPassword to set
	 */
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Person> getRelPersons() {
		return relPersons;
	}

	public void setRelPersons(List<Person> relPersons) {
		this.relPersons = relPersons;
	}

}