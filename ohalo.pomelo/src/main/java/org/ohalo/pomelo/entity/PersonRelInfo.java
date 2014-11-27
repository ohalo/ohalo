package org.ohalo.pomelo.entity;

import java.util.HashMap;
import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 
 * 人员关联关系信息
 * 
 * @author z.halo
 * @since 2013年10月14日 1.0
 */
public class PersonRelInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6510390021556058574L;

	/**
	 * 关联关系唯一标识
	 */
	private String prid;

	/**
	 * 人员a
	 */
	private String pida;

	/**
	 * 人员b
	 */
	private String pidb;

	/**
	 * 关联关系（爸爸，妈妈，儿子，哥哥，姐姐，弟弟，妹妹，爷爷，奶奶，姥爷，姥姥）
	 */
	private String prtype;

	public PersonRelInfo() {

	}

	public PersonRelInfo(String prid, String pida, String pidb, String prtype) {
		this.prid = prid;
		this.pida = pida;
		this.pidb = pidb;
		this.prtype = prtype;
	}

	public String getPrid() {
		return prid;
	}

	public void setPrid(String prid) {
		this.prid = prid;
	}

	public String getPida() {
		return pida;
	}

	public void setPida(String pida) {
		this.pida = pida;
	}

	public String getPidb() {
		return pidb;
	}

	public void setPidb(String pidb) {
		this.pidb = pidb;
	}

	public String getPrtype() {
		return prtype;
	}

	public void setPrtype(String prtype) {
		this.prtype = prtype;
	}

	@Override
	public String toString() {
		return "PersonRelInfo [prid=" + prid + ", pida=" + pida + ", pidb="
				+ pidb + ", prtype=" + prtype + ", getCreateTime()="
				+ getCreateTime() + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prid", prid);
		params.put("pida", pida);
		params.put("pidb", pidb);
		params.put("prtype", prtype);
		params.put("createTime", getCreateTime());
		return params;
	}

	@Override
	public DBObject toDBObject() {
		// TODO Auto-generated method stub
		return null;
	}
}