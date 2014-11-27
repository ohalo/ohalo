package org.ohalo.pomelo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.ohalo.base.service.BaseService;
import org.ohalo.base.utils.Guid;
import org.ohalo.base.utils.TimeUtils;
import org.ohalo.db.HaloDBException;
import org.ohalo.db.page.Pagination;
import org.ohalo.pomelo.config.ParamsConfig;
import org.ohalo.pomelo.db.PersonDb;
import org.ohalo.pomelo.db.PersonRelDb;
import org.ohalo.pomelo.db.UserDb;
import org.ohalo.pomelo.entity.PersonInfo;
import org.ohalo.pomelo.entity.PersonRelInfo;
import org.ohalo.pomelo.entity.UserInfo;
import org.ohalo.pomelo.result.Person;
import org.ohalo.pomelo.result.RelPersonsResult;
import org.ohalo.pomelo.result.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service("pomeloUserService")
public class UserService implements BaseService {

	@Autowired
	private UserDb userdb;

	@Autowired
	private PersonDb persondb;

	@Autowired
	private PersonRelDb personreldb;

	/**
	 * 保存用户信息
	 * 
	 * @param nickName
	 * @param userName
	 * @param pwd
	 * @param phoneNum
	 * @param email
	 */
	@ExceptionHandler(value = Exception.class)
	@Transactional
	public void saveUserInfo(String nickName, String userName, String pwd,
			String phoneNum, String email, String uziType) {
		PersonInfo personInfo = null;
		if (StringUtils.isNotBlank(phoneNum)) {
			personInfo = persondb.findBypPhone(phoneNum);
		} else {
			personInfo = new PersonInfo();
			personInfo.setPid(Guid.newDCGuid(ParamsConfig.PERSON_STUFFIX));
			personInfo.setCreateTime(TimeUtils.getSystemTime());
		}

		personInfo.setpName(nickName);
		personInfo.setpPhone(phoneNum);
		personInfo.setpEmail(email);
		personInfo.setuName(userName);

		if (StringUtils.isNotBlank(phoneNum)) {
			persondb.update(personInfo);
		} else {
			persondb.save(personInfo);
		}
		UserInfo user = new UserInfo(userName, pwd,
				Guid.newDCGuid(ParamsConfig.USER_STUFFIX), "正常", uziType);
		user.setCreateTime(TimeUtils.getSystemTime());
		userdb.save(user);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param uaccount
	 *            账户名称
	 * @param uPassword
	 *            账户密码
	 * @param pPhone
	 *            手机
	 * @param pName
	 *            昵称
	 * @param pBirthdate
	 *            生日
	 * @param pSex
	 *            性别
	 */
	@CacheEvict(value = "pomeloUserInfoCache", key = "#uaccount")
	@ExceptionHandler(value = Exception.class)
	@Transactional
	public void updateUserInfo(String uaccount, String uPassword,
			String pPhone, String pName, String pBirthdate, String pSex,
			String email) {

		PersonInfo personInfo = persondb.findByuAccount(uaccount);

		if (personInfo == null) {
			personInfo = new PersonInfo();
			personInfo.setPid(Guid.newDCGuid(ParamsConfig.PERSON_STUFFIX));
		}

		personInfo.setpName(pName);
		personInfo.setpBirthdate(pBirthdate);
		personInfo.setpPhone(pPhone);
		personInfo.setpEmail(email);
		personInfo.setCreateTime(TimeUtils.getSystemTime());
		persondb.update(personInfo);

		UserInfo user = userdb.findByuAccount(uaccount);

		if (user == null) {
			user = new UserInfo();
			user.setUid(Guid.newDCGuid(ParamsConfig.USER_STUFFIX));
			user.setCreateTime(TimeUtils.getSystemTime());
		}

		user.setuAccount(uaccount);
		user.setuPassword(uPassword);

		userdb.update(user);
	}

	/**
	 * 根据账户名密码，查询用户信息
	 * 
	 * @param uaccount
	 *            用户名
	 * @param password
	 *            密码
	 * @param uziType
	 *            用户类型：1.{@link ParamsConfig#ME_IS_UZI},2.
	 *            {@link ParamsConfig#ME_IS_PARENT}
	 * @return
	 */

	public UserResult queryUserInfo(String uaccount, String password,
			String uziType) {
		UserInfo user = userdb.findByuAccountAuPwd(uaccount, password);
		if (user == null) {
			return null;
		}
		UserResult result = queryUserInfo(uaccount, uziType);
		if (result != null) {
			result.setuAccount(uaccount);
			result.setuPassword(password);
		}
		return result;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param uaccount
	 * @param uziType
	 * @return
	 */
	@Cacheable(value = "pomeloUserInfoCache", key = "#uaccount")
	public UserResult queryUserInfo(String uaccount, String uziType) {
		UserResult result = new UserResult();
		PersonInfo info = persondb.findByuAccount(uaccount);
		if (info == null) {
			return null;
		}
		Person person = new Person();
		person.setPerson(info);
		result.setPerson(person);
		List<Person> persons = queryPersons(uaccount, uziType);
		result.setRelPersons(persons);
		return result;
	}

	/**
	 * 
	 * 
	 * @param uaccount
	 *            用户账户
	 * @param uziType
	 *            1.我是柚子。2.我是父母
	 * @return
	 */
	public RelPersonsResult queryRelPersons(String uaccount, String uziType) {
		RelPersonsResult result = new RelPersonsResult(uaccount, uziType, null);
		result.setRelpersons(queryPersons(uaccount, uziType));
		return result;
	}

	/**
	 * 查询人员信息
	 * 
	 * @param uaccount
	 * @param uziType
	 * @return
	 */
	private List<Person> queryPersons(String uaccount, String uziType) {
		PersonInfo info = persondb.findByuAccount(uaccount);
		List<PersonRelInfo> personrels = null;
		if (StringUtils.equals(uziType, ParamsConfig.ME_IS_PARENT)) {
			personrels = personreldb.findByPidb(info.getPid());
		} else {
			personrels = personreldb.findByPida(info.getPid());
		}

		if (personrels == null || personrels.isEmpty()) {
			return null;
		}

		List<Person> persons = new ArrayList<Person>();
		for (PersonRelInfo personRelInfo : personrels) {
			Person person1 = new Person();
			person1.setPersonRelType(personRelInfo.getPrtype());
			String pid = personRelInfo.getPidb();
			if (StringUtils.equals(uziType, ParamsConfig.ME_IS_PARENT)) {
				pid = personRelInfo.getPida();
			}
			PersonInfo info1 = persondb.findById(pid);
			person1.setPerson(info1);
			persons.add(person1);
		}
		return persons;
	}

	/**
	 * 
	 * 保存关联关系人信息
	 * 
	 * @param uAccount
	 * @param pPhone
	 * @param pName
	 * @param pBirthdate
	 * @param pAddress
	 * @param prType
	 * @throws HaloDBException
	 */
	@SuppressWarnings("unused")
	@CacheEvict(value = "pomeloUserInfoCache", key = "#uAccount")
	@ExceptionHandler(value = Exception.class)
	@Transactional
	public void saveRelPeopleInfo(String uAccount, String pPhone, String pName,
			String pBirthdate, String pAddress, String prType)
			throws HaloDBException {
		PersonInfo persona = persondb.findByuAccount(uAccount);
		PersonInfo personb = null;
		personb = persondb.findBypPhone(pPhone);
		if (personb == null) {
			personb = new PersonInfo();
			personb.setPid(Guid.newDCGuid(ParamsConfig.PERSON_STUFFIX));
			personb.setCreateTime(TimeUtils.getSystemTime());
		}
		personb.setpName(pName);
		personb.setpBirthdate(pBirthdate);
		personb.setpPhone(pPhone);
		personb.setpAddress(pAddress);
		this.savePersonInfo(personb);
		PersonRelInfo relinfo = new PersonRelInfo(
				Guid.newDCGuid(ParamsConfig.PERSONREL_STUFFIX),
				persona.getPid(), personb.getPid(), prType);
		if (personb == null) {
			relinfo.setCreateTime(TimeUtils.getSystemTime());
			this.savePersonRelInfo(relinfo);
		} else {
			this.updatePersonRelInfo(relinfo);
		}
	}

	/**
	 * 
	 * 修改关联关系人信息
	 * 
	 * @param uAccount
	 * @param pPhone
	 * @param pName
	 * @param pBirthdate
	 * @param pAddress
	 * @param prType
	 * @throws HaloDBException
	 */
	@SuppressWarnings("unused")
	@CacheEvict(value = "pomeloUserInfoCache", key = "#uAccount")
	@ExceptionHandler(value = Exception.class)
	@Transactional
	public void updateRelPeopleInfo(String uAccount, String pPhone,
			String pName, String pBirthdate, String pAddress, String prType)
			throws HaloDBException {
		PersonInfo persona = persondb.findByuAccount(uAccount);
		PersonInfo personb = null;
		personb = persondb.findBypPhone(pPhone);
		if (personb == null) {
			personb = new PersonInfo();
			personb.setPid(Guid.newDCGuid(ParamsConfig.PERSON_STUFFIX));
			personb.setCreateTime(TimeUtils.getSystemTime());
		}
		personb.setpName(pName);
		personb.setpBirthdate(pBirthdate);
		personb.setpPhone(pPhone);
		personb.setpAddress(pAddress);
		this.savePersonInfo(personb);

		PersonRelInfo relinfo = new PersonRelInfo(
				Guid.newDCGuid(ParamsConfig.PERSONREL_STUFFIX),
				persona.getPid(), personb.getPid(), prType);
		if (personb == null) {
			this.savePersonRelInfo(relinfo);
		} else {
			this.updatePersonRelInfo(relinfo);
		}

	}

	/**
	 * 保存人员信息
	 * 
	 * @param person
	 */
	public void savePersonInfo(PersonInfo person) {
		persondb.save(person);
	}

	/**
	 * 保存人员信息
	 * 
	 * @param person
	 */
	public void savePersonRelInfo(PersonRelInfo personrel) {
		personreldb.save(personrel);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	@CacheEvict(value = "pomeloUserInfoCache", key = "#user.getuAccount()")
	public void updateUserInfo(UserInfo user) {
		userdb.update(user);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @throws HaloDBException
	 */
	public void updatePersonInfo(PersonInfo person) throws HaloDBException {
		persondb.update(person);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @throws HaloDBException
	 */
	public void updatePersonRelInfo(PersonRelInfo personrel)
			throws HaloDBException {
		personreldb.update(personrel);
	}

	@CacheEvict(value = "pomeloUserInfoCache", key = "#uAccount")
	public void deleteRelPerson(String uAccount, String pPhone) {
		PersonInfo persona = persondb.findByuAccount(uAccount);
		PersonInfo personb = persondb.findBypPhone(pPhone);
		PersonRelInfo relInfo = new PersonRelInfo(null, persona.getPid(),
				personb.getPid(), null);
		personreldb.remove(relInfo);
	}

	public Pagination<UserInfo> queryAllUserByLimit(UserInfo userInfo,
			int start, int limit) {
		Pagination<UserInfo> oage = userdb.queryAllUserByLimit(userInfo, start,
				limit);
		return oage;
	}
}